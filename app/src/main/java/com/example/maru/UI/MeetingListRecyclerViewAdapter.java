package com.example.maru.UI;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    @Override
    public void onBindViewHolder(@NonNull MeetingListViewHolder holder, int position) {
        Meeting meeting = meetingsList.get(position);
        holder.details.setText("Réunion " + meeting.getRoom() +" - "+ getTime(meeting)+" - "+meeting.getSubject());
        holder.participants.setText(meeting.getParticipants());
        holder.circle.setCardBackgroundColor(Color.parseColor(meeting.getColor()));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new LaunchDialogEvent("delete", meeting));
            }
        });
        if(position == this.position){
            holder.details.setSelected(true);
            holder.participants.setSelected(true);
        }
        else{
            holder.details.setSelected(false);
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
        CardView circle;
        ImageButton delete;
        public MeetingListViewHolder(@NonNull View itemView) {
            super(itemView);
                 details = itemView.findViewById(R.id.details);
                 participants = itemView.findViewById(R.id.participants);
                 circle = itemView.findViewById((R.id.circle));
                 delete = itemView.findViewById(R.id.delete_bt);
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
