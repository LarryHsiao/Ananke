package com.larryhsiao.ananke.alarms;

import com.larryhsiao.clotho.Source;

import java.util.Calendar;
import java.util.Date;

/**
 * Source to build next alarm's {@link Calendar}.
 */
public class NextAlarmCalendarSrc implements Source<Calendar> {
    private final Alarm alarm;
    private final Source<Long> currentTimeSrc;

    public NextAlarmCalendarSrc(Alarm alarm, Source<Long> currentTimeSrc) {
        this.alarm = alarm;
        this.currentTimeSrc = currentTimeSrc;
    }

    @Override
    public Calendar value() {
        final long currentTime = currentTimeSrc.value();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(currentTime));
        calendar.set(Calendar.MINUTE, alarm.minute());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.hour());
        if (currentTime > calendar.getTimeInMillis()) {
            return findNextByRepetition(calendar);
        } else {
            // The current calendar is exact the Alarm trigger time.
            return calendar;
        }
    }

    private Calendar findNextByRepetition(Calendar current) {
        final int weekday = current.get(Calendar.DAY_OF_WEEK) - 1; // 0~6
        final int repetition = alarm.repetition();
        for (int i = 1; i <= 7; i++) { // offset 7 times,(7 day a week), start from day 1
            if (((repetition >> (6 - ((weekday + i) % 7))) & 0b0000001) == 1) {
                current.add(Calendar.DAY_OF_YEAR, i);
                break;
            }
        }
        return current;
    }
}
