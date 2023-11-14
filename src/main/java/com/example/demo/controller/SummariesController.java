package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidFormatException;
import com.example.demo.service.SummariesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/summaries")
@RequiredArgsConstructor
public class SummariesController {

    private final SummariesService summariesService;

    @PostMapping("/")
    public String generateSummary(@RequestBody String message) throws InvalidFormatException{
        return summariesService.generateTempChatSummary(message);
    }
}
