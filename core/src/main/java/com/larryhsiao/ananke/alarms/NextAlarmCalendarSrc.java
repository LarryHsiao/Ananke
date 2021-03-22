package com.larryhsiao.ananke.alarms;

import com.larryhsiao.clotho.Source;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DAY_OF_WEEK;

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
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.MINUTE, alarm.minute());
        calendar.set(Calendar.HOUR_OF_DAY, alarm.hour());
        calendar.set(Calendar.SECOND, 0);
        if (currentTime > calendar.getTimeInMillis()) {
            return findNextByRepetition(calendar);
        } else {
            // The current calendar is exact the Alarm trigger time.
            return calendar;
        }
    }

    private Calendar findNextByRepetition(Calendar current) {
        if (alarm.repetition() == 0){
            current.add(Calendar.DAY_OF_YEAR, 1);
        }else {
            Calendar temp = Calendar.getInstance();
            temp.setFirstDayOfWeek(Calendar.MONDAY);
            temp.setTimeInMillis(current.getTimeInMillis());
            for (int i = 1; i <= 7; i++) { // offset 7 times,(7 day a week), start from day 1
                temp.add(Calendar.DAY_OF_YEAR, 1);
                if (willRepeat(weekdayIndex(temp))){
                    current.setTimeInMillis(temp.getTimeInMillis());
                    break;
                }
            }
            System.out.println("" + (0b0000100 >>> 2));
        }
        return current;
    }

    private int weekdayIndex(Calendar calendar){
        switch (calendar.get(DAY_OF_WEEK)) {
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            case Calendar.MONDAY:
            default:
                return 0;
        }
    }

    private boolean willRepeat(int weekday){ // 0~6 weekday
        return ((alarm.repetition() >> (6-weekday)) & 0b0000001) == 0b0000001;
    }
}
