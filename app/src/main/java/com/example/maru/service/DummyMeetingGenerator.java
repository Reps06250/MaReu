package com.example.maru.service;

import com.example.maru.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    public final static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("F", "Android","pierre, henri, herve, carole, laura, nico, pauline, sandrine, sophie, olivier, floufi",1589882400000L,1800000L,"#7FE873F2"),
            new Meeting("D","La place du bilboquet dans les cartoons des années 80","pierre, henri, herve, carole, laura, nico, pauline, sandrine, sophie, olivier, floufi",1589893200000L,1800000L,"#80F2F273"),
            new Meeting("H", "Java","pierre, henri, herve, carole, laura, nico, pauline, sandrine, sophie, olivier, floufi",1589902200000L,1800000L,"#5800E5FF"),
            new Meeting("A", "PanPan, la descente aux enfers d'un lapin star à hollywood !","pierre, henri, herve, carole, laura, nico, pauline, sandrine, sophie, olivier, floufi",1589902200000L,1800000L,"#7FE873F2"),
            new Meeting("E","La vie d'Amora, l'inventeur du tire cornichon","moi et personne d'autre",1593335700000L,1800000L,"#8073F29D")
    );

    public static List<String> DUMMY_ROOMS = Arrays.asList("A","B","C","D","E","F","G","H","I","J");

    static List<Meeting> generateMeetings(){return new ArrayList<>(DUMMY_MEETINGS);}
    static List<String> generateRooms(){return new ArrayList<>(DUMMY_ROOMS);}

}
