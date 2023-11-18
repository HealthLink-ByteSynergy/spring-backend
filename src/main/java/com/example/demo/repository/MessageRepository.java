package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MessageEntity;
import com.example.demo.entity.PatientEntity;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,String>{
     List<MessageEntity> findAllByRecPatientEntity(PatientEntity recPatientEntity);
     List<MessageEntity> findAllBySenPatientEntity(PatientEntity senPatientEntity);
     List<MessageEntity> findAllByRecPatientEntityAndSenPatientEntityOrderByDateAsc
     
     (PatientEntity recPatientEntity,PatientEntity senPatientEntity);

     List<MessageEntity> findByRecPatientEntityInAndSenPatientEntityInOrderByDateAsc(List<PatientEntity> recPatientEntities, List<PatientEntity> senPatientEntity);

     List<MessageEntity> deleteByRecPatientEntityInAndSenPatientEntityIn
     (List<PatientEntity> recPatientEntities, List<PatientEntity> senPatientEntity);

     String deleteAllByRecPatientEntityAndSenPatientEntity(PatientEntity recpPatientEntity,PatientEntity senPatientEntity);
}
