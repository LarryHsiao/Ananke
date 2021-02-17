package com.larryhsiao.ananke.android.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.larryhsiao.ananke.android.alarms.views.AlarmActivity;

/**
 * BroadcastReceiver that receives the broadcasts triggered by the preset.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Intent launchIntent = new Intent(context, AlarmActivity.class);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(launchIntent);;
    }
}
