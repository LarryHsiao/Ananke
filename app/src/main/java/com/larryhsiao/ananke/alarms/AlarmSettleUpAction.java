package com.larryhsiao.ananke.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import com.larryhsiao.clotho.Action;

/**
 * Action to setup by given {@link Alarm} to {@link AlarmManager}.
 */
public class AlarmSettleUpAction implements Action {
    private final Context context;
    private final Alarm alarm;
    private final AlarmManager manager;

    public AlarmSettleUpAction(Context context, Alarm alarm) {
        this.context = context;
        this.alarm = alarm;
        this.manager = ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));
    }

    @Override
    public void fire() {
        final PendingIntent pendingIntent = new AlarmIntentSrc(context, alarm).value();
        if (alarm.enabled()) {
            manager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                    new NextAlarmCalendarSrc(
                        alarm,
                        System::currentTimeMillis
                    ).value()
                        .getTime()
                        .getTime(),
                pendingIntent
            );
        } else {
            manager.cancel(pendingIntent);
        }
    }
}
