package com.example.maru.service;

import com.example.maru.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    private static List<String> listeDesParticipants = Arrays.asList("pierre","henri","herve","carole","laura","nico","pauline","sandrine","sophie","olivier","floufi");
    public final static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("F", "Android", listeDesParticipants,1591891200000L,7200000L,"#7FE873F2"),
            new Meeting("D","La place du bilboquet dans les cartoons des années 80",listeDesParticipants,1591887600000L,7200000L,"#80F2F273"),
            new Meeting("H", "Java",listeDesParticipants,1591891200000L,7200000L,"#5800E5FF"),
            new Meeting("A", "PanPan, la descente aux enfers d'un lapin star à hollywood !",listeDesParticipants,1591887600000L,7200000L,"#7FE873F2"),
            new Meeting("E","La vie d'Amora, l'inventeur du tire cornichon",listeDesParticipants,1591891200000L,7200000L,"#8073F29D"),
            new Meeting("I", "L'art du tissage d'un tapis persan", listeDesParticipants,1591887600000L,7200000L,"#7FE873F2"),
            new Meeting("C","La place du bilboquet dans les cartoons des années 80",listeDesParticipants,1591887600000L,7200000L,"#80F2F273"),
            new Meeting("J", "Java",listeDesParticipants,1591891200000L,7200000L,"#5800E5FF"),
            new Meeting("B", "PanPan, la descente aux enfers d'un lapin star à hollywood !",listeDesParticipants,1591891200000L,7200000L,"#7FE873F2"),
            new Meeting("G","La vie d'Amora, l'inventeur du tire cornichon",listeDesParticipants,1591891200000L,7200000L,"#8073F29D")
    );

    public static List<String> DUMMY_ROOMS = Arrays.asList("A","B","C","D","E","F","G","H","I","J");

    static List<Meeting> generateMeetings(){return new ArrayList<>(DUMMY_MEETINGS);}
    static List<String> generateRooms(){return new ArrayList<>(DUMMY_ROOMS);}

}
