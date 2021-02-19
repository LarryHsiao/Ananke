package com.larryhsiao.ananke;

import android.app.Application;
import com.larryhsiao.ananke.alarms.Alarms;
import com.larryhsiao.ananke.alarms.ConstAlarm;
import com.larryhsiao.ananke.alarms.MemoryAlarms;
import com.larryhsiao.ananke.alarms.h2.AnankeDbConn;
import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.SingleConn;
import org.flywaydb.core.api.android.ContextHolder;

import java.io.File;
import java.sql.Connection;

/**
 * Application object.
 */
public class Ananke extends Application {
    private Source<Connection> db;
    private final Alarms alarms = new MemoryAlarms();

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(this);
        db = new SingleConn(
            new AnankeDbConn(new File(getFilesDir(), "Ananke"))
        );
    }

    public Connection getConnection() {
        return db.value();
    }

    public Alarms getAlarms() {
        if (alarms.all().size() == 0) {
            alarms.create(new ConstAlarm(1, 10, 0, true));
            alarms.create(new ConstAlarm(2, 9, 0, false));
        }
        return alarms;
    }
}
