package com.example.demo.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.MeetingEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.MeetingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService{
    
    private final MeetingRepository meetingRepository;

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
    
}
