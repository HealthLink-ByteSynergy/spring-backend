package com.example.demo.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;

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

    @GetMapping("/")
    public String Meeting() throws IOException, InterruptedException, URISyntaxException{
        
        String authorizationHeader = "Bearer " + "9fdb395e880766152a0e4877a92c342c7e3ff328";

        HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(new URI("https://248567598112cb22.api-in.cometchat.io/v3"))
        .header("Authorization", authorizationHeader)
        .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Body: " + response.body());
        return response.body() ;
    }
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


