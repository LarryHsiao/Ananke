package com.larryhsiao.ananke.alarms;

import java.util.Date;

/**
 * Wrapped Alarm.
 */
public class WrappedAlarm implements Alarm{
    private final Alarm alarm;

    public WrappedAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public long id() {
        return alarm.id();
    }

    @Override
    public Date time() {
        return alarm.time();
    }

    @Override
    public boolean enabled() {
        return alarm.enabled();
    }
}
