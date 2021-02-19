package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.Alarms;

import java.sql.Connection;
import java.util.Map;

/**
 * Alarms in H2
 */
public class H2Alarms implements Alarms {
    private final Connection connection;

    public H2Alarms(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Map<Long, Alarm> all() {
        return null;
    }

    @Override
    public void update(Alarm alarm) {

    }

    @Override
    public Alarm create(Alarm alarm) {
        return null;
    }

    @Override
    public void deleteById(long id) {
    }
}
