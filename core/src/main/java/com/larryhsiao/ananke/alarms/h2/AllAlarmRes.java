package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.clotho.Source;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Source to build {@link ResultSet} from querying all alarms.
 */
public class AllAlarmRes implements Source<ResultSet> {
    private final Connection connection;

    public AllAlarmRes(Connection connection) {this.connection = connection;}

    @Override
    public ResultSet value() {
        try {
            return connection.createStatement().executeQuery(
                // language=H2
                "SELECT * FROM ALARMS;"
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
