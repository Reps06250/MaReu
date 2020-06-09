package com.example.maru.Event;

import com.example.maru.model.Meeting;

import java.util.List;

public class LaunchDialogEvent {

    private String dialog;
    private String string;
    private int data;
    private long l;
    private Meeting meeting;
    private List<String> stringList;

    public LaunchDialogEvent(String dialog, String string, long l) {
        this.dialog = dialog;
        this.string = string;
        this.l =l;
    }

    public LaunchDialogEvent(String dialog, String string, int data) {
        this.dialog = dialog;
        this.string = string;
        this.data = data;
    }

    public LaunchDialogEvent(String dialog, long l) {
        this.dialog = dialog;
        this.l =l;
    }

    public LaunchDialogEvent(String dialog, String string){
        this.dialog = dialog;
        this.string = string;
    }

    public LaunchDialogEvent(String dialog, List<String> stringList, int data){
        this.dialog = dialog;
        this.stringList= stringList;
        this.data = data;
    }

    public LaunchDialogEvent(String dialog, Meeting meeting){
        this.dialog =dialog;
        this.meeting = meeting;
    }

    public String getDialog() {
        return dialog;
    }

    public String getString() {
        return string;
    }

    public int getData() {
        return data;
    }

    public Long getL() {return l;}

    public Meeting getMeeting() {return meeting;}

    public List<String> getStringList() {return stringList;}
}
