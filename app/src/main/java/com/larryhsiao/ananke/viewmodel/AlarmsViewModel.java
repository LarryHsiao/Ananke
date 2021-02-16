package com.larryhsiao.ananke.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.Alarms;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * ViewModel of Alarms.
 */
public class AlarmsViewModel extends ViewModel {
    private final Map<Long, Alarm> alarmMap = new LinkedHashMap<>();
    private final Alarms alarms;

    private final MutableLiveData<Map<Long, Alarm>> alarmsLive = new MutableLiveData<>();

    public AlarmsViewModel(Alarms alarms) {
        this.alarms = alarms;
    }

    /**
     * The live data of Alarm for the list.
     */
    public LiveData<Map<Long, Alarm>> alarmsLive() {
        return alarmsLive;
    }

    /**
     * Load the list of alarms.
     */
    public void loadAlarms() {
        CompletableFuture.runAsync(() -> {
            alarmMap.clear();
            alarmMap.putAll(alarms.all());
            alarmsLive.postValue(alarmMap);
        });
    }

    /**
     * Update given alarm.
     */
    public CompletableFuture<Void> updateAlarm(Alarm newAlarm) {
        return CompletableFuture.runAsync(() -> {
            alarms.update(newAlarm);
        });
    }

    public void removeAlarm(Alarm alarm) {
        CompletableFuture.runAsync(() -> {
            alarms.deleteById(alarm.id());
            alarmMap.remove(alarm.id());
        });
    }
}
