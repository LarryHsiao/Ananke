package com.larryhsiao.ananke.alarms;

import java.util.Map;

/**
 * Alarms recorded in Ananke.
 */
public interface Alarms {
    /**
     * All of the alarms
     */
    Map<Long, Alarm> all();

    /**
     * Update a exist {@link Alarm}.
     */
    void update(Alarm alarm);

    /**
     * Create a alarm.
     *
     * @return New alarm with generated id.
     */
    Alarm create(Alarm alarm);

    /**
     * Delte a alram by its id.
     */
    void deleteById(long id);
}
