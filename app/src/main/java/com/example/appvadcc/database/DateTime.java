package com.example.appvadcc.database;

import java.time.LocalDateTime;

public class DateTime
{
    private String year;
    private String month;
    private String day;

    private String hour;
    private String minute;
    private String second;

    public DateTime()
    {

    }

    public DateTime(String year, String month, String day, String hour, String minute, String second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public DateTime(int i)
    {
        LocalDateTime localDateTime = LocalDateTime.now();

        this.year =  subString(localDateTime.getYear());
        this.month = subString(localDateTime.getMonthValue());
        this.day = subString(localDateTime.getDayOfMonth());
        this.hour = subString(localDateTime.getHour());
        this.minute = subString(localDateTime.getMinute());
        this.second = subString(localDateTime.getSecond());
    }

    public String subString(int num)
    {
        if (num > 10)
        {
            return ""+num;
        }
        else
        {
            return String.format("%02d", num);
        }

    }

    public String toDateString()
    {
        return getYear()+"/"+getMonth()+"/"+getDay()+" "+getHour()+":"+getMinute()+":"+getSecond();
    }

    public String toDate()
    {
        return getYear()+"/"+getMonth()+"/"+getDay();
    }

    public String toTime()
    {
        return getHour()+":"+getMinute()+":"+getSecond();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}
