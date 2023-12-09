package com.example.demo.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DoctorModelEntity {
    private String doctorId;

    private UserEntity userEntity;

    private List<String> specialization;

    private IsAvailable isAvailable;

    private String licenseNumber;

    private String phoneNumber;
}
