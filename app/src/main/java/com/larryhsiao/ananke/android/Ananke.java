package com.larryhsiao.ananke.android;

import android.app.Application;
import com.larryhsiao.ananke.alarms.Alarms;
import com.larryhsiao.ananke.alarms.ConstAlarm;
import com.larryhsiao.ananke.alarms.MemoryAlarms;

/**
 * Application object.
 */
public class Ananke extends Application {
    private final Alarms alarms = new MemoryAlarms();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Alarms getAlarms() {
        if (alarms.all().size() == 0) {
            alarms.create(new ConstAlarm(1, 10, 0, true));
            alarms.create(new ConstAlarm(2, 9, 0, false));
        }
        return alarms;
    }
}
