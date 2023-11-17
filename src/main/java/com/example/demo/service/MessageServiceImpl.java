package com.example.demo.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.Generate;
import com.example.demo.entity.MessageEntity;
import com.example.demo.entity.MessageType;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SummariesService summariesService;
    private final DoctorService doctorService;

    @Override
    public MessageEntity getMessageById(String messageId) throws ItemNotFoundException {
        try{
            Optional<MessageEntity> message=messageRepository.findById(messageId);
            if(message.isPresent()) return message.get();
        }
        catch(Exception ex){
            throw new ItemNotFoundException("This message doesn't exist");
        }
        return null;
    }

    @Override
    public String generateMessage(String message) throws InvalidFormatException {
        String p=message.replace("\r","\n\n");
        final String uri="https://api.cohere.ai/v1/generate";
        final String apiKey="Bearer "+ "0Nr94xvToSWRj7hM54yr8Y1uxz1HCMw8q8Bxh1Uo";

        try{
            RestTemplate restTemplate=new RestTemplate();
            HttpHeaders headers=new HttpHeaders();
            headers.set("Authorization",apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Generate requestBody= new Generate();
            requestBody.setTruncate("END");
            requestBody.setReturn_likelihoods("NONE");
            requestBody.setPrompt(p);

            HttpEntity<Generate> requestEntity=new HttpEntity<>(requestBody,headers);

            ResponseEntity<Generate> response=restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                Generate.class
            );

            String finalresponse=response.getBody().getGenerations().get(0).getText();
            return finalresponse;
        }
        catch(Exception ex){
            throw new InvalidFormatException(ex.getMessage());
        }
    }

    @Override
    public List<MessageEntity> getAllReceiverSender(MessageEntity messageEntity) throws ItemNotFoundException {
        try{
            return messageRepository.findAllByRecPatientEntityAndSenPatientEntityOrderByDateAsc(messageEntity.getRecPatientEntity(), messageEntity.getSenPatientEntity());
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }


    @Override
    public MessageEntity saveUserToBotMessage(MessageEntity messageEntity) throws ItemNotFoundException, InvalidFormatException {
        //implementation incomplete
        try{
            messageEntity.setMessageType(MessageType.CHAT);
            messageEntity.setDate(new Date());
            messageEntity.setMessageId(UUIDService.getUUID());
            String previousId=messageEntity.getPreviousMessageId();
            // Add patient details too
            String newMessage=messageEntity.getText();

            if(previousId!=null){
                Optional<MessageEntity> mess=messageRepository.findById(previousId);
                if(mess.isPresent()){
                    MessageEntity prevMessage=mess.get();
                    newMessage=newMessage+prevMessage.getSummary(); 
                }
            }

            //Saving the previous message
            if(newMessage.length()<300){
                messageEntity.setSummary(newMessage);
            }
            else messageEntity.setSummary(summariesService.generateTempChatSummary(newMessage));
            
            //adding patient details
            
            String patientDetails=messageEntity.getSenPatientEntity().toString();

            newMessage=patientDetails+"\n"+newMessage;

            MessageEntity currentEntity=messageRepository.save(messageEntity); //saving current message
            String botResponse=generateMessage(newMessage); 
            //response by bot
            currentEntity.setPreviousMessageId(currentEntity.getMessageId());
            currentEntity.setMessageId(UUIDService.getUUID());
            currentEntity.setRecPatientEntity(messageEntity.getSenPatientEntity());
            currentEntity.setSenPatientEntity(messageEntity.getRecPatientEntity());
            currentEntity.setText(botResponse);
            currentEntity.setDate(new Date());
            currentEntity.setSummary(summariesService.generateTempChatSummary(botResponse));
            
            return messageRepository.save(currentEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException(ex.getMessage());
        }

    }

    @Override
    public MessageEntity recommendSpecialists(MessageEntity messageEntity)
            throws InvalidFormatException, ItemNotFoundException {
        try{
            //frontend needs to send the previous message id;
            String previousId=messageEntity.getPreviousMessageId();
            // Add patient details too
            String newMessage=" ";

            if(previousId!=null){
                MessageEntity prevMessage=getMessageById(previousId);
                newMessage=newMessage+prevMessage.getSummary(); 
            }
            
            //adding patient details
            
            String patientDetails=messageEntity.getSenPatientEntity().toString();

            newMessage=patientDetails+"\n"+newMessage;

            String finalMessage=newMessage+"\nIs this health problem severe enough that it requires me to consult a doctor? Give a one word answer Yes or No";

            String botResponse=generateMessage(finalMessage); //response by bot

            if(botResponse.contains("YES")){
                String giveSpecialists=newMessage+"\nSuggest some specialists in the decreasing order of relevance with separation by commas and in one sentence. Only give the specialization, no description of the specialization.";
                botResponse=generateMessage(giveSpecialists);

                List<String> Specialists=Arrays.asList(botResponse.split(","));
                for(int i=0;i<Specialists.size();i++){
                    String spec=Specialists.get(i);
                    List<DoctorEntity> doctors=doctorService.getDetailsBySpecialization(spec);
                    botResponse+="\n"+spec+": \n";
                    for(int j=0;j<doctors.size();j++){
                        botResponse+=doctors.get(j).toString()+"\n";
                    }
                }
            }
            else botResponse+=" This health problem is not that severe.";

            MessageEntity currentEntity=new MessageEntity();

            currentEntity.setMessageType(MessageType.CHAT);
            currentEntity.setMessageId(UUIDService.getUUID());
            currentEntity.setRecPatientEntity(messageEntity.getSenPatientEntity());
            currentEntity.setSenPatientEntity(messageEntity.getRecPatientEntity());
            currentEntity.setText(summariesService.generateTempChatSummary(botResponse));
            currentEntity.setDate(new Date());
            currentEntity.setPreviousMessageId(messageEntity.getPreviousMessageId());
            currentEntity.setSummary(" ");
            
            return messageRepository.save(currentEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException(ex.getMessage());
        }

    }

    @Override
    public MessageEntity saveUserToUserMessage(MessageEntity messageEntity) throws InvalidFormatException {
        try{
            messageEntity.setMessageType(MessageType.CHAT);
            messageEntity.setDate(new Date());
            messageEntity.setMessageId(UUIDService.getUUID());
            messageEntity.setSummary(" ");
            return messageRepository.save(messageEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException(ex.getMessage());
        }
    }


    @Override
    public MessageEntity saveMeetingChat(MessageEntity messageEntity) throws ItemNotFoundException {
        //assuming that meeting link is in the text area
        try{
            messageEntity.setMessageType(MessageType.NOTIFICATION);
            messageEntity.setDate(new Date());
            messageEntity.setMessageId(UUIDService.getUUID());
            String previousId=messageEntity.getPreviousMessageId();

            if(previousId!=null){
                MessageEntity prevMessage=getMessageById(previousId);
                messageEntity.setSummary(prevMessage.getSummary());
                return messageRepository.save(messageEntity);
            }
        
            messageEntity.setSummary(" ");
            return messageRepository.save(messageEntity);
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
        
    }

    @Override
    public void deleteByMessageId(String messageId) throws ItemNotFoundException {
        try{
            messageRepository.deleteById(messageId);
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

    @Override
    public void deleteAllByRecIdAndSendId(MessageEntity messageEntity) throws ItemNotFoundException {
        try{
            messageRepository.deleteAllByRecPatientEntityAndSenPatientEntity(messageEntity.getRecPatientEntity(),messageEntity.getSenPatientEntity());
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

    
}
