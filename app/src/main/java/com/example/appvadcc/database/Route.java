package com.example.appvadcc.database;

import java.util.UUID;

public class Route
{
    private String uuid;
    private DateTime dateTimeStart;
    private DateTime dateTimeEnd;
    private String timeTotal;
    private String sleepyTime;
    private String alarmActivationsBuzzers;
    private String alarmActivationsModules;
    private String totalSections;

    public Route()
    {
    }

    public Route(String uuid, DateTime dateTimeStart, DateTime dateTimeEnd, String timeTotal, String sleepyTime, String alarmActivationsBuzzers, String alarmActivationsModules, String totalSections) {
        this.uuid = uuid;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.timeTotal = timeTotal;
        this.sleepyTime = sleepyTime;
        this.alarmActivationsBuzzers = alarmActivationsBuzzers;
        this.alarmActivationsModules = alarmActivationsModules;
        this.totalSections = totalSections;
    }

    public Route(DateTime dateTimeStart)
    {
        this.uuid = UUID.randomUUID().toString();
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = null;
        this.sleepyTime = "";
        this.timeTotal = "";
        this.alarmActivationsBuzzers = "";
        this.alarmActivationsModules = "";
        this.totalSections = "";
    }

    public void endRoute(DateTime dateTimeEnd, String sleepyTime, String timeTotal , String alarmActivationsBuzzers, String alarmActivationsModulos, String totalSections)
    {
        if(!this.uuid.equals(""))
        {
            this.dateTimeEnd = dateTimeEnd;
            this.sleepyTime = sleepyTime;
            this.timeTotal = timeTotal;
            this.alarmActivationsBuzzers = alarmActivationsBuzzers;
            this.alarmActivationsModules = alarmActivationsModulos;
            this.totalSections = totalSections;
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = String.valueOf(uuid);
    }

    public DateTime getDateStart() {
        return dateTimeStart;
    }

    public void setDateStart(DateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public DateTime getDateEnd() {
        return dateTimeEnd;
    }

    public void setDateEnd(DateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getSleepyTime() {
        return sleepyTime;
    }

    public void setSleepyTime(String sleepyTime) {
        this.sleepyTime = String.valueOf(sleepyTime);
    }

    public String getAlarmActivationsBuzzers() {
        return alarmActivationsBuzzers;
    }

    public void setAlarmActivationsBuzzers(String alarmActivationsBuzzers) {
        this.alarmActivationsBuzzers = String.valueOf(alarmActivationsBuzzers);
    }

    public String getAlarmActivationsModules() {
        return alarmActivationsModules;
    }

    public void setAlarmActivationsModules(String alarmActivationsModules) {
        this.alarmActivationsModules = String.valueOf(alarmActivationsModules);
    }

    public String getTotalSections() {
        return totalSections;
    }

    public void setTotalSections(String totalSections) {
        this.totalSections = String.valueOf(totalSections);
    }

    public String getTimeTotal() {
        return timeTotal;
    }

    public void setTimeTotal(String timeTotal) {
        this.timeTotal = String.valueOf(timeTotal);
    }
}
