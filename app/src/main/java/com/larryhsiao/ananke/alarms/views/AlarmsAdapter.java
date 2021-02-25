package com.larryhsiao.ananke.alarms.views;

import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.larryhsiao.ananke.R;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.WrappedAlarm;
import com.larryhsiao.ananke.views.WeekDayToggle;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Adapter for show Alarm list.
 */
public class AlarmsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public AlarmsAdapter(ClickListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface ClickListener {
        void onItemUpdated(Alarm item);

        void onItemRemoved(Alarm item);
    }

    private final ClickListener changeListener;
    private final List<Alarm> alarms = new ArrayList<>();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    );

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_alarm,
                parent,
                false
            )
        ) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Alarm item = alarms.get(position);
        final TextView timeTextView = holder.itemView.findViewById(R.id.itemAlarm_time);
        final Calendar itemCalendar = Calendar.getInstance();
        itemCalendar.set(Calendar.HOUR_OF_DAY, item.hour());
        itemCalendar.set(Calendar.MINUTE, item.minute());
        timeTextView.setText(timeFormat.format(itemCalendar.getTime()));
        timeTextView.setOnClickListener(v -> new TimePickerDialog(
            v.getContext(),
            (view, hourOfDay, minute) -> {
                updateAlarm(new WrappedAlarm(alarms.get(position)) {
                    @Override
                    public int hour() {
                        return hourOfDay;
                    }

                    @Override
                    public int minute() {
                        return minute;
                    }
                });
                notifyItemChanged(position);
            },
            itemCalendar.get(Calendar.HOUR_OF_DAY),
            itemCalendar.get(Calendar.MINUTE),
            true
        ).show());

        final CheckBox enabledCbx = holder.itemView.findViewById(R.id.itemAlarm_enabled);
        enabledCbx.setChecked(item.enabled());
        enabledCbx.setOnCheckedChangeListener((buttonView, isChecked) ->
            updateAlarm(new WrappedAlarm(alarms.get(holder.getAdapterPosition())) {
                @Override
                public boolean enabled() {
                    return isChecked;
                }
            })
        );

        final View deleteButton = holder.itemView.findViewById(R.id.itemAlarm_delete);
        deleteButton.setOnClickListener(v -> {
            alarms.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            changeListener.onItemRemoved(item);
        });

        final WeekDayToggle weekDayToggle = holder.itemView.findViewById(
            R.id.itemAlarm_weekdayToggle
        );
        weekDayToggle.loadToggleState(item.repetition());
        weekDayToggle.setOnStateChangeListener(state ->
            updateAlarm(new WrappedAlarm(alarms.get(holder.getAdapterPosition())) {
                @Override
                public int repetition() {
                    return state;
                }
            })
        );
    }

    private void updateAlarm(Alarm newAlarm) {
        alarms.replaceAll(alarm -> {
            if (alarm.id() == newAlarm.id()) {
                return newAlarm;
            }
            return alarm;
        });
        changeListener.onItemUpdated(newAlarm);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    /**
     * Load all alarms.
     */
    public void loadAlarms(Map<Long, Alarm> newAlarms) {
        alarms.clear();
        alarms.addAll(newAlarms.values());
        notifyDataSetChanged();
    }

    /**
     * Append a {@link Alarm} to list.
     */
    public void newAlarm(Alarm alarm) {
        alarms.add(alarm);
        notifyItemInserted(alarms.size() - 1);
    }
}
