package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.clotho.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Alarm deletion.
 */
public class AlarmDeletionById implements Action {
    private final Connection connection;
    private final Alarm alarm;

    public AlarmDeletionById(Connection connection, Alarm alarm) {
        this.connection = connection;
        this.alarm = alarm;
    }

    @Override
    public void fire() {
        try (final PreparedStatement stmt = connection.prepareStatement(
            "DELETE FROM ALARMS WHERE ID=?1"
        )) {
            stmt.setLong(1, alarm.id());
            stmt.execute();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
