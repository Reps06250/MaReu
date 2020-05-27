package com.example.maru.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.UI.AddMeeting.AddMeetingActivity;
import com.example.maru.UI.AddMeeting.RoomDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

public class MeetingListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FloatingActionButton fab;
    Calendar calendar;
    int year,month,day;
    long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_list);
        this.configureToolbar();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR) ;
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        fab = findViewById(R.id.add_meeting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMeetingActivity.navigate(MeetingListActivity.this);
            }
        });
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_meeting_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.by_rooms){
            RoomDialogFragment roomDialogFragment = new RoomDialogFragment(true);
            roomDialogFragment.show(getSupportFragmentManager(), "roomPicker");
        }
        else if(item.getItemId() == R.id.by_Date){
            if(item.isChecked()){
                item.setChecked(false);
                EventBus.getDefault().post(new LaunchDialogEvent("dateFilter",0));
            }
            else{
                item.setChecked(true);
                DatePickerDialog dpd = DatePickerDialog.newInstance(MeetingListActivity.this, year, month, day);
                dpd.setTitle("Date de la réunion");
                dpd.setMinDate(calendar);
//                dpd.setDisabledDays(mApiService.getDisabledDays());
                dpd.show(getSupportFragmentManager(), "DatePickerDialog");
            }
        }
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(new LaunchDialogEvent("dateFilter", date));
    }
}
