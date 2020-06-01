package com.example.maru.UI.AddMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.Utils.CheckValidity;
import com.example.maru.databinding.ActivityAddMeetingBinding;
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.service.MeetingApiService;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import de.greenrobot.event.EventBus;

public class AddMeetingActivity extends AppCompatActivity implements View.OnClickListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ActivityAddMeetingBinding binding;
    MeetingApiService mApiService;
    Calendar calendar;
    private String dateString;
    private long date = 0;
    private long time = 0;
    private int launchOrNot;
    int year,month,day,hour,minute,second;
    boolean h24 = true;
    private Timepoint[] timepoints;

    private long dateAndTime;
    private long duree = 0;
    private String room;
    private String subject = "";
    private String listeDesParticipants = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR) ;
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        mApiService = DI.getMeetingApiService();
        binding.dateBt.setOnClickListener(this);
        binding.roomBt.setOnClickListener(this);
        binding.dureeBt.setOnClickListener(this);
        binding.timeBt.setOnClickListener(this);
        binding.subjectBt.setOnClickListener(this);
        binding.participantsBt.setOnClickListener(this);
        binding.okFab.setOnClickListener(this);
        binding.participantsTv.setSelected(true);
        launchDate();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        binding.dateBt.setText(getString(R.string.date_de_la_reunion) + dateString);
        try {
            date = Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy").parse(dateString)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.dateBt.setBackgroundResource(R.drawable.border2);
        launchDuree();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        if(hourOfDay >=10 && minute < 10) binding.timeBt.setText(getString(R.string.heure_de_la_reunion) + hourOfDay + "H0" + minute);
        else if (hourOfDay < 10 && minute > 0) binding.timeBt.setText(String.format(Locale.ENGLISH,getString(R.string.heure_de_la_reunion1), hourOfDay, minute));
        else if (hourOfDay < 10 && minute < 10) binding.timeBt.setText(getString(R.string.heure_de_la_reunion2) + hourOfDay + "H0" + minute);
        else binding.timeBt.setText(getString(R.string.heure_de_le_reunion3) + hourOfDay + "H" + minute);
        try {
            time = Objects.requireNonNull(new SimpleDateFormat("HH:mm").parse(hourOfDay + ":" + minute)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateAndTime = date + time;
        binding.timeBt.setBackgroundResource(R.drawable.border2);
        launchRooms();
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public void onEvent(LaunchDialogEvent event){
        String dialog = event.getDialog();
        switch(dialog){
            case "duree" :
                binding.dureeBt.setBackgroundResource(R.drawable.border2);
                binding.dureeBt.setText(event.getString());
                duree = event.getL();
                launchTime();
                break;
            case "room" :
                binding.roomBt.setBackgroundResource(R.drawable.border2);
                room = event.getString();
                binding.roomBt.setText(getString(R.string.salle_de_la_reunion) + room);
                if(launchOrNot!= 1) launchSubject();
                break;
            case "subject" :
                subject = event.getString();
                if(!subject.equals(""))binding.subjectBt.setBackgroundResource(R.drawable.border2);
                binding.subjectBt.setText(getString(R.string.sujet) + subject);
                if(launchOrNot!= 1) launchParticipants();
                break;
            case "participants" :
                launchOrNot = event.getData();
                listeDesParticipants = event.getString();
                if(!listeDesParticipants.equals(""))binding.participantsBt.setBackgroundResource(R.drawable.border2);
                binding.participantsTv.setText(listeDesParticipants);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_bt : launchDate();break;
            case R.id.room_bt : launchRooms(); break;
            case R.id.time_bt : launchTime(); break;
            case R.id.duree_bt : launchDuree(); break;
            case R.id.subject_bt : launchSubject(); break;
            case R.id.participants_bt : launchParticipants(); break;
            case R.id.ok_fab :
                if(date == 0) Toast.makeText(getApplicationContext(), R.string.toast_date,Toast.LENGTH_SHORT).show();
                else if(duree == 0) Toast.makeText(getApplicationContext(), R.string.toast_duree,Toast.LENGTH_SHORT).show();
                else if(time == 0) Toast.makeText(getApplicationContext(), R.string.toast_heure,Toast.LENGTH_SHORT).show();
                else if(room == null) Toast.makeText(getApplicationContext(), R.string.toast_room,Toast.LENGTH_SHORT).show();
                else{
                    Meeting meeting = new Meeting(room, subject, listeDesParticipants, dateAndTime,duree, mApiService.getColor());
                    mApiService.createMeeting(meeting);
                    finish();
                }
                break;
        }
    }

    public void launchTime() {
        if(date == 0) Toast.makeText(getApplicationContext(),R.string.toast_date,Toast.LENGTH_SHORT).show();
        else if(duree == 0) Toast.makeText(getApplicationContext(),R.string.toast_duree,Toast.LENGTH_SHORT).show();
        else{
            TimePickerDialog tpd = TimePickerDialog.newInstance(AddMeetingActivity.this, hour, minute, h24);
            tpd.setTitle(getString(R.string.heure_de_la_reunion_title));
            tpd.setMinTime(mApiService.getOpenHour());
            tpd.setMaxTime(mApiService.getClosedHour());
            tpd.setTimeInterval(1,mApiService.getMinuteIntervalInt());
            if(getDisableTimeTab()) tpd.setDisabledTimes(timepoints);
            tpd.show(getSupportFragmentManager(), "TimePickerDialog");
        }
    }

    public void launchDate(){
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(AddMeetingActivity.this, year, month, day);
        dpd.setTitle(getString(R.string.date_de_la_reunion_title));
        dpd.setMinDate(calendar);
        dpd.setDisabledDays(mApiService.getDisabledDays());
        dpd.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    public void launchDuree(){
        if(date == 0) Toast.makeText(getApplicationContext(),R.string.toast_date,Toast.LENGTH_SHORT).show();
        else{
            DureePicker dureePicker = new DureePicker();
            dureePicker.show(getSupportFragmentManager(), "duréePicker");
        }
    }

    public void launchRooms(){
        if(date == 0) Toast.makeText(getApplicationContext(),R.string.toast_date,Toast.LENGTH_SHORT).show();
        else if(duree == 0) Toast.makeText(getApplicationContext(),R.string.toast_duree,Toast.LENGTH_SHORT).show();
        else if(time == 0) Toast.makeText(getApplicationContext(),R.string.toast_heure,Toast.LENGTH_SHORT).show();
        else {
            RoomDialogFragment roomDialogFragment = new RoomDialogFragment(false, dateAndTime, duree);
            roomDialogFragment.show(getSupportFragmentManager(), "roomPicker");
        }
    }
    public void launchSubject(){
        SubjectPicker subjectPicker = new SubjectPicker(subject);
        subjectPicker.show(getSupportFragmentManager(), "subjectPicker");
    }

    public void launchParticipants(){
        ParticipantsPicker participantsPicker = new ParticipantsPicker(listeDesParticipants);
        participantsPicker.show(getSupportFragmentManager(), "participantsPicker");
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }

    public boolean getDisableTimeTab() {
        SimpleDateFormat tdfH = new SimpleDateFormat("HH");
        SimpleDateFormat tdfM = new SimpleDateFormat("mm");
        List<Long> disableTimeList = new ArrayList<>();
        for(long mDate = date+mApiService.getOpenHourLong(); mDate<(date+mApiService.getClosedHourLong()); mDate+=mApiService.getMinuteIntervalLong()) {
            if (CheckValidity.getFreeRooms(mDate, duree).size() == 0)
                disableTimeList.add(mDate);
        }
        if(disableTimeList.size()!=0){
            timepoints = new Timepoint[disableTimeList.size()];
            int index = 0;
            for(Long l : disableTimeList) {
                int hour = Integer.parseInt(tdfH.format(l));
                int minute = Integer.parseInt(tdfM.format(l));
                timepoints[index] = new Timepoint(hour, minute);
                index++;
            }
            return true;
        }
        else return false;
    }
}



