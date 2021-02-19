package com.larryhsiao.ananke.alarms;

import java.util.*;

/**
 * In-memory Alarms.
 */
public class MemoryAlarms implements Alarms {
    private final Map<Long, Alarm> alarms = new LinkedHashMap<>();
    private long lastId = 0;

    @Override
    public Map<Long, Alarm> all() {
        return alarms;
    }

    @Override
    public void update(Alarm alarm) {
        alarms.put(alarm.id(), alarm);
    }

    @Override
    public Alarm create(Alarm alarm) {
        final long newId = lastId + 1;
        lastId = newId;
        final Alarm newAlarm = new WrappedAlarm(alarm) {
            @Override
            public long id() {
                return newId;
            }
        };
        alarms.put(newId, newAlarm);
        return newAlarm;
    }

    @Override
    public void deleteById(long id) {
        alarms.remove(id);
    }
}
