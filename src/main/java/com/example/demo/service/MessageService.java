package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.MessageEntity;

public interface MessageService {

    MessageEntity getMessageById(String messageId);

    List<MessageEntity> getAllReceiverSender(String receiverId, String senderId); //sort by date, convert to patiententity
    
    String generateSummary(String message);

    MessageEntity saveUserToBotMessage(MessageEntity messageEntity);  //save sender and send reply

    MessageEntity saveUserToUserMessage(MessageEntity messageEntity); //save sender and send notification

    MessageEntity sendChatBotMessage(String message, String senderId, String receiverId);

    MessageEntity saveMeetingChat(String meetingLink);

    void deleteByMessageId(String messageId);

    void deleteAllByRecIdAndSendId(String receiverId, String senderId);
}
