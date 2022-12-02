package com.example.appvadcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.appvadcc.database.Section;
import com.example.appvadcc.map10.SectionAdapter;
import com.example.appvadcc.tools.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MAP10 extends AppCompatActivity
{
    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView sectionListView;

    TextView timeStart;
    TextView dateStart;

    TextView timeEnd;
    TextView dateEnd;

    TextView sleepyTime;
    TextView buzzersAct;
    TextView modulesAct;
    TextView timeT;

    Button btnDetails;
    Button btnLessDetails;

    TableLayout sectionsTable;

    String uuidRoute;

    ArrayList<Section> sectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map10);

        sectionListView = (ListView) findViewById(R.id.listadoSection);

        timeStart = (TextView) findViewById(R.id.timeStart);
        dateStart = (TextView) findViewById(R.id.dateStart);

        timeEnd = (TextView) findViewById(R.id.timeEnd);
        dateEnd = (TextView) findViewById(R.id.dateEnd);

        sleepyTime = (TextView) findViewById(R.id.sleepyT);
        buzzersAct = (TextView) findViewById(R.id.buzzersAct);
        modulesAct = (TextView) findViewById(R.id.modulosAct);
        timeT = (TextView) findViewById(R.id.timeT);

        btnDetails = (Button) findViewById(R.id.btnDetails);
        btnLessDetails = (Button) findViewById(R.id.btnLessDetails);

        sectionsTable = (TableLayout) findViewById(R.id.tableLayout);

        timeStart.setText(getIntent().getStringExtra("timeStart"));
        dateStart.setText(getIntent().getStringExtra("dateStart"));

        timeEnd.setText(getIntent().getStringExtra("timeEnd"));
        dateEnd.setText(getIntent().getStringExtra("dateEnd"));

        sleepyTime.setText("Tiempo total de somnolencia: "+getIntent().getStringExtra("sleepyTime"));
        buzzersAct.setText("Veces que se activó el sistema de sonido: "+getIntent().getStringExtra("buzzersAct"));
        modulesAct.setText("Veces que se activó el sistema de vibración: "+getIntent().getStringExtra("modulosAct"));
        timeT.setText("Duración total del recorrido: \b"+getIntent().getStringExtra("timeT"));

        queryRoute();
    }

    public void queryRoute()
    {
        uuidRoute = getIntent().getStringExtra("uuid");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        sectionList = new ArrayList<>();
        databaseReference.child("Section").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                sectionList.clear();

                if (((int)snapshot.getChildrenCount()) > 0)
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Section section = dataSnapshot.getValue(Section.class);
                        if(section.getUuidRoute().equals(uuidRoute))
                        {
                            sectionList.add(section);
                        }
                    }
                    if(sectionList.size()>1)
                    {
                        Collections.sort(sectionList, new Comparator<Section>() {
                            @Override
                            public int compare(Section p1, Section p2)
                            {
                                return p1.getDate().toDateString().compareTo(p2.getDate().toDateString());
                            }
                        });
                    }

                    SectionAdapter adapter = new SectionAdapter(getActivity(), sectionList);
                    sectionListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });
    }

    public void toDeleteRoute(View view)
    {
        for(int x = 0; x < sectionList.size(); x++)
        {
            databaseReference.child("Section").child(sectionList.get(x).getUuid()).removeValue();
        }

        databaseReference.child("Routes").child(uuidRoute).removeValue();
        Tools.generateToast(getActivity(), "Ruta eliminada");
        Tools.goActivity(getActivity(),MAP9.class);
    }

    public void toDetails(View view)
    {
        sleepyTime.setVisibility(View.VISIBLE);
        buzzersAct.setVisibility(View.VISIBLE);
        modulesAct.setVisibility(View.VISIBLE);
        timeT.setVisibility(View.VISIBLE);

        btnDetails.setVisibility(View.GONE);
        btnLessDetails.setVisibility(View.VISIBLE);

        sectionsTable.setVisibility(View.GONE);
    }

    public void toLessDetails(View view)
    {
        sleepyTime.setVisibility(View.GONE);
        buzzersAct.setVisibility(View.GONE);
        modulesAct.setVisibility(View.GONE);
        timeT.setVisibility(View.GONE);

        btnDetails.setVisibility(View.VISIBLE);
        btnLessDetails.setVisibility(View.GONE);

        sectionsTable.setVisibility(View.VISIBLE);
    }

    public Activity getActivity()
    {
        return this;
    }
}