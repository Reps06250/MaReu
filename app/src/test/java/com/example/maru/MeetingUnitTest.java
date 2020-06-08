package com.example.maru;

import com.example.maru.UI.AddMeeting.AddMeetingActivity;
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.service.DummyMeetingGenerator;
import com.example.maru.service.MeetingApiService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;
import static org.junit.Assert.*;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class MeetingUnitTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingssWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedmeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedmeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void createMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        // save meetings list size
        int meetingsOriginalSize = meetings.size();
        Meeting meeting= new Meeting("A", "Android","laura, nico, pauline, sandrine",1589882400000L,1800000L,"#E873F2");
        //check meetings not contain meeting
        assertFalse(service.getMeetings().contains(meeting));
        // Add meeting
        service.createMeeting(meeting);
        //check meetings contain meeting
        assertTrue(service.getMeetings().contains(meeting));
        // Check meetings have 1 element more
        assertEquals(meetings.size(), meetingsOriginalSize + 1, 0.001);
    }

    @Test
    public void RoomFilter() {
        int expected = 0;
        String room = "F";
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> filtredMeetings = service.getRoomsFilteredMeetings(meetings, room);
        for(Meeting meeting : meetings){
            if(meeting.getRoom().equals(room)){
                assertTrue(filtredMeetings.contains(meeting));
                expected ++;
            }
        }
        assertEquals(filtredMeetings.size() , expected);
    }

    @Test
    public void DateFilter() {
        int expected = 0;
        long date = 1589902200000L;
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> filtredMeetings = service.getDateFilteredMeetings(meetings, date);
        for(Meeting meeting : meetings){
            if(meeting.getDate()/86400000 == date/86400000){
                assertTrue(filtredMeetings.contains(meeting));
                expected ++;
            }
        }
        assertEquals(filtredMeetings.size() , expected);
    }
}