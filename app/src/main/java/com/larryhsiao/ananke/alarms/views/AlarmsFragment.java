package com.larryhsiao.ananke.alarms.views;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.larryhsiao.ananke.R;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.AnankeViewModelFactory;
import com.larryhsiao.ananke.alarms.AlarmSettleUpAction;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Fragment that shows all alarms
 */
public class AlarmsFragment extends Fragment implements AlarmsAdapter.ClickListener {
    private final AlarmsAdapter adapter = new AlarmsAdapter(this);
    private AlarmsViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(
            this,
            new AnankeViewModelFactory(requireContext())
        ).get(AlarmsViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(
        @NonNull @NotNull Menu menu,
        @NonNull @NotNull MenuInflater inflater
    ) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.alarms, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alarmsMenu_new:
                viewModel.createAlarm().thenApply(
                    alarm -> {
                        requireActivity().runOnUiThread(() ->
                            adapter.newAlarm(alarm)
                        );
                        return null;
                    }
                );
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        final RecyclerView recyclerView = view.findViewById(R.id.alarms_recyclerView);
        recyclerView.setAdapter(adapter);
        viewModel.alarmsLive().observe(getViewLifecycleOwner(), adapter::loadAlarms);
        viewModel.loadAlarms();
    }

    @Override
    public void onItemUpdated(Alarm item) {
        viewModel.updateAlarm(item);
        new AlarmSettleUpAction(requireContext(), item).fire();
    }

    @Override
    public void onItemRemoved(Alarm item) {
        viewModel.removeAlarm(item);
        new AlarmSettleUpAction(requireContext(), item).fire();
    }
}
