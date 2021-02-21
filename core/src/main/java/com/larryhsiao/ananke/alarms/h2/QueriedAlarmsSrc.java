package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.ConstAlarm;
import com.larryhsiao.clotho.Source;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Source to build list of {@link Alarm} from {@link java.sql.ResultSet}.
 */
public class QueriedAlarmsSrc implements Source<List<Alarm>> {
    private final Source<ResultSet> queryResult;

    public QueriedAlarmsSrc(Source<ResultSet> queryResult) {
        this.queryResult = queryResult;
    }

    @Override
    public List<Alarm> value() {
        try (ResultSet resultSet = queryResult.value()) {
            final List<Alarm> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new ConstAlarm(
                    resultSet.getLong("id"),
                    resultSet.getInt("hour"),
                    resultSet.getInt("minute"),
                    resultSet.getBoolean("enabled")
                ));
            }
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
