package com.example.maru.service;

import com.example.maru.model.Meeting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
            new Meeting("F", "Android","pierre, henri, herve, carole, laura, nico, pauline, sandrine, sophie, olivier, floufi",1589882400000L,1800000L,"#E873F2"),
            new Meeting("D","La place du bilboquet dans les cartoons des années 80","pierre, henri, herve, carole, laura, nico, pauline, sandrine, sophie, olivier, floufi",1589893200000L,1800000L,"#73F29D"),
            new Meeting("H", "Java","pierre, henri, herve, carole, laura, nico, pauline, sandrine, sophie, olivier, floufi",1589902200000L,1800000L,"#F27573"),
            new Meeting("A", "PanPan, la descente aux enfers d'un lapin star à hollywood !","pierre, henri, herve, carole, laura, nico, pauline, sandrine, sophie, olivier, floufi",1589902200000L,1800000L,"#73F29D")
    );

    public static List<String> DUMMY_ROOMS = Arrays.asList("A","B","C","D","E","F","G","H","I","J");

    static List<Meeting> generateMeetings(){return new ArrayList<>(DUMMY_MEETINGS);}
    static List<String> generateRooms(){return new ArrayList<>(DUMMY_ROOMS);}

}
