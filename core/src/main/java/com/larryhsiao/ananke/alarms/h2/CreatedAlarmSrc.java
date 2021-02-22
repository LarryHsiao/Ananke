package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.WrappedAlarm;
import com.larryhsiao.clotho.Source;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Source to build a {@link Alarm} that just inserted to database.
 */
public class CreatedAlarmSrc implements Source<Alarm> {
    private final Connection connection;
    private final Alarm newAlarm;

    public CreatedAlarmSrc(Connection connection, Alarm newAlarm) {
        this.connection = connection;
        this.newAlarm = newAlarm;
    }

    @Override
    public Alarm value() {
        try (final PreparedStatement stmt = connection.prepareStatement(
            // language=H2
            "INSERT INTO ALARMS(hour, minute, enabled, REPETITION)" +
                "VALUES (?1, ?2, ?3, ?4);",
            RETURN_GENERATED_KEYS
        )) {
            stmt.setInt(1, newAlarm.hour());
            stmt.setInt(2, newAlarm.minute());
            stmt.setBoolean(3, newAlarm.enabled());
            stmt.setInt(4, newAlarm.repetition());
            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Insert failed");
            }

            final ResultSet keys = stmt.getGeneratedKeys();
            if (!keys.next()) {
                throw new IllegalArgumentException("Create alarms failed");
            }
            final long id = keys.getLong(1);
            return new WrappedAlarm(newAlarm) {
                @Override
                public long id() {
                    return id;
                }
            };
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
