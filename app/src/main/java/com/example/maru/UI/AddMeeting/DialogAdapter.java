package com.example.maru.UI.AddMeeting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import java.util.List;

import de.greenrobot.event.EventBus;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DureeViewHolder>{

    private List<String> liste;
    private boolean cross;

    // Cet adapter sert pour les rv des classes DureePicker et RoomDialogPicker

    public DialogAdapter(List<String> liste, boolean cross) {
        this.cross = cross;
        this.liste = liste;
    }

    @NonNull
    @Override
    public DureeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(cross) view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participants_item, parent, false);
        else view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new DureeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DureeViewHolder holder, int position) {
        String str = liste.get(position);
        holder.item.setText(str);
        if(cross){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new LaunchDialogEvent("deleteParticipant", str));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }


    public static class DureeViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        ImageButton imageButton;
        public DureeViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            imageButton = itemView.findViewById(R.id.cross);
        }
    }

    public String getItem(int position){
        return liste.get(position);
    }
}
