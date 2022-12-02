package com.example.appvadcc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appvadcc.tools.Tools;

public class MAP3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map3);
    }

    public void toContactsList(View view)
    {
        Tools.goActivity(this,MAP4.class);
    }

    public void toRoutesList(View view)
    {
        Tools.goActivity(this,MAP9.class);
    }

    public void toMonitoring(View view)
    {
        Tools.goActivity(this, MAP7.class);
    }

    public Activity getActivity()
    {
        return this;
    }
}