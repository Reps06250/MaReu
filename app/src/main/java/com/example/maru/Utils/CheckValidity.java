package com.example.maru.Utils;

import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.service.MeetingApiService;
import java.util.ArrayList;
import java.util.List;

public class CheckValidity {

    private static MeetingApiService mApiService = DI.getMeetingApiService();


    public static List<String> getFreeRooms(long dateAndTime, long duree) {

        // Définie qu'elles sont les salles disponibles e fonction de la date, de l'heure et de la durée de la réunion.
        //TODO : trop coûteuse, peut être amélioré si l'idée convient au client

        List<String> listeDesSallesDisponibles = new ArrayList<>();
        listeDesSallesDisponibles.addAll(mApiService.getRoomsList());
        int count = 0;
            while (count < mApiService.getMeetings().size() && listeDesSallesDisponibles.size() > 0) {
                for (Meeting meeting : mApiService.getMeetings()) {
                    if((meeting.getDate() <= dateAndTime && (meeting.getDate()+meeting.getDuree()) >= dateAndTime)
                            || (meeting.getDate() <= dateAndTime+duree && (meeting.getDate()+meeting.getDuree()) >= dateAndTime+duree)
                            || (meeting.getDate()>=dateAndTime && ((meeting.getDate()+meeting.getDuree())<=(dateAndTime+duree))))
                        listeDesSallesDisponibles.remove(meeting.getRoom());
                }
                count++;
            }
            return listeDesSallesDisponibles;
    }
}
