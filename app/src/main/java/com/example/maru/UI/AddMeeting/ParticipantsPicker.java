package com.example.maru.UI.AddMeeting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.databinding.FragmentParticipantsDialogBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParticipantsPicker extends DialogFragment implements View.OnClickListener{

    private FragmentParticipantsDialogBinding binding;
    private List<String> listeDesParticipants;
    private String emailPattern;
    private DialogAdapter adapter;

    public ParticipantsPicker(List<String> listeDesParticipants) {
        this.listeDesParticipants = listeDesParticipants;
     }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        binding = FragmentParticipantsDialogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        adapter = new DialogAdapter(listeDesParticipants, true);
        binding.recyclerView.setAdapter(adapter);
        binding.okParticipantsBt.setOnClickListener(this);
        binding.addParticipant.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        String participant = binding.participantEt.getText().toString();
        switch(v.getId()){
            case R.id.add_participant :
                if(checkValidEmail(participant)){
                    listeDesParticipants.add(participant);
                    binding.participantEt.setText("");
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

    public void onEvent(LaunchDialogEvent event) {
        if(event.getDialog().equals("deleteParticipant")) listeDesParticipants.remove(event.getString());
        adapter.notifyDataSetChanged();
    }

}
