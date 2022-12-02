package com.example.appvadcc.database;

public class DeviceBT
{
    private String Name;
    private String Address;

    public DeviceBT()
    {

    }

    public DeviceBT(String Name, String Address)
    {
        this.Name=Name;
        this.Address=Address;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
