package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.Generate;
import com.example.demo.entity.IsAvailable;
import com.example.demo.entity.MeetingLink;
import com.example.demo.entity.MessageEntity;
import com.example.demo.entity.MessageType;
import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.SpecialistEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SummariesService summariesService;
    private final DoctorService doctorService;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final MeetingService meetingService;

    @Value("${cohereAi.Key}")
    private String Key;

    public PatientEntity getIdByEmail(String email){
        Optional<UserEntity> user=userRepository.findByEmail(email);
        if(user.isPresent()){
            UserEntity isuser=user.get();
            List<PatientEntity> patient=patientRepository.findByUserEntity(isuser);
            return patient.get(0);
        }
        return null;
    }

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
        final String apiKey="Bearer "+ Key;

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
            List<PatientEntity> ids=new ArrayList<>();
            ids.add(messageEntity.getRecPatientEntity());
            ids.add(messageEntity.getSenPatientEntity());
            // return messageRepository.findAllByRecPatientEntityAndSenPatientEntityOrderByDateAsc(messageEntity.getRecPatientEntity(), messageEntity.getSenPatientEntity());
            return messageRepository.findByRecPatientEntityInAndSenPatientEntityInOrderByDateAsc(ids, ids);
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

    @Override
    public List<MessageEntity> getAllReceiverSenderBot(MessageEntity messageEntity) throws ItemNotFoundException {
        try{
            List<PatientEntity> ids=new ArrayList<>();
            ids.add(getIdByEmail("health-link@gmail.com"));
            ids.add(messageEntity.getSenPatientEntity());
            // return messageRepository.findAllByRecPatientEntityAndSenPatientEntityOrderByDateAsc(messageEntity.getRecPatientEntity(), messageEntity.getSenPatientEntity());
            return messageRepository.findByRecPatientEntityInAndSenPatientEntityInOrderByDateAsc(ids, ids);
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

            String patientDetails="";
            String patient=messageEntity.getSenPatientEntity().getPatientId();
            Optional<PatientEntity> currPatient=patientRepository.findById(patient);
            patientDetails=currPatient.get().toString();

            System.out.println(patientDetails);

            newMessage="Patient Details: "+ patientDetails+"\n"+"Problem: "+ newMessage+"\nGive a relevant answer even if it is not the most accurate one. The patient details is just for reference. Base the response on the current problem";

            messageEntity.setRecPatientEntity(getIdByEmail("health-link@gmail.com"));
            messageEntity.setSummary(messageEntity.getText());
            MessageEntity currentEntity=messageRepository.save(messageEntity); //saving current message
            MessageEntity newEntity=new MessageEntity();

            String botResponse=generateMessage(newMessage); 
            //response by bot
            newEntity.setPreviousMessageId(currentEntity.getMessageId());
            newEntity.setMessageId(UUIDService.getUUID());
            newEntity.setRecPatientEntity(messageEntity.getSenPatientEntity());
            newEntity.setSenPatientEntity(messageEntity.getRecPatientEntity());
            newEntity.setText(botResponse);
            newEntity.setDate(new Date());
            newEntity.setMessageType(MessageType.CHAT);
            newEntity.setSummary(summariesService.generateTempChatSummary(newMessage+botResponse,"long","paragraph"));
            
            return messageRepository.save(newEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException(ex.getMessage());
        }

    }

    @Override
    public SpecialistEntity recommendSpecialists(MessageEntity messageEntity)
            throws InvalidFormatException, ItemNotFoundException {
        try{
            //frontend needs to send the previous message id;
            String previousId=messageEntity.getPreviousMessageId();
            // Add patient details too
            String newMessage=" ";

            if(previousId!=null && !previousId.equals("") && !previousId.equals(" ")){
                MessageEntity prevMessage=getMessageById(previousId);
                newMessage=newMessage+prevMessage.getSummary(); 
            }

            //Initialise response;

            SpecialistEntity specialistEntity=new SpecialistEntity();
            
            //adding patient details
            
            String patientDetails="";
            PatientEntity ps=messageEntity.getSenPatientEntity();

            Optional<PatientEntity> patientEntity=patientRepository.findById(ps.getPatientId());
            if(patientEntity.isPresent()) patientDetails=patientEntity.get().toString();

            newMessage="Patient Details: "+patientDetails+"\n"+"Main message: " + newMessage;

            String finalMessage=newMessage+"\nIs this health problem severe enough that it requires me to consult a doctor. You must give a 1 word answer Yes or No. No additional description.";

            String botResponse=generateMessage(finalMessage); //response by bot
              
            String currentResponse=botResponse;

            if(botResponse.contains("Yes")){

                String giveSpecialists=newMessage+"\nSuggest some specialists in the decreasing order of relevance. Try to keep it crisp. The patient details is just for reference. Base the response on the main message. ";

                botResponse=summariesService.generateTempChatSummary(generateMessage(giveSpecialists), "short", "paragraph");

                currentResponse=botResponse;

                botResponse+="\n\n What are the specialists names mentioned in the above text. Only give the names word by word no articles in the sentence and should have atmost 10 words and separate by commas.";

                botResponse=summariesService.generateTempChatSummary(generateMessage(botResponse),"short", "paragraph");
                List<DoctorEntity> AllDoctors=new ArrayList<>();
                List<String> Specialists=Arrays.asList(botResponse.split(","));
                for(int i=0;i<Specialists.size();i++){
                    String spec=Specialists.get(i);
                    if(spec.contains("\n")) spec=spec.split("\n")[0];
                    spec=spec.replace(" ", ""); //problem
                    System.out.println(spec);
                    List<DoctorEntity> doctors=doctorService.getDetailsBySpecialization(spec);
                    for(int j=0;j<doctors.size();j++) AllDoctors.add(doctors.get(j));

                    // if empty , add some random doctor

                    if(AllDoctors.size() ==0){
                        List<DoctorEntity> doct=doctorService.getByAvailability(IsAvailable.AVAILABLE);
                        if(doct.size()>0){
                            AllDoctors.add(doct.get(0));
                        }
                    }
                    
                }
                 specialistEntity.setDoctorEntity(AllDoctors);
            }

            else botResponse+=" This health problem is not that severe or the contex is not relevant.";

            MessageEntity currentEntity=new MessageEntity();
            currentEntity.setRecPatientEntity(getIdByEmail("health-link@gmail.com"));
            currentEntity.setMessageType(MessageType.CHAT);
            currentEntity.setMessageId(UUIDService.getUUID());
            currentEntity.setRecPatientEntity(messageEntity.getSenPatientEntity());
            currentEntity.setText(currentResponse);
            currentEntity.setDate(new Date());
            currentEntity.setPreviousMessageId(messageEntity.getPreviousMessageId());
            currentEntity.setSummary(currentResponse);
            
            MessageEntity res=messageRepository.save(currentEntity);
            specialistEntity.setMessageEntity(res);

            return specialistEntity;
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
            MeetingLink meetingLink= meetingService.CreateMeeting();
            if(meetingLink!=null){
                messageEntity.setText(meetingLink.getRoom_url());
            }
            
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
            List<PatientEntity> ids=new ArrayList<>();
            ids.add(messageEntity.getRecPatientEntity());
            ids.add(messageEntity.getSenPatientEntity());
            messageRepository.deleteByRecPatientEntityInAndSenPatientEntityIn(ids, ids);            
            //removing from tempchat table
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

    
}
