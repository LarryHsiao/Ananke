package com.larryhsiao.ananke.alarms.h2;

import com.larryhsiao.clotho.Source;
import com.larryhsiao.clotho.database.h2.EmbedH2Conn;
import com.larryhsiao.clotho.source.ConstSource;
import org.flywaydb.core.Flyway;

import java.io.File;
import java.sql.Connection;

/**
 * Source to build a SQL connection to local H2.
 */
public class AnankeDbConn implements Source<Connection> {
    private final File dbFile;

    public AnankeDbConn(File dbFile) {
        this.dbFile = dbFile;
    }

    @Override
    public Connection value() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Flyway flyway = Flyway.configure()
            .baselineOnMigrate(true)
//            .baselineVersion("0")
            .dataSource("jdbc:h2:" +
                    dbFile.getAbsolutePath() +
                    ";FILE_LOCK=FS" +
                    ";PAGE_SIZE=1024" +
                    ";CACHE_SIZE=8192",
                null,
                null
            ).load();
        flyway.migrate();
        return new EmbedH2Conn(
            new ConstSource<>(dbFile)
        ).value();
    }
}
