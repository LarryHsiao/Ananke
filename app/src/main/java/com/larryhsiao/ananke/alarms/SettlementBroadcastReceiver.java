package com.larryhsiao.ananke.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.larryhsiao.ananke.Ananke;

import java.util.ArrayList;

/**
 * {@link BroadcastReceiver} for triggering the alarm settlement.
 */
public class SettlementBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            new AlarmsSettleUpAction(context, () -> new ArrayList<>(
                ((Ananke) context.getApplicationContext()).getAlarms().all().values()
            )).fire();
        }
    }
}
