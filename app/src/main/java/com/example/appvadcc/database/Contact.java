package com.example.appvadcc.database;

import java.util.UUID;

public class Contact
{
    private String uuid;
    private String name;
    private String phoneNumber;

    public Contact ()
    {

    }

    public Contact (String name, String phoneNumber)
    {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact (String uuid, String name, String phoneNumber)
    {
        this.uuid = uuid;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public boolean isValid()
    {
        if (!name.equals("") && !phoneNumber.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
