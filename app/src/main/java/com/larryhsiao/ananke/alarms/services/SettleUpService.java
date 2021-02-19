package com.larryhsiao.ananke.alarms.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Service to settle up all alarms to {@link android.app.AlarmManager}.
 */
public class SettleUpService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
