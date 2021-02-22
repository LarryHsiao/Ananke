package com.larryhsiao.ananke;

import android.app.Application;
import com.larryhsiao.ananke.alarms.Alarms;
import com.larryhsiao.ananke.alarms.h2.AnankeDbConn;
import com.larryhsiao.ananke.alarms.h2.H2Alarms;
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
    private Alarms alarms;

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(this);
        db = new SingleConn(
            new AnankeDbConn(new File(getFilesDir(), "Ananke"))
        );
        alarms = new H2Alarms(db.value());
    }

    public Alarms getAlarms() {
        return alarms;
    }
}
