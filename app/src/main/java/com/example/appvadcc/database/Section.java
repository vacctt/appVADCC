package com.example.appvadcc.database;

import java.util.UUID;

public class Section
{
    private String uuid;
    private String uuidRoute;
    private DateTime dateTime;
    private String level;


    public Section()
    {

    }

    public Section(String uuid, String uuidRoute, DateTime dateTime, String level)
    {
        this.uuid = uuid;
        this.uuidRoute = uuidRoute;
        this.dateTime = dateTime;
        this.level = level;
    }

    public Section(String uuidRoute, DateTime dateTime, String level)
    {
        this.uuid = UUID.randomUUID().toString();
        this.uuidRoute = uuidRoute;
        this.dateTime = dateTime;
        this.level = level;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidRoute() {
        return uuidRoute;
    }

    public void setUuidRoute(String uuidRoute) {
        this.uuidRoute = uuidRoute;
    }

    public DateTime getDate() {
        return dateTime;
    }

    public void setDate(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
