package com.larryhsiao.ananke.android.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.larryhsiao.ananke.R;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.android.AnankeViewModelFactory;

/**
 * Fragment that shows all alarms
 */
public class AlarmsFragment extends Fragment implements AlarmsAdapter.ClickListener {
    private AlarmsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(
            this,
            new AnankeViewModelFactory(requireContext())
        ).get(AlarmsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.page_alarms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final AlarmsAdapter adapter = new AlarmsAdapter(this);
        final RecyclerView recyclerView = view.findViewById(R.id.alarms_recyclerView);
        recyclerView.setAdapter(adapter);
        viewModel.alarmsLive().observe(getViewLifecycleOwner(), adapter::loadAlarms);
        viewModel.loadAlarms();
    }

    @Override
    public void onItemUpdated(Alarm item) {
        viewModel.updateAlarm(item);
    }

    @Override
    public void onItemRemoved(Alarm item) {
        viewModel.removeAlarm(item);
    }
}
