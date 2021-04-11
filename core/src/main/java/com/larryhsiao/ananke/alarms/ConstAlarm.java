package com.larryhsiao.ananke.alarms;

/**
 * Constant {@link Alarm}.
 */
public class ConstAlarm implements Alarm {
    private final long id;
    private final int hour;
    private final int minute;
    private final boolean enabled;
    private final int repetition;

    public ConstAlarm(long id, int hour, int minute, boolean enabled) {
        this(id, hour, minute, enabled, -1); // -1 for disable reception by default.
    }

    public ConstAlarm(long id, int hour, int minute, boolean enabled, int repetition) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.enabled = enabled;
        this.repetition = repetition;
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public int hour() {
        return hour;
    }

    @Override
    public int minute() {
        return minute;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public int repetition() {
        return repetition;
    }
}
