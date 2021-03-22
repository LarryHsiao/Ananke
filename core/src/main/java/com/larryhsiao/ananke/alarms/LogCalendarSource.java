package com.larryhsiao.ananke.alarms;

import com.larryhsiao.clotho.Source;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Decorator source to print log by given source of {@link Calendar}.
 */
public class LogCalendarSource implements Source<Calendar> {
    private final Source<Calendar> src;

    public LogCalendarSource(Source<Calendar> src) {
        this.src = src;
    }

    @Override
    public Calendar value() {
        final Calendar calendar = src.value();
        System.out.println(
            SimpleDateFormat.getDateTimeInstance().format(calendar.getTime())
        );
        return calendar;
    }
}
