package com.example.demo.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MeetingEntity;
import com.example.demo.entity.MeetingLink;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.service.MeetingService;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/meeting")
@RequiredArgsConstructor
public class MeetingController {
    
    private final MeetingService meetingService;

    @GetMapping("/")
    public MeetingLink GenerateMeeting() throws IOException, InterruptedException, URISyntaxException{
        return meetingService.CreateMeeting();
    }
    @GetMapping("/id/{meetingId}")
    public MeetingEntity getMeeting(@PathVariable("meetingId") String meetingId) throws ItemNotFoundException{
        return meetingService.getMeetingByMeetingId(meetingId);
    }

    @PostMapping("/save")
    public MeetingEntity saveMeeting(@RequestBody MeetingEntity meetingEntity) throws InvalidFormatException{
        return meetingService.saveMeeting(meetingEntity);
    }

    @Transactional
    @DeleteMapping("/delete/{meetingId}")
    public void DeleteMeeting(@PathVariable("meetingId") String meetingId) throws ItemNotFoundException{
        meetingService.deleteByMeetingId(meetingId);
    }
}


