package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.clotho.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Action to update given alarm.
 */
public class AlarmUpdating implements Action {
    private final Connection connection;
    private final Alarm alarm;

    public AlarmUpdating(Connection connection, Alarm alarm) {
        this.connection = connection;
        this.alarm = alarm;
    }

    @Override
    public void fire() {
        try (PreparedStatement stmt = connection.prepareStatement(
            // language=H2
            "UPDATE ALARMS SET HOUR=?1, MINUTE=?2, ENABLED=?3 " +
                "WHERE ID=?4;"
        )) {
            stmt.setInt(1, alarm.hour());
            stmt.setInt(2, alarm.minute());
            stmt.setBoolean(3, alarm.enabled());
            stmt.setLong(4, alarm.id());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
