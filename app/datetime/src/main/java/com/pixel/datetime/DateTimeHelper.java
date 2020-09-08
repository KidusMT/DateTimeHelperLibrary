package com.pixel.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class DateTimeHelper {

    /**
     * This method is used for converting the string timestamp to a Date object
     *
     * @param time is the timestamp from the API that we want to change
     * @return Date object of the string timestamp after being converted
     */
    public static Date timeStampConvertToDate(String time) {

        Date date1 = null;
        SimpleDateFormat dateFormat;

        try {
            if (time.contains("T")) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'", Locale.getDefault());
            } else if (time.contains("AM") || time.contains("PM") || time.contains("am") || time.contains("pm")) {
                dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            } else if (time.length() > 16 && time.contains("-") && time.contains(":")) {// todo need to solve this issue or its a huge bug source
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            } else {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            }
            date1 = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * This method will convert timestamp time to a 12 hours format for better display
     *
     * @param time is the API timestamp that we want to convert
     * @return 12 hours format with AM or PM of the timestamp
     */
    public static String timeStampConvertTo12HoursFormatTime(String time) {

        Date date1 = null;
        SimpleDateFormat dateFormat;

        try {
            if (time.contains("T")) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'", Locale.getDefault());
            } else {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            }
            date1 = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date1);
    }

    /**
     * This method is for converting the timestamp to a 24 horus formatting for better display
     *
     * @param time is the API timestamp that we want to convert
     * @return 24 hours format with AM or PM of the timestamp
     */
    public static String timeStampConvertTo24HoursFormatTime(String time) {

        Date date1 = null;
        SimpleDateFormat dateFormat;

        try {
            if (time.contains("T")) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'", Locale.getDefault());
            } else {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            }
            date1 = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(date1);
    }

    /**
     * This method is for converting the java date string value object to a timestamp value for
     * the API request
     *
     * @param time is the string format of the date selected on the device
     * @return to a string value of a proper timestamp format for the API request
     */
    public static String timeConvertToTimeStamp(String time) {
        String date1 = null;


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        try {

            Date timeD = sdf.parse(time.length() > 7 ? time : format(Locale.getDefault(), "0%s", time));
            if (timeD != null && time.length() > 0) {
                date1 = date24Format.format(timeD);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return today + " " + date1;
    }

    public static String formatTimeDisplayDays(int days, int hrs, int min) {
        String duration;
        if (days > 0)
            if (min > 0)
                duration = format(Locale.getDefault(), " %d D:", days) +
                        format(Locale.getDefault(), hrs > 0 ? (hrs == 1 ? " %d Hr:" : " %d Hrs:") : "", hrs) +
                        format(Locale.getDefault(), " %d Min ", min);
            else if (hrs > 0)
                duration = format(Locale.getDefault(), " %d D:", days) +
                        format(Locale.getDefault(), (hrs == 1 ? " %d Hr " : " %d Hrs "), hrs);
            else //if (min == 0)
                duration = format(Locale.getDefault(), (days == 1 ? " %d D " : " %d D "), days);
        else if (hrs > 0)
            if (min > 0)
                duration = format(Locale.getDefault(), hrs == 1 ? " %d Hr: %d Min " : " %d Hrs: %d Min ", hrs, min);
            else //if (min == 0)
                duration = format(Locale.getDefault(), hrs == 1 ? " %d Hr " : " %d Hrs ", hrs);
        else //if (min > 0) {
            if (min > 0)
                duration = format(Locale.getDefault(), " %d Min ", min);
            else
                duration = " - ";

        return duration;
    }

    public static String secondToFormattedTimeDisplay(long duration) {
        long seconds = duration / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long min = minutes % 60;
        long hrs = hours % 24;
        return formatTimeDisplay((int) hrs, (int) min);
    }

    public static String formatTimeDisplay(int hrs, int min) {
        String duration;
        if (hrs > 0)
            if (min > 0)
                duration = format(Locale.getDefault(), hrs == 1 ? " %d Hr: %d Min " : " %d Hrs: %d Min ", hrs, min);
            else //if (min == 0)
                duration = format(Locale.getDefault(), hrs == 1 ? " %d Hr " : " %d Hrs ", hrs);
        else //if (min > 0) {
            if (min > 0)
                duration = format(Locale.getDefault(), " %d Min ", min);
            else
                duration = " - ";

        return duration;
    }

    public static String formatTime(int hourOfDay, int minutes) {
        String format;
        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        String minute = valueOf(minutes).length() < 2 ? format(Locale.getDefault(), "0%d", minutes) : valueOf(minutes);
        return String.format("%s:%s %s", hourOfDay, minute, format);
    }

    public static String calculateHours(double hours) {
        String duration;

        long seconds = (long) hours * 3600;

        int day = (int) (seconds / 28800);
        long min = seconds % 3600 / 60;
        long hrs = (seconds - (day * 28800)) / 3600;
        if (day > 0)
            if (min > 0)
                duration = format(Locale.getDefault(), " %d D: %d H: %d M ", day, hrs, min);
            else if (hrs > 0 && min == 0)
                duration = format(Locale.getDefault(), " %d D: %d H ", day, hrs);
            else //if (hours == 0 && min == 0)
                duration = format(Locale.getDefault(), day == 1 ? " %d Day " : " %d Days ", day);
        else if (hrs > 0)
            if (min > 0)
                duration = format(Locale.getDefault(), " %d H: %d M ", hrs, min);
            else //if (min == 0)
                duration = format(Locale.getDefault(), hrs == 1 ? " %d Hr " : " %d Hrs ", hrs);
        else //if (min > 0) {
            duration = format(Locale.getDefault(), " %d Min ", min);

        return duration;
    }
}
