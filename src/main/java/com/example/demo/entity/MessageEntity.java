package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity{

    @Id
    private String messageId;

    private String previousMessageId;

    @ManyToOne
    @JoinColumn(name="receiver_id")
    private PatientEntity recPatientEntity;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private PatientEntity senPatientEntity;

    @Column(length = 7000)
    private String text;

    private MessageType messageType;

    private Date date;

    @Column(length = 4000)
    private String summary;

}