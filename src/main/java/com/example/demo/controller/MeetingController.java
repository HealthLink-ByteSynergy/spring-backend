package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MeetingEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.service.MeetingService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/meeting")
@RequiredArgsConstructor
public class MeetingController {
    
    private final MeetingService meetingService;

    @GetMapping("/id/{meetingId}")
    public MeetingEntity getMeeting(@PathVariable("meetingId") String meetingId) throws ItemNotFoundException{
        return meetingService.getMeetingByMeetingId(meetingId);
    }

    @PostMapping("/save")
    public MeetingEntity saveMeeting(@RequestBody MeetingEntity meetingEntity) throws InvalidFormatException{
        return meetingService.saveMeeting(meetingEntity);
    }

    @DeleteMapping("/delete/{meetingId}")
    public void DeleteMeeting(@PathVariable("meetingId") String meetingId) throws ItemNotFoundException{
        meetingService.deleteByMeetingId(meetingId);
    }
}
