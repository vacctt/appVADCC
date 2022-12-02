package com.example.appvadcc.tools;

public class Parameter
{
    String label;
    String date;

    public Parameter(String label, String date)
    {
        this.label = label;
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
