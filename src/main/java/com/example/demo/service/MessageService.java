package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.MessageEntity;
import com.example.demo.entity.SpecialistEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;


public interface MessageService {

    MessageEntity getMessageById(String messageId) throws ItemNotFoundException;

    String generateMessage(String message) throws InvalidFormatException;
    
    List<MessageEntity> getAllReceiverSender(MessageEntity messageEntity) throws ItemNotFoundException; //sort by date, convert to patiententity
    
    MessageEntity saveUserToBotMessage(MessageEntity messageEntity) throws ItemNotFoundException, InvalidFormatException;  //save sender and send reply

    MessageEntity saveUserToUserMessage(MessageEntity messageEntity) throws InvalidFormatException; //save sender and send notification

    //need a function that accounts separately for recommending doctors from our database;
    List<MessageEntity> getAllReceiverSenderBot(MessageEntity messageEntity) throws ItemNotFoundException;

    SpecialistEntity recommendSpecialists(MessageEntity messageEntity) throws InvalidFormatException, ItemNotFoundException;

    MessageEntity saveMeetingChat(MessageEntity messageEntity) throws ItemNotFoundException;

    void deleteByMessageId(String messageId) throws ItemNotFoundException;

    void deleteAllByRecIdAndSendId(MessageEntity messageEntity) throws ItemNotFoundException;


}
