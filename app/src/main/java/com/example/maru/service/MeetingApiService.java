package com.example.maru.service;

import com.example.maru.model.Meeting;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();
    List<Meeting> getRoomsFilteredMeetings(String room);
    List<Meeting> getDateFilteredMeetings(List<Meeting> list, long date);
    List<String> getRoomsList();
    Timepoint getOpenHour();
    long getOpenHourLong();
    Timepoint getClosedHour();
    long getClosedHourLong();
    Calendar[] getDisabledDays();
    int getMinuteIntervalInt();
    long getMinuteIntervalLong();
    List<String> getListeDesDurees();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);
}
