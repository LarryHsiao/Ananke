package com.larryhsiao.ananke;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.Alarms;
import com.larryhsiao.ananke.views.MainActivity;
import com.larryhsiao.clotho.dgist.MD5;

import java.util.List;
import java.util.Map;

/**
 * Decorator for manipulating {@link AlarmManager}.
 */
public class AndroidAlarms implements Alarms {
    private final Context context;
    private final Alarms alarms;
    private final AlarmManager alarmManager;

    public AndroidAlarms(Context context, Alarms alarms) {
        this.context = context;
        this.alarms = alarms;
        this.alarmManager = ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));
    }

    @Override
    public Map<Long, Alarm> all() {
        return alarms.all();
    }

    @Override
    public void update(Alarm alarm) {
        alarms.update(alarm);
    }

    @Override
    public Alarm create(Alarm alarm) {
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            ,
        );
        return alarms.create(alarm);
    }

    @Override
    public void deleteById(long id) {
        alarms.deleteById(id);
        alarmManager.cancel(
            PendingIntent.getActivity(
                context,
                Long.valueOf(id).hashCode(),
                new Intent(context, MainActivity.class),
                Intent.FLAG_ACTIVITY_NEW_TASK
            )
        );
    }
}
