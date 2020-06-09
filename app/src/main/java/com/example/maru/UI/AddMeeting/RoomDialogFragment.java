package com.example.maru.UI.AddMeeting;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.Utils.CheckValidity;
import com.example.maru.Utils.ItemClickSupport;
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomDialogFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private DialogAdapter adapter;
    private MeetingApiService mApiService;
    private long dateAndTime;
    private long duree;
    private boolean fromMenu;
    private List<String> listWithAll = new ArrayList<>();


    public RoomDialogFragment(boolean fromMenu, long dateAndTime, long duree) {
        this.fromMenu = fromMenu;
        this.dateAndTime = dateAndTime;
        this.duree = duree;
    }

    public RoomDialogFragment(boolean fromMenu){
        this.fromMenu = fromMenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        TextView title = (TextView) view.findViewById(R.id.title);
        if(fromMenu)title.setText(R.string.salles);
        else title.setText(R.string.salles_disponibles);
        mApiService = DI.getMeetingApiService();
        listWithAll.add("ALL");
        listWithAll.addAll(mApiService.getRoomsList());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        if(fromMenu)adapter = new DialogAdapter(listWithAll,false);
        else adapter = new DialogAdapter(CheckValidity.getFreeRooms(dateAndTime,duree),false);
        recyclerView.setAdapter(adapter);
        configureOnClickRecyclerView();
        return view;
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        String salle = adapter.getItem(position);
                        if(fromMenu) EventBus.getDefault().post(new LaunchDialogEvent("roomFilter",salle));
                        else EventBus.getDefault().post(new LaunchDialogEvent("room",salle));
                        dismiss();
                    }
                });
    }
}
