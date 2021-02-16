package com.larryhsiao.ananke.alarms;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory Alarms.
 */
public class MemoryAlarms implements Alarms {
    private final HashMap<Long, Alarm> alarms = new HashMap<>();
    private long lastId = 0;

    @Override
    public List<Alarm> all() {
        return new ArrayList<>(alarms.values());
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
