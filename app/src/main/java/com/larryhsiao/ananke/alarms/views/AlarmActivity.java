package com.larryhsiao.ananke.alarms.views;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.annotation.Nullable;
import com.larryhsiao.ananke.R;

import static android.content.Context.VIBRATOR_SERVICE;
import static android.media.RingtoneManager.TYPE_ALARM;
import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;
import static android.os.VibrationEffect.createWaveform;
import static java.lang.Integer.MAX_VALUE;

/**
 * Activity that fires the Alarm.
 */
public class AlarmActivity extends Activity {
    private Vibrator vibrator;
    private Ringtone ringtone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_alarm);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(createWaveform(new long[]{500, 500}, 0));

        final Uri notification = RingtoneManager.getDefaultUri(TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        ringtone.play();

        findViewById(R.id.alarm_dismiss).setOnClickListener(view->{
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vibrator.cancel();
        ringtone.stop();
    }

    @Override
    public void onBackPressed() {
    }
}
