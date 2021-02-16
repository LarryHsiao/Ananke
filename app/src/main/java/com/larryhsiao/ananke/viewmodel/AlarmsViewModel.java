package com.larryhsiao.ananke.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.Alarms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * ViewModel of Alarms.
 */
public class AlarmsViewModel extends ViewModel {
    private final List<Alarm> alarmList = new ArrayList<>();
    private final Alarms alarms;

    private final MutableLiveData<List<Alarm>> alarmsLive = new MutableLiveData<>();

    public AlarmsViewModel(Alarms alarms) {
        this.alarms = alarms;
    }

    /**
     * The live data of Alarm for the list.
     */
    public LiveData<List<Alarm>> alarmsLive() {
        return alarmsLive;
    }

    /**
     * Load the list of alarms.
     */
    public void loadAlarms() {
        CompletableFuture.runAsync(() -> {
            alarmList.clear();
            alarmList.addAll(alarms.all());
            alarmsLive.postValue(alarmList);
        });
    }

    /**
     * Update given alarm.
     */
    public CompletableFuture<Void> updateAlarm(Alarm alarm) {
        return CompletableFuture.runAsync(() -> {
            alarms.update(alarm);
        });
    }
}
