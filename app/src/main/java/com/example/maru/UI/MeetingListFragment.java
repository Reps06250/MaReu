package com.example.maru.UI;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maru.Event.LaunchDialogEvent;
import com.example.maru.R;
import com.example.maru.Utils.ItemClickSupport;
import com.example.maru.databinding.FragmentMeetingListBinding;
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.service.MeetingApiService;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeetingListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MeetingListRecyclerViewAdapter adapter;
    private MeetingApiService mApiService;
    private List<Meeting> meetingsList;
    FragmentMeetingListBinding binding;
    String room = "ALL";
    long date = 0;

    public MeetingListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        meetingsList = mApiService.getMeetings();
        binding = FragmentMeetingListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_meeting_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        configureOnClickRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new MeetingListRecyclerViewAdapter(meetingsList);
        recyclerView.setAdapter(adapter);
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

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.meeting_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Meeting meeting = adapter.getMeeting(position);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    public void onEvent(LaunchDialogEvent event) {
        String dialog = event.getDialog();
        switch (dialog) {
            case "roomFilter":
                room = event.getString();
                break;
            case"dateFilter" :
                date = event.getL();
                break;
            case"delete" :
                mApiService.deleteMeeting(event.getMeeting());
        }
        if(room.equals("ALL") && date == 0) meetingsList = mApiService.getMeetings();
        else if(room.equals("ALL") && date != 0) meetingsList = mApiService.getDateFilteredMeetings(mApiService.getMeetings(), date);
        else if(!room.equals("ALL") && date == 0) meetingsList = mApiService.getRoomsFilteredMeetings(room);
        else meetingsList = mApiService.getDateFilteredMeetings(mApiService.getRoomsFilteredMeetings(room), date);
        refresh();
    }

    public void refresh(){
        adapter = new MeetingListRecyclerViewAdapter(meetingsList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
