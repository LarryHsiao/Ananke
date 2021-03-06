package com.larryhsiao.ananke.alarms.views;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.larryhsiao.ananke.Ananke;
import com.larryhsiao.ananke.R;
import com.larryhsiao.ananke.alarms.Alarm;
import com.larryhsiao.ananke.alarms.Alarms;
import com.larryhsiao.ananke.alarms.AlarmsSettleUpAction;
import com.larryhsiao.ananke.alarms.WrappedAlarm;

import java.util.ArrayList;

import static android.media.RingtoneManager.TYPE_ALARM;
import static android.os.VibrationEffect.createWaveform;

/**
 * Activity that fires the Alarm.
 */
public class AlarmActivity extends AppCompatActivity {
    private Vibrator vibrator;
    private Ringtone ringtone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String rawId = getIntent().getAction();
        if (rawId != null) {
            new Thread(() -> disableSingleShotAlarm(rawId)).start();
        }
        setContentView(R.layout.page_alarm);
        allowOnLockScreen();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(
            createWaveform(new long[]{500, 500}, 0),
            new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
        );

        final Uri notification = RingtoneManager.getDefaultUri(TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
        ringtone.setLooping(true);
        ringtone.setAudioAttributes(
            new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
        );
        ringtone.play();

        findViewById(R.id.alarm_dismiss).setOnClickListener(view -> finish());

        new AlarmsSettleUpAction(this, () -> new ArrayList<>(
            ((Ananke) getApplicationContext()).getAlarms().all().values()
        )).fire();
    }

    private void disableSingleShotAlarm(String rawId) {
        try {
            long id = Long.parseLong(rawId);
            final Alarms alarms = ((Ananke) getApplication()).getAlarms();
            final Alarm alarm = alarms.all().get(id);
            if (alarm != null && alarm.repetition() < 0) {
                alarms.update(
                    new WrappedAlarm(alarm) {
                        @Override
                        public boolean enabled() {
                            return false;
                        }
                    }
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void allowOnLockScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager =
                (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);
        } else {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
    }
}
