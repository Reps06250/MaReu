package com.example.maru;

import android.content.Context;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.maru.UI.AddMeeting.AddMeetingActivity;
import com.example.maru.UI.MeetingListActivity;
import com.example.maru.di.DI;
import com.example.maru.service.MeetingApiService;
import com.example.maru.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.maru.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListActivityInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.maru", appContext.getPackageName());
    }

    private MeetingApiService ApiService = DI.getNewInstanceApiService();
    private MeetingListActivity mActivity;

    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityRule =
            new ActivityTestRule<>(MeetingListActivity.class);


    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        int index = 0;
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.fragment_meeting_list_recycler_view))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        int position = 1;
        int ITEMS_COUNT = 4;
        // Given : We remove the element at position 1
        onView(withId(R.id.fragment_meeting_list_recycler_view)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.fragment_meeting_list_recycler_view))
                .perform(actionOnItemAtPosition(position, new DeleteViewAction()));
        // Then : the number of element is 3
        onView(withId(R.id.fragment_meeting_list_recycler_view)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void ClickOpenAddActivity() {
        Intents.init();
        // Click on button
        onView(withId(R.id.add_meeting)).perform(click());
        // Check AddMeetingActivity View is displayed
        intended(hasComponent(AddMeetingActivity.class.getName()));
    }

    @Test
    public void RoomFilterIsDisplay() {
        // Make sure we hide the contextual action bar.
        onView(withId(R.id.toolbar))
                .perform(click());
        onView(withId(R.id.menu_filter))
                .perform(click());
        // Click the item.
        onView(withText("Filter By Room"))
                .perform(click());
        // Verify that we have really clicked on the icon by checking
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void RoomFilterOk() {
        int position = 5;
        onView(withId(R.id.toolbar))
                .perform(click());
        onView(withId(R.id.menu_filter))
                .perform(click());
        // Click the item.
        onView(withText("Filter By Room"))
                .perform(click());
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition(position, click()));
        if(position == 0)onView(ViewMatchers.withId(R.id.fragment_meeting_list_recycler_view)).check(withItemCount(ApiService.getMeetings().size()));
        else {
            int itemCountExpected = ApiService.getRoomsFilteredMeetings(ApiService.getRoomsList().get(position -1)).size();
            onView(ViewMatchers.withId(R.id.fragment_meeting_list_recycler_view)).check(withItemCount(itemCountExpected));
        }
    }

    @Test
    public void DateFilterIsDisplay() {
        // Make sure we hide the contextual action bar.
        onView(withId(R.id.toolbar))
                .perform(click());
        onView(withId(R.id.menu_filter))
                .perform(click());
        // Click the item.
        onView(withText("Filter By Date"))
                .perform(click());
        // Verify that we have really clicked on the icon by checking
        onView(withText("DATE DE LA RÉUNION")).check(matches(isDisplayed()));
    }

    @Test
    public void DateFilterOk() {

    }

    @Test
    public void DoubleFilterOk() {

    }
}
