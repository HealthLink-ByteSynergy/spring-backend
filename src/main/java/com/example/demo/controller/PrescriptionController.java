package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.PrescriptionEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.service.PrescriptionService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
public class PrescriptionController {
    
    private final PrescriptionService prescriptionService;

    @GetMapping("/id/{prescriptionId}")
    public PrescriptionEntity getByPrescription(@PathVariable("prescriptionId") String prescriptionId) throws ItemNotFoundException{
        return prescriptionService.getByPrescriptionId(prescriptionId);
    }

    @PostMapping("/save")
    public PrescriptionEntity savePrescription(@RequestBody PrescriptionEntity prescriptionEntity) throws InvalidFormatException{
        return prescriptionService.savePrescription(prescriptionEntity);
    }

}
