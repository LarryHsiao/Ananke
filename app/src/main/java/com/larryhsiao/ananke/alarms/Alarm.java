package com.larryhsiao.ananke.alarms;


/**
 * Just Alarm triggered at specific time.
 */
public interface Alarm {

    /**
     * Id of this alarm.
     */
    long id();

    /**
     * Triggered hour, 0-23.
     */
    int hour();

    /**
     * Trigger minute of the hour.
     */
    int minute();

    /**
     * Indicates if this alarm will triggered by the given preset.
     */
    boolean enabled();
}
