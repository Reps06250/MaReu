package com.example.maru.service;

import com.example.maru.model.Meeting;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DummyMeetingApiService implements MeetingApiService {

    private Timepoint openHour = new Timepoint(8,00);  // heure minimum de début de réunion
    private Timepoint closedHour = new Timepoint(22,00); // heure maximum de début de réunion
    private Calendar[] disabledDays = new Calendar[0]; // jours non selectionnables
    private int minuteIntervalInt = 15; //minimum 1, interval en minute pour la durée des réunion et la sélection de l'heure
    private int maxMeetingTimeInMinutes = 240; // duréee maximum d'une réunion en minute
    private List<String> colors = Arrays.asList("#7FE873F2","#7F7F73F2","#8073F29D","#80F2F273","#7FF27573","#5800E5FF"); // liste des couleurs

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
    private List<String> rooms = DummyMeetingGenerator.generateRooms(); // liste des salles


    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public List<Meeting> getRoomsFilteredMeetings(List<Meeting> list, String room){
        List<Meeting> filteredList = new ArrayList<>();
        for( Meeting meeting : meetings)
            if(meeting.getRoom().equals(room)) filteredList.add(meeting);
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
    public List<String> getListeDesDurees() {  // liste des durees de réunion possible en fonction de la durée max et de l'interval souhaité
        List<String> liste = new ArrayList<>();

        for(int i = minuteIntervalInt; i <= maxMeetingTimeInMinutes; i+=minuteIntervalInt){
            if(i/60>=10 && i%60 == 0)liste.add(i/60 + "H" + i%60 + "0");
            else if(i/60<10 && i%60 == 0)liste.add("0" + i/60 + "H" + i%60 + "0");
            else if(i/60<10 && i%60 != 0)liste.add("0" + i/60 + "H" + i%60);
            else liste.add( i/60 + "H" + i%60);
        }
        return liste;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void resetMeetings() {meetings = DummyMeetingGenerator.generateMeetings();}

    @Override
    public String getColor(){ // renvoie une couleur aléatoire qui sera attribué à un meeting
        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }
}
