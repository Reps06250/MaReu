package com.example.maru.UI.AddMeeting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.databinding.FragmentParticipantsDialogBinding;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParticipantsPicker extends DialogFragment implements View.OnClickListener{

    private FragmentParticipantsDialogBinding binding;
    private String listeDesParticipants;
    private String emailPattern;

    public ParticipantsPicker(String listeDesParticipants) {
        this.listeDesParticipants = listeDesParticipants;
     }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        binding = FragmentParticipantsDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.okParticipantsBt.setOnClickListener(this);
        binding.addParticipant.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_participant :
                String participant = binding.participantEt.getText().toString();
                if(checkValidEmail(participant)){
                    listeDesParticipants += (participant + " ; ");
                    //binding.participantEt.clearComposingText();
                    binding.participantEt.setText("");
                    binding.participantsTv0.setText(listeDesParticipants);
                }
                break;
            case R.id.ok_participants_bt :
                EventBus.getDefault().post(new LaunchDialogEvent("participants",listeDesParticipants, 1));
                dismiss();
                break;
        }
    }

    private boolean checkValidEmail(String participant){
        if(participant.isEmpty()) {
            Toast.makeText(getContext(),"enter email address",Toast.LENGTH_SHORT).show();
        }else {
            if (participant.trim().matches(emailPattern)) {
                return true;
            } else {
                Toast.makeText(getContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}
