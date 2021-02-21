package com.larryhsiao.ananke.alarms;

import com.larryhsiao.clotho.Source;

import java.util.Calendar;

/**
 * Source to build next alarm's {@link Calendar}.
 */
public class NextAlarmCalendarSrc implements Source<Calendar> {
    private final Alarm alarm;
    private final Source<Long> currentTime;

    public NextAlarmCalendarSrc(Alarm alarm, Source<Long> currentTime) {
        this.alarm = alarm;
        this.currentTime = currentTime;
    }

    @Override
    public Calendar value() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, alarm.minute());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.hour());
        if (currentTime.value() > calendar.getTimeInMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return calendar;
    }
}
