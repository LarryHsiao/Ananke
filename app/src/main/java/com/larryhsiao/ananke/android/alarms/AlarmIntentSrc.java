package com.larryhsiao.ananke.android.alarms;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.clotho.Source;

/**
 * Source to build {@link PendingIntent} for trigger a alarm.
 */
public class AlarmIntentSrc implements Source<PendingIntent> {
    private final Context context;
    private final Alarm alarm;

    public AlarmIntentSrc(Context context, Alarm alarm) {
        this.context = context;
        this.alarm = alarm;
    }

    @Override
    public PendingIntent value() {
        final Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(String.valueOf(alarm.id()));
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            0
        );
    }
}
