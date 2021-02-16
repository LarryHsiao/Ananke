package com.larryhsiao.ananke.alarms;

import java.util.Date;

/**
 * Just Alarm triggered at specific time.
 */
public interface Alarm {

    /**
     * Id of this alarm.
     */
    long id();

    /**
     * The trigger time.
     */
    Date time();

    /**
     * Indicates if this alarm will triggered by the given preset.
     */
    boolean enabled();
}
