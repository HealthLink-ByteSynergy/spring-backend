package com.example.demo.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;



import com.example.demo.entity.MeetingEntity;
import com.example.demo.entity.MeetingLink;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.MeetingRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{
    
    private final MeetingRepository meetingRepository;

    @Value("${digitalSamba.teamId}")
    private String TeamId;

    @Value("${digitalSamba.developerKey}")
    private String developerKey;

    @Override
    public MeetingEntity getMeetingByMeetingId(String meetingId) throws ItemNotFoundException {
        try{
            Optional<MeetingEntity> meeting=meetingRepository.findById(meetingId);
            if(meeting.isPresent()) return meeting.get();
        }
        catch(Exception ex){
            throw new ItemNotFoundException("This meetingId doesn't exist");
        }
        return null;
    }

    @Override
    public void deleteByMeetingId(String meetingId) throws ItemNotFoundException {
        try{
            meetingRepository.deleteById(meetingId);
        }
        catch(Exception ex){
            throw new ItemNotFoundException(ex.getMessage());
        }
        
    }

    @Override
    public MeetingEntity saveMeeting(MeetingEntity meetingEntity) throws InvalidFormatException {
        try{
            meetingEntity.setDate(new Date());
            return meetingRepository.save(meetingEntity);
        }
        catch(Exception ex){
            throw new InvalidFormatException("Invalid parameters passed");
        }
    }

    @Override
    public MeetingLink CreateMeeting() throws IOException, InterruptedException, URISyntaxException{
        String TEAM_ID = TeamId;
        String DEVELOPER_KEY = developerKey;
        String authorizationHeader = "Bearer " + Base64.getEncoder().encodeToString((TEAM_ID + ":" + DEVELOPER_KEY).getBytes());
        final String uri="https://api.digitalsamba.com/api/v1/rooms";

        String jsonData="{ \"privacy\" : \"public\" }";

        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization",authorizationHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity=new HttpEntity<>(jsonData,headers);

        ResponseEntity<MeetingLink> response=restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                MeetingLink.class
            );
        
        return response.getBody();
    }
    
}
