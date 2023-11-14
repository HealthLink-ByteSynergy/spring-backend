package com.example.demo.service;

import com.example.demo.entity.MeetingEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.ItemNotFoundException;

public interface MeetingService {
    MeetingEntity getMeetingByMeetingId(String meetingId) throws ItemNotFoundException;
    void deleteByMeetingId(String meetingId) throws ItemNotFoundException;
    MeetingEntity saveMeeting(MeetingEntity meetingEntity) throws InvalidFormatException;

}
