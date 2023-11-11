package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;

@Service
public class UUID_Service {
    public static String getUUID() {
        return Generators.timeBasedEpochGenerator().generate().toString();
    }
}

