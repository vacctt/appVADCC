package com.example.appvadcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appvadcc.database.Contact;

import com.example.appvadcc.map4.ContactAdapter;
import com.example.appvadcc.tools.Parameter;
import com.example.appvadcc.tools.Tools;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MAP4 extends AppCompatActivity
{
    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //List
    ArrayList<Contact> contactsList;
    ListView listViewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map4);
        listViewContacts = (ListView) findViewById(R.id.listadoContactos);
        queryContacts();
        listViewContacts.setClickable(true);
        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                sendData(contactsList.get(position));
            }
        });
    }

    public void toCreateContact(View view)
    {
        Tools.goActivity(getActivity(),MAP6.class);
    }

    private void queryContacts()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        contactsList = new ArrayList<Contact>();
        databaseReference.child("Contact").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                contactsList.clear();

                if (snapshot.getChildrenCount() > 0L)
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Contact contact = dataSnapshot.getValue(Contact.class);
                        contactsList.add(contact);
                    }
                    ContactAdapter adapter = new ContactAdapter(getActivity(), contactsList);
                    listViewContacts.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    public void sendData(Contact contact)
    {
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter("uuid", contact.getUuid()));
        parameters.add(new Parameter("name", contact.getName()));
        parameters.add(new Parameter("phoneNumber", contact.getPhoneNumber()));

        Tools.goActivity(getActivity(),MAP5.class,parameters);
    }

    public Activity getActivity()
    {
        return this;
    }
}