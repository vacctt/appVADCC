package com.example.appvadcc.tools;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.appvadcc.R;
import com.example.appvadcc.database.Contact;
import com.example.appvadcc.sms.SendSMS;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tools
{
    //Firebase
    static FirebaseDatabase firebaseDatabase;
    static DatabaseReference databaseReference;

    //Variables de notificacion
    private final static String CHANNEL_ID = "NOTIFICACION";

    private static FusedLocationProviderClient fusedLocationProviderClient;
    private  final  static int REQUEST_CODE=100;

    public static void goActivity(Activity activity1, Class activity2)
    {
        Intent intent = new Intent(activity1, activity2);
        activity1.startActivity(intent);
    }

    public static void goActivity(Activity activity1, Class activity2, ArrayList<Parameter> data)
    {
        Bundle parmetros = new Bundle();

        for(int i = 0; i < data.size(); i++)
        {
            parmetros.putString(data.get(i).getLabel(), data.get(i).getDate());
        }
        Intent intent = new Intent(activity1, activity2);
        intent.putExtras(parmetros);
        activity1.startActivity(intent);
    }

    public static void goActivity(Activity activity1, Class activity2, String label, String data)
    {
        Bundle parmetros = new Bundle();
        parmetros.putString(label, data);
        Intent intent = new Intent(activity1, activity2);
        intent.putExtras(parmetros);
        activity1.startActivity(intent);
    }

    public static void generateToast(Activity context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void createNotification(Activity context, String title, String content, int icon, int NOTIFICATION_ID, NotificationManager notificationManager)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            //NotificationManager notificationManager = notificationManager;
            notificationManager.createNotificationChannel(notificationChannel);
            showNotification(context,title,content,icon,NOTIFICATION_ID);
        }
        else
        {
            showNotification(context,title,content,icon,NOTIFICATION_ID);
        }
    }

    private static void showNotification(Activity context, String title, String content, int icon, int NOTIFICATION_ID)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(content);
        switch (icon)
        {
            case 1:
            {
                builder.setSmallIcon(R.drawable.error);
                builder.setColor(Color.RED);
                break;
            }
            case 2:
            {
                builder.setSmallIcon(R.drawable.envio);
                builder.setColor(Color.GREEN);
                break;
            }
            case 3:
            {
                builder.setSmallIcon(R.drawable.menssage);
                builder.setColor(Color.BLUE);
                break;
            }
            default:
            {
                builder.setSmallIcon(R.drawable.notifications);
                builder.setColor(Color.YELLOW);
                break;
            }
        }
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public static void sms(Activity context)
    {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(context);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null)
                            {
                                Tools.sendSMS(context,location);
                            }
                        }
                    });
        }else
        {
            askPermission(context);
        }
    }

    private static void askPermission(Activity context)
    {
        ActivityCompat.requestPermissions(context, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    public static void sendSMS(Activity context, Location location)
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        ArrayList<Contact> contactList = new ArrayList<>();

        databaseReference.child("Contact").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                contactList.clear();

                if (snapshot.getChildrenCount() > 0L)
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Contact contact = dataSnapshot.getValue(Contact.class);
                        contactList.add(contact);
                    }

                    for (int x = 0; x <contactList.size(); x++)
                    {
                        String strMessage = "ESTO ES UN MENSAJE AUTOMATICO DE LA APLICACIÓN VADCC \n\n La persona que envió este mensaje está en riesgo de sufrir un accidente";
                        String menssage = "https://www.google.com/maps?q="+location.getLatitude()+","+location.getLongitude()+"&z=17&hl=es";
                        SendSMS.sendSMS(context,contactList.get(x).getPhoneNumber(), strMessage+"\n\n"+menssage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });
    }

    public static void sleep(int time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }

    public static String secondsToHoursMinutesSeconds(String value)
    {
        value = value.replaceAll("\\D+","");
        int num = Integer.parseInt(value);
        int hor,min,seg;
        hor=num/3600;
        min=(num-(3600*hor))/60;
        seg=num-((hor*3600)+(min*60));
        return hor+"h "+min+"m "+seg+"s";
    }
}
