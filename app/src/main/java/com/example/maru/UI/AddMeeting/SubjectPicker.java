package com.example.maru.UI.AddMeeting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.databinding.ActivityAddMeetingBinding;
import com.example.maru.databinding.FragmentSubjectDialogBinding;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectPicker extends DialogFragment implements View.OnClickListener{

    private FragmentSubjectDialogBinding binding;
    private String subject = "";

    public SubjectPicker(String subject) {
        this.subject = subject;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         binding = FragmentSubjectDialogBinding.inflate(inflater, container, false);
         View view = binding.getRoot();
         if(!subject.equals(""))binding.subjectEt.setText(subject);
         binding.okSubjectBt.setOnClickListener(this);
         return view;
    }

    @Override
    public void onClick(View v) {
        if(!binding.subjectEt.getText().toString().equals("")) subject = binding.subjectEt.getText().toString();
        EventBus.getDefault().post(new LaunchDialogEvent("subject",subject));
        dismiss();
    }
}
