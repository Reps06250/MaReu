package com.example.maru;

import android.content.Intent;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.UI.AddMeeting.AddMeetingActivity;
import com.example.maru.UI.MeetingListActivity;
import com.example.maru.di.DI;
import com.example.maru.service.MeetingApiService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.maru.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.*;


@RunWith(AndroidJUnit4.class)
public class AddMeetingInstrumentedTest {

    private MeetingApiService ApiService = DI.getNewInstanceApiService();
    private AddMeetingActivity mActivity;

    @Rule
    public ActivityTestRule<AddMeetingActivity> mActivityRule =
            new ActivityTestRule<>(AddMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void goodInput() {
        int position = 0;
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(today);

        onView(withText("OK")).perform(click());
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(position, click()));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(position, click()));
        onView(withId(R.id.subject_et)).perform(typeText("TEST"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.ok_subject_bt)).perform(click());
        onView(withId(R.id.participant_et)).perform(typeText("TEST@test.fr"),closeSoftKeyboard());
        onView(withText("ADD")).perform(click());
        onView(ViewMatchers.withId(R.id.participants_tv0)).check(matches(withText("TEST@test.fr ; ")));
        onView(withText("OK")).perform(click());
//        onView(ViewMatchers.withId(R.id.date_bt)).check(matches(withText("Date de la réunion : " + date)));
        onView(ViewMatchers.withId(R.id.date_bt)).check(matches(withText("Date de la réunion : 28/5/2020")));
//        onView(ViewMatchers.withId(R.id.duree_bt)).check(matches(withText("Pendant :    " + ApiService.getListeDesDurees().get(position))));
        onView(ViewMatchers.withId(R.id.duree_bt)).check(matches(withText("Pendant :    00H15")));
        onView(ViewMatchers.withId(R.id.time_bt)).check(matches(withText("Heure de la réunion :   09H15")));
//        onView(ViewMatchers.withId(R.id.room_bt)).check(matches(withText("Salle de réunion :   " + ApiService.getRoomsList().get(position))));
        onView(ViewMatchers.withId(R.id.room_bt)).check(matches(withText("Salle de réunion :   A")));
        onView(ViewMatchers.withId(R.id.subject_bt)).check(matches(withText("SUJET : TEST")));
        onView(ViewMatchers.withId(R.id.participants_bt)).check(matches(withText("PARTICIPANTS DE LA RÉUNION : ")));
        onView(ViewMatchers.withId(R.id.participants_tv)).check(matches(withText("TEST@test.fr ; ")));
    }

    @Test
    public void createNewMeeting() {
        int position = 0;
        int expectedListSize = ApiService.getMeetings().size() + 1;
        onView(withText("OK")).perform(click());
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(position, click()));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(position, click()));
        onView(withId(R.id.subject_et)).perform(typeText("TEST"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.ok_subject_bt)).perform(click());
        onView(withId(R.id.participant_et)).perform(typeText("TEST@test.fr"),closeSoftKeyboard());
        onView(withText("ADD")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.ok_fab)).perform(click());
        assertEquals(ApiService.getMeetings().size(), expectedListSize);
    }
}
