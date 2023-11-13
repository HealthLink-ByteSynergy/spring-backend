package com.example.demo.service;

import com.example.demo.entity.MeetingEntity;

public interface MeetingService {
    MeetingEntity getMeetingByMeetingId(String meetingId);
    void deleteByMeetingId(String meetingId);
    MeetingEntity saveMeeting(MeetingEntity meetingEntity);

}
