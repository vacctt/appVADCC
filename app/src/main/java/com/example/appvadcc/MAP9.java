package com.example.appvadcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appvadcc.database.Route;
import com.example.appvadcc.map9.RouteAdapter;
import com.example.appvadcc.tools.Parameter;
import com.example.appvadcc.tools.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MAP9 extends AppCompatActivity
{
    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Route> routesList;
    ListView listViewRoute;

    int contRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map9);

        contRoutes = 0;

        listViewRoute = (ListView) findViewById(R.id.listadoRecorridos);

        queryRoutes();
        listViewRoute.setClickable(true);
        listViewRoute.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                sendData(routesList.get(position));
            }
        });
    }

    public void queryRoutes()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        routesList = new ArrayList<Route>();
        databaseReference.child("Routes").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                routesList.clear();

                if (((int)snapshot.getChildrenCount()) > 0)
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        try
                        {
                            Route route = dataSnapshot.getValue(Route.class);
                            routesList.add(route);
                        }
                        catch (DatabaseException de)
                        {
                            de.printStackTrace();
                            Tools.createNotification(getActivity(),"Error",de.getMessage(),1,1,(NotificationManager) getSystemService(NOTIFICATION_SERVICE));
                        }
                    }

                    if(routesList.size()>1)
                    {
                        Collections.sort(routesList, new Comparator<Route>() {
                            @Override
                            public int compare(Route p1, Route p2)
                            {
                                return p1.getDateStart().toDateString().compareTo(p2.getDateStart().toDateString());
                            }
                        });
                    }
                    RouteAdapter adapter = new RouteAdapter(getActivity(), routesList);
                    listViewRoute.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    public void sendData(Route route)
    {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("uuid",route.getUuid()));

        parameters.add(new Parameter("timeStart",route.getDateStart().toTime()));
        parameters.add(new Parameter("dateStart",route.getDateStart().toDate()));

        parameters.add(new Parameter("timeEnd",route.getDateEnd().toTime()));
        parameters.add(new Parameter("dateEnd",route.getDateEnd().toDate()));

        parameters.add(new Parameter("sleepyTime",route.getSleepyTime()));
        parameters.add(new Parameter("buzzersAct",route.getAlarmActivationsBuzzers()));
        parameters.add(new Parameter("modulosAct",route.getAlarmActivationsModules()));
        parameters.add(new Parameter("timeT",route.getTimeTotal()));

        Tools.goActivity(getActivity(),MAP10.class, parameters);
    }

    public Activity getActivity()
    {
        return this;
    }

}