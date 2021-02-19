package com.larryhsiao.ananke.alarms;

/**
 * Wrapped Alarm.
 */
public class WrappedAlarm implements Alarm {
    private final Alarm alarm;

    public WrappedAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public long id() {
        return alarm.id();
    }

    @Override
    public int hour() {
        return alarm.hour();
    }

    @Override
    public int minute() {
        return alarm.minute();
    }

    @Override
    public boolean enabled() {
        return alarm.enabled();
    }
}
