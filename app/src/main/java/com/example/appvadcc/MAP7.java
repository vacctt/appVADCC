package com.example.appvadcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appvadcc.bluetooth.ServerBT;
import com.example.appvadcc.database.DateTime;
import com.example.appvadcc.database.DeviceBT;
import com.example.appvadcc.database.Route;
import com.example.appvadcc.database.Section;
import com.example.appvadcc.tools.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MAP7 extends AppCompatActivity
{
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //Variables para la conexion bluetooth
    private DeviceBT deviceBT;
    private ServerBT serverBT;

    //Variables
    Route route = null;

    String sleepyTime;
    String alarmActivationsBuzzers;
    String alarmActivationsModules;
    String timeTotal;
    int totalSections;

    TextView textViewPrincipal;
    TextView textViewAbajo;

    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map7);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        textViewPrincipal = (TextView) findViewById(R.id.textView);
        textViewAbajo = (TextView) findViewById(R.id.textView3);
        button4 = (Button) findViewById(R.id.button4);

        sleepyTime = "";
        alarmActivationsBuzzers = "";
        timeTotal = "";
        totalSections = 0;

        textViewPrincipal.setText("Conectando...");
        textViewAbajo.setText("");
        button4.setText("Cancelar");

        databaseReference.child("Device").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.getChildrenCount() > 0L)
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        deviceBT = dataSnapshot.getValue(DeviceBT.class);
                    }
                    connectDevice();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void connectDevice()
    {
        serverBT = new ServerBT(this);
        serverBT.start();
    }

    public void notification(String title, String content, int icon, int notificacionId)
    {
        Tools.createNotification(this,title,content,icon,notificacionId,(NotificationManager) getSystemService(NOTIFICATION_SERVICE));
    }

    public void createRoute()
    {
        button4.setText("Finalizar Monitoreo");
        textViewPrincipal.setText("Monitoreando");
        textViewAbajo.setText("¡Recuerda no usar el celular mientras manejes!");

        route = new Route(new DateTime(1));
        databaseReference.child("Routes").child(route.getUuid()).setValue(route);
    }

    public void createSection(String level)
    {
        if(route != null)
        {
            Section section = new Section(route.getUuid(),new DateTime(1),level);
            databaseReference.child("Section").child(section.getUuid()).setValue(section);
            totalSections++;
        }
    }

    public void toFinish(View view)
    {
        if(serverBT.getSocket() == null)
        {
            Tools.goActivity(getActivity(),MAP3.class);
        }
        else
        {
            serverBT.write("FIN");
        }
    }

    public void finishProcess()
    {
        serverBT.cancel();
        route.endRoute(new DateTime(1), Tools.secondsToHoursMinutesSeconds(sleepyTime),Tools.secondsToHoursMinutesSeconds(timeTotal),alarmActivationsBuzzers, alarmActivationsModules,String.valueOf(totalSections));
        databaseReference.child("Routes").child(route.getUuid()).setValue(route);
        Tools.goActivity(this,MAP8.class);
    }

    public void sendSMS()
    {
        Tools.sms(getActivity());
    }

    public Activity getActivity()
    {
        return this;
    }

    public void setSleepyTime(String sleepyTime) {
        this.sleepyTime = sleepyTime;
    }

    public void setAlarmActivationsBuzzers(String alarmActivationsBuzzers) {
        this.alarmActivationsBuzzers = alarmActivationsBuzzers;
    }

    public void setAlarmActivationsModules(String alarmActivationsModules) {
        this.alarmActivationsModules = alarmActivationsModules;
    }
    public void setTimeTotal(String timeTotal)
    {
        this.timeTotal = timeTotal;
    }
}