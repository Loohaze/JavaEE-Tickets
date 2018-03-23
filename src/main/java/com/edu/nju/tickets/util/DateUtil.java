package com.edu.nju.tickets.util;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

public class DateUtil {

    public static String getCurrentDateTime() {
        LocalDateTime dateTime = new LocalDateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        fmt = fmt.withLocale(Locale.CHINA);

//        System.out.println(dateTime.toString(fmt));
        return dateTime.toString(fmt);
    }

    public static String getCurrentDate() {
        LocalDate date = new LocalDate();
//        System.out.println(date.toString());
        return date.toString();
    }

    public static int getIntervalDays(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        int days = Days.daysBetween(start, end).getDays();
//        System.out.println(days);
        return days;
    }

    public static DateTime timeFormat(String datetime){
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime dateTime = new DateTime(fmt.parseDateTime(datetime));
//        System.out.println(datetime);

        return dateTime;
    }


    public static void main(String[] args) {

        DateTime currentTime = DateTime.now();
        DateTime orderTime = timeFormat("2018-03-08 23:21:16");

        Interval interval = new Interval(orderTime,currentTime);
        System.out.println(interval.toDuration().getStandardMinutes());

        System.out.println(Integer.parseInt(Long.toString(interval.toDuration().getStandardMinutes())));
    }
}
