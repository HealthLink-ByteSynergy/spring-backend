package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MedicineEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.service.MedicineService;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/medicine")
@RequiredArgsConstructor
public class MedicineController {
    
    private final MedicineService medicineService;

    @GetMapping("/getMedicines/{medicineId}")
    public List<MedicineEntity> getMedicines(@PathVariable("medicineId") String medicineId) throws ItemNotFoundException{
        return medicineService.getMedicinesList(medicineId);
    }

    @PostMapping("/save")
    public MedicineEntity saveMedicine(@RequestBody MedicineEntity medicineEntity) throws InvalidFormatException{
        return medicineService.saveMedicine(medicineEntity);
    }
}
