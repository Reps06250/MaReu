package com.example.maru.service;

import com.example.maru.model.Meeting;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private Timepoint openHour = new Timepoint(8,00);
    private Timepoint closedHour = new Timepoint(22,00);
    private Calendar[] disabledDays = new Calendar[Calendar.SUNDAY];
    private int minuteIntervalInt = 15; //minimum 1
    private int maxMeetingTimeInMinutes = 240;

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private List<String> rooms = DummyMeetingGenerator.generateRooms();


    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Meeting> getRoomsFilteredMeetings(String room){
        List<Meeting> filteredList = new ArrayList<>();
        for( Meeting meeting : meetings)
            if(meeting.getRoom() == room) filteredList.add(meeting);
            return filteredList;
    }

    @Override
    public List<Meeting> getDateFilteredMeetings(List<Meeting> list, long date) {
        List<Meeting> filtredList = new ArrayList<>();
        for(Meeting meeting : list)
            if(meeting.getDate()/86_400_000 == date/86_400_000) filtredList.add(meeting);
        return filtredList;
    }

    @Override
    public List<String> getRoomsList() {return rooms;}

    @Override
    public Timepoint getOpenHour() {return openHour;}

    @Override
    public long getOpenHourLong() {return openHour.getHour()*3_600_000 + openHour.getMinute()*60_000;}

    @Override
    public Timepoint getClosedHour() {return closedHour;}

    @Override
    public long getClosedHourLong() {return closedHour.getHour()*3_600_000 + closedHour.getMinute()*60_000;}

    @Override
    public Calendar[] getDisabledDays() {return disabledDays;}

    @Override
    public int getMinuteIntervalInt() {return minuteIntervalInt;}

    @Override
    public long getMinuteIntervalLong() {return minuteIntervalInt * 60_000;}

    @Override
    public List<String> getListeDesDurees() {
        List<String> liste = new ArrayList<>();

        for(int i = minuteIntervalInt; i <= maxMeetingTimeInMinutes; i+=minuteIntervalInt){
            if(i/60>=10 && i%60 == 0)liste.add(i/60 + "H" + i%60 + "0");
            else if(i/60<10 && i%60 == 0)liste.add("0" + i/60 + "H" + i%60 + "0");
            else if(i/60<10 && i%60 != 0)liste.add("0" + i/60 + "H" + i%60);
            else liste.add( i/60 + "H" + i%60);
        }
        return liste;
    }


//    @Override
//    public List<Meeting> getDateFilteredMeetings(List<Meeting> list, String date){
//        List<Meeting> filteredList = new ArrayList<>();
////        for( Meeting meeting : list)
////            if(meeting.getDate().equals(date)) filteredList.add(meeting);
//        return filteredList;
//    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
}
