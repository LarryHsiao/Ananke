package com.larryhsiao.ananke.alarms;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Test for {@link NextAlarmCalendarSrc}.
 */
class NextAlarmCalendarSrcTest {
    /**
     * Check if the repetition day is tomorrow.
     */
    @Test
    void findNextRepetitionTomorrow() {
        final Calendar current = Calendar.getInstance();
        current.set(Calendar.MINUTE, 59);
        current.set(Calendar.HOUR_OF_DAY, 23);
        current.set(Calendar.MILLISECOND, 0);
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Monday
        current.set(Calendar.SECOND, 0);
        final Calendar targetDate = Calendar.getInstance();
        targetDate.setTime(current.getTime());
        targetDate.set(Calendar.HOUR_OF_DAY, 10);
        targetDate.set(Calendar.MINUTE, 0);
        targetDate.add(Calendar.DAY_OF_YEAR, 1);
        Assertions.assertEquals(
            SimpleDateFormat.getDateTimeInstance().format(
                targetDate.getTime()
            ),
            SimpleDateFormat.getDateTimeInstance().format(
                new NextAlarmCalendarSrc(
                    new ConstAlarm(
                        0,
                        10,
                        0,
                        true,
                        0b0100000 // Repeat at tuesday.
                    ),
                    current::getTimeInMillis
                ).value().getTime()
            )
        );
    }

    /**
     * Check if the repetition day is at next weekday.
     */
    @Test
    void findNextRepetitionNextWeek() {
        final Calendar current = Calendar.getInstance();
        current.set(Calendar.MINUTE, 59);
        current.set(Calendar.HOUR_OF_DAY, 23);
        current.set(Calendar.MILLISECOND, 0);
        current.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Current day is Monday
        current.set(Calendar.SECOND, 0);
        final Calendar targetDate = Calendar.getInstance();
        targetDate.setTime(current.getTime());
        targetDate.set(Calendar.HOUR_OF_DAY, 10);
        targetDate.set(Calendar.MINUTE, 0);
        targetDate.add(Calendar.DAY_OF_YEAR, 7); // Next day
        Assertions.assertEquals(
            SimpleDateFormat.getDateTimeInstance().format(
                targetDate.getTime()
            ),
            SimpleDateFormat.getDateTimeInstance().format(
                new NextAlarmCalendarSrc(
                    new ConstAlarm(
                        0,
                        10,
                        0,
                        true,
                        0b1000000 // repeat at Monday.
                    ),
                    current::getTimeInMillis
                ).value().getTime()
            )
        );
    }
}