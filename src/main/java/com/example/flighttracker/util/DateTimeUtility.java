package com.example.flighttracker.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import static com.example.flighttracker.util.ConstantsUtil.*;
public class DateTimeUtility {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtility.class);

    public static String getCurrentDate() {
        String formattedDate = "";
        try {
            Calendar calendar = getCalendarInstance();
            Date currentDate = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
            formattedDate = formatter.format(currentDate);
        } catch (Exception e) {
            logger.error("Error while getting today's date {}", e.getMessage());
        }
        return formattedDate;
    }

    public static String getPreviousDate(int previousDays) {
        String formattedDate = "";
        try {
            Calendar calendar = getCalendarInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1 * previousDays);
            Date currentDate = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
            formattedDate = formatter.format(currentDate);
        } catch (Exception e) {
            logger.error("Error while getting previous date {}", e.getMessage());
        }
        return formattedDate;
    }

    public static int getCurrentHour() {
        Calendar calendar = getCalendarInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private static Calendar getCalendarInstance() {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone(CST_TIMEZONE);
        calendar.setTimeZone(timeZone);
        return calendar;
    }
}
