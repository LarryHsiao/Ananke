package com.larryhsiao.ananke.alarms;

import android.app.AlarmManager;
import android.content.Context;
import com.larryhsiao.clotho.Action;

/**
 * Action to cancel a given {@link Alarm}'s setup in {@link AlarmManager}.
 */
public class AlarmCancellation implements Action {
    private final Context context;
    private final Alarm alarm;
    private final AlarmManager manager;

    public AlarmCancellation(Context context, Alarm alarm) {
        this.context = context;
        this.alarm = alarm;
        this.manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void fire() {
        manager.cancel(new AlarmIntentSrc(context, alarm).value());
    }
}
