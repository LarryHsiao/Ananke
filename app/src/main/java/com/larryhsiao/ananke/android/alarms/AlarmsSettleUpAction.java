package com.larryhsiao.ananke.android.alarms;

import android.content.Context;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.clotho.Action;
import com.larryhsiao.clotho.Source;

import java.util.List;

/**
 * Action to setting up list of Alarms.
 */
public class AlarmsSettleUpAction implements Action {
    private final Context context;
    private final Source<List<Alarm>> alarms;

    public AlarmsSettleUpAction(Context context, Source<List<Alarm>> alarms) {
        this.context = context;
        this.alarms = alarms;
    }

    @Override
    public void fire() {
        for (Alarm alarm : alarms.value()) {
            new AlarmSettleUpAction(
                context,
                alarm
            ).fire();
        }
    }
}
