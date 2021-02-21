package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.clotho.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Alarm deletion.
 */
public class AlarmDeletionById implements Action {
    private final Connection connection;
    private final long id;

    public AlarmDeletionById(Connection connection, long id) {
        this.connection = connection;
        this.id = id;
    }

    @Override
    public void fire() {
        try (final PreparedStatement stmt = connection.prepareStatement(
            "DELETE FROM ALARMS WHERE ID=?1"
        )) {
            stmt.setLong(1, id);
            stmt.execute();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
