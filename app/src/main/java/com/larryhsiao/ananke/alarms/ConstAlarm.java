package com.larryhsiao.ananke.alarms;

import java.util.Calendar;
import java.util.Date;

/**
 * Constant {@link Alarm}.
 */
public class ConstAlarm implements Alarm {
    private final long id;
    private final int time;
    private final boolean enabled;

    public ConstAlarm(long id, int time, boolean enabled) {
        this.id = id;
        this.time = time;
        this.enabled = enabled;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public Date time() {
        final Calendar calendarObj = Calendar.getInstance();
        calendarObj.set(Calendar.YEAR, 2000);
        calendarObj.set(Calendar.MONTH, 1);
        calendarObj.set(Calendar.DAY_OF_MONTH, 1);
        calendarObj.set(Calendar.HOUR_OF_DAY, 0);
        calendarObj.set(Calendar.MINUTE, 0);
        calendarObj.set(Calendar.SECOND, 0);
        calendarObj.set(Calendar.MILLISECOND, 0);
        calendarObj.add(Calendar.MILLISECOND, time);
        return calendarObj.getTime();
    }

    @Override
    public boolean enabled() {
        return enabled;
    }
}
