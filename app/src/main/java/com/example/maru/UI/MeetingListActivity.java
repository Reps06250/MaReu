package com.example.maru.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.UI.AddMeeting.AddMeetingActivity;
import com.example.maru.UI.AddMeeting.RoomDialogFragment;
import com.example.maru.di.DI;
import com.example.maru.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import de.greenrobot.event.EventBus;

public class MeetingListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FloatingActionButton fab;
    Calendar calendar;
    private int year,month,day;
    private long date;
    private MeetingApiService apiService = DI.getMeetingApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService.resetMeetings();
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
        if(item.getItemId() == R.id.by_rooms){                                             // Ouvre RoomDialog pour selectionner la salle a filtrer
            RoomDialogFragment roomDialogFragment = new RoomDialogFragment(true);
            roomDialogFragment.show(getSupportFragmentManager(), "roomPicker");
        }
        else if(item.getItemId() == R.id.by_Date){                                         // Si checké (donc filtré), renvoie un Long à 0 pour dire de ne plus filtrer
            if(item.isChecked()){
                item.setChecked(false);
                EventBus.getDefault().post(new LaunchDialogEvent("dateFilter",0));
            }
            else{
                item.setChecked(true);                                                     // sinon ouvre le datePicker pour choisir la date du filtre
                DatePickerDialog dpd = DatePickerDialog.newInstance(MeetingListActivity.this, year, month, day);
                dpd.setTitle(getString(R.string.date_de_la_reunion));
                dpd.setDisabledDays(apiService.getDisabledDays());
                dpd.show(getSupportFragmentManager(), "DatePickerDialog");
            }
        }
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        try {
            date = Objects.requireNonNull(new SimpleDateFormat(getString(R.string.patern_dd_MM_yyyy)).parse(dateString)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(new LaunchDialogEvent("dateFilter", date)); // renvoie la date à filtrer
    }
}
