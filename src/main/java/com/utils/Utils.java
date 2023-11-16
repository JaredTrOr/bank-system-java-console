package com.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Utils {

    public Utils() {}

    public String getCurrentDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Formatting the date and time using DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        return currentDateTime.format(formatter);
    }

    public String getCurrentTime() {
        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Get the current hour, minute, and second
        int hour = currentTime.getHour();
        int minute = currentTime.getMinute();
        int second = currentTime.getSecond();

        return hour + ":" + minute + ":" + second;
    }

    public String generateRandomId() {
        return UUID.randomUUID().toString();
    }

}
