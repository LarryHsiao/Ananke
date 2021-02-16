package com.larryhsiao.ananke.android;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
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

        if (!Settings.canDrawOverlays(this)) {
            new AlertDialog.Builder(this)
                .setTitle(R.string.Permission_required_)
                .setMessage(
                    R.string.To_make_sure_the_alarm_triggered_as_expected_we_need_overlay_permission_to_do_that_
                )
                .setPositiveButton(R.string.OK, (dialog, which) -> {
                    startActivity(new Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                    ));
                })
                .setNegativeButton(R.string.Cancel, null)
                .show();
        }
    }
}