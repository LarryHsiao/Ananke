package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.ConstAlarm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.sql.Connection;

/**
 * Test for {@link AnankeDbConn}.
 * Check if database is accessible and migrated correctly.
 */
class AnankeDbConnTest {
    /**
     * Check if the Alarms can do insert.
     */
    @Test
    void normalCase() throws Exception {
        try (Connection conn = new AnankeDbConn(
            Files.createTempFile("prefix", "suffix").toFile()
        ).value()) {
            new CreatedAlarmSrc(
                conn,
                new ConstAlarm(-1, 10, 30, true)
            ).value();
            Assertions.assertEquals(
                1,
                new QueriedAlarmsSrc(new AllAlarmRes(conn)).value().size()
            );
        }
    }
}