package com.larryhsiao.ananke.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.larryhsiao.ananke.R;
import com.larryhsiao.ananke.android.alarm.AlarmActivity;
import com.larryhsiao.ananke.android.alarm.AlarmReceiver;

/**
 * Entry Activity of Ananke.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 5000,
            PendingIntent.getBroadcast(
                this,
                1000,
                new Intent(this, AlarmReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        );
    }
}