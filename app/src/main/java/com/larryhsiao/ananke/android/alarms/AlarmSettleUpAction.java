package com.larryhsiao.ananke.android.alarms;

import android.app.AlarmManager;
import android.content.Context;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.clotho.Action;

import java.util.Calendar;

/**
 * Action to setup by given {@link com.larryhsiao.ananke.alarms.Alarm} to {@link android.app.AlarmManager}.
 */
public class AlarmSettleUpAction implements Action {
    private final Context context;
    private final Alarm alarm;
    private final AlarmManager manager;

    public AlarmSettleUpAction(Context context, Alarm alarm, AlarmManager manager) {
        this.context = context;
        this.alarm = alarm;
        this.manager = manager;
    }

    @Override
    public void fire() {
        Calendar alarmCalendar = Calendar.getInstance();
        manager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmCalendar.getTime().getTime(),
            new AlarmIntentSrc(context, alarm).value()
        );
    }
}
