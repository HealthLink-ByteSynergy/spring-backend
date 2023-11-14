package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.MessageEntity;
import com.example.demo.exception.ItemNotFoundException;

public interface MessageService {

    MessageEntity getMessageById(String messageId) throws ItemNotFoundException;

    List<MessageEntity> getAllReceiverSender(String receiverId, String senderId) throws ItemNotFoundException; //sort by date, convert to patiententity
    
    String generateSummary(String message);

    MessageEntity saveUserToBotMessage(MessageEntity messageEntity);  //save sender and send reply

    MessageEntity saveUserToUserMessage(MessageEntity messageEntity); //save sender and send notification

    MessageEntity sendChatBotMessage(String message, String senderId, String receiverId);

    MessageEntity saveMeetingChat(String meetingLink);

    void deleteByMessageId(String messageId);

    void deleteAllByRecIdAndSendId(String receiverId, String senderId);
}
