package com.example.maru;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.UI.AddMeeting.AddMeetingActivity;
import com.example.maru.UI.MeetingListActivity;
import com.example.maru.di.DI;
import com.example.maru.service.MeetingApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
    public void GoodDate() {
    }

    @Test
    public void GoodDuree() {
        
    }

    @Test
    public void GoodTime() {
    }

    @Test
    public void GoodRoom() {
    }

    @Test
    public void GoodSubject() {
    }

    @Test
    public void GoodParticipantList() {
    }
}
