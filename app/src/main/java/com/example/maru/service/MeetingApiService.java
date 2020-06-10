package com.example.maru.service;

import com.example.maru.model.Meeting;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();
    void resetMeetings();

    void deleteMeeting(Meeting meeting);

    List<Meeting> getRoomsFilteredMeetings(List<Meeting> list, String room);
    List<Meeting> getDateFilteredMeetings(List<Meeting> list, long date);

    List<String> getRoomsList();
    String getColor() ;
    Timepoint getOpenHour();
    long getOpenHourLong();
    Timepoint getClosedHour();
    long getClosedHourLong();
    Calendar[] getDisabledDays();
    int getMinuteIntervalInt();
    long getMinuteIntervalLong();
    List<String> getListeDesDurees();
    void createMeeting(Meeting meeting);
}
