package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.Alarms;

import java.sql.Connection;
import java.util.Map;
import java.util.stream.Collectors;

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
        return new QueriedAlarmsSrc(
            new AllAlarmRes(connection)
        ).value().stream().collect(
            Collectors.toMap(Alarm::id, alarm -> alarm)
        );
    }

    @Override
    public void update(Alarm alarm) {
        new AlarmUpdating(connection, alarm).fire();
    }

    @Override
    public Alarm create(Alarm alarm) {
        return new CreatedAlarmSrc(
            connection, alarm
        ).value();
    }

    @Override
    public void deleteById(long id) {
        new AlarmDeletionById(connection, id).fire();
    }
}
