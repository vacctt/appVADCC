package com.example.appvadcc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appvadcc.tools.Tools;

public class MAP8 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map8);
    }

    public void toMonitoring(View view)
    {
        Tools.goActivity(getActivity(),MAP7.class);
    }

    public void toRoutes(View view)
    {
        Tools.goActivity(getActivity(),MAP9.class);
    }

    public Activity getActivity()
    {
        return this;
    }
}