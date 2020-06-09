package com.example.maru.UI;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.model.Meeting;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import de.greenrobot.event.EventBus;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.MeetingListViewHolder> {

    private List<Meeting> meetingsList;
    private int position = -1;

    public MeetingListRecyclerViewAdapter(List<Meeting> meetingsList) {
        this.meetingsList = meetingsList;
    }

    @NonNull
    @Override
    public MeetingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_item, parent, false);
        return new MeetingListViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MeetingListViewHolder holder, int position) {
        Meeting meeting = meetingsList.get(position);
        String participantsString = "";
        for(String string : meeting.getParticipants()) participantsString += (string + "; ");
        holder.details.setText("Réunion " + meeting.getRoom() +" - "+ getTime(meeting)+" - "+meeting.getSubject());
        holder.participants.setText(participantsString);
        holder.gd.setColor(Color.parseColor(meeting.getColor()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new LaunchDialogEvent("delete", meeting));
            }
        });
        if(position == this.position){       // active le défilement
            holder.details.setSelected(true);
            holder.participants.setSelected(true);
        }
        else{
            holder.details.setSelected(false);   // le désactive
            holder.participants.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return meetingsList.size();
    }

    public class MeetingListViewHolder extends RecyclerView.ViewHolder {
        TextView details;
        TextView participants;
        ImageView circle;
        ImageButton delete;
        GradientDrawable gd ;
        public MeetingListViewHolder(@NonNull View itemView) {
            super(itemView);
                 details = itemView.findViewById(R.id.details);
                 participants = itemView.findViewById(R.id.participants);
                 circle = itemView.findViewById((R.id.circle));
                 delete = itemView.findViewById(R.id.delete_bt);
                 gd = (GradientDrawable) circle.getBackground();
        }
    }

    public Meeting getMeeting(int position){
        this.position = position;
        return meetingsList.get(position);
    }

    public String getTime(Meeting meeting){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date(meeting.getDate()));
    }
}
