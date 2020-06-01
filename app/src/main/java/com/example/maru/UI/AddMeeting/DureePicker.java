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
import com.example.maru.Utils.ItemClickSupport;
import com.example.maru.di.DI;
import com.example.maru.service.MeetingApiService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class DureePicker extends DialogFragment {

    private RecyclerView recyclerView;
    private DialogAdapter adapter;
    private MeetingApiService mApiService;

    public DureePicker() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(R.string.duree_de_le_reunion);
        mApiService = DI.getMeetingApiService();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        adapter = new DialogAdapter(mApiService.getListeDesDurees());
        recyclerView.setAdapter(adapter);
        configureOnClickRecyclerView();
        return view;
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        long duree = (position + 1) * mApiService.getMinuteIntervalLong();
                        EventBus.getDefault().post(new LaunchDialogEvent("duree", "Pendant :    " + adapter.getItem(position), duree));
                        dismiss();
                    }
                });
    }
}
