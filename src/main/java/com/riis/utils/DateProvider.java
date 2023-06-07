package com.riis.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateProvider {
    public static LocalDateTime getDateTime() {
        String now = LocalDateTime.now().toString();
        String clean = now.substring(0, now.indexOf('T')) + " " + now.substring(now.indexOf('T') + 1, now.indexOf('.'));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.parse(clean, formatter);
        return currentTime;
    }

    public static String getDateTimeString() {
        String now = LocalDateTime.now().toString();
        String clean = now.substring(0, now.indexOf('T')) + " " + now.substring(now.indexOf('T') + 1, now.indexOf('.'));
        return clean;
    }

    public static String getTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm: a");
        String formattedTime = time.format(formatter);

        return formattedTime;
    }
}