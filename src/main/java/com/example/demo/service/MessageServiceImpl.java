package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.MessageEntity;
import com.example.demo.entity.PatientEntity;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

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
    public List<MessageEntity> getAllReceiverSender(String receiverId, String senderId) throws ItemNotFoundException {
        try{
            PatientEntity recpatientEntity=new PatientEntity();
            PatientEntity senpatientEntity=new PatientEntity();
            recpatientEntity.setPatientId(receiverId);
            senpatientEntity.setPatientId(senderId);
            return messageRepository.findAllByRecPatientEntityAndSenPatientEntityOrderByDateAsc(recpatientEntity, senpatientEntity);
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
    }

    @Override
    public String generateSummary(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateSummary'");
    }

    @Override
    public MessageEntity saveUserToBotMessage(MessageEntity messageEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveUserToBotMessage'");
    }

    @Override
    public MessageEntity saveUserToUserMessage(MessageEntity messageEntity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveUserToUserMessage'");
    }

    @Override
    public MessageEntity sendChatBotMessage(String message, String senderId, String receiverId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendChatBotMessage'");
    }

    @Override
    public MessageEntity saveMeetingChat(String meetingLink) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveMeetingChat'");
    }

    @Override
    public void deleteByMessageId(String messageId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteByMessageId'");
    }

    @Override
    public void deleteAllByRecIdAndSendId(String receiverId, String senderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllByRecIdAndSendId'");
    }
    
}
