package com.example.maru.model;

import java.util.List;

public class Meeting implements Comparable<Meeting>{

    private String room;
    private String subject;
    private List<String> participants;
    private long date;
    private long duree;
    private String color;

    public Meeting(String room, String subject, List<String> participants, long date, long duree, String color){ //date représente la date et l'heure en epochTime, et duree la duree en ms
        this.room = room;
        this.subject = subject;
        this.participants = participants;
        this.date = date;
        this.duree = duree;
        this.color = color;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDuree() {return duree;}

    public void setDuree(long duree) {this.duree = duree;}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int compareTo(Meeting meeting) {
        int res;
        if((this.date - meeting.date)>0) res = 1;
        else if((this.date - meeting.date)<0) res = -1;
        else res = 0;
        return res;
    }
}
