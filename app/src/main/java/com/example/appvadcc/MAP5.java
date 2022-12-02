package com.example.appvadcc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.appvadcc.database.Contact;
import com.example.appvadcc.tools.Tools;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MAP5 extends AppCompatActivity
{
    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Contact contact;
    //
    EditText editTextName;
    EditText editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map5);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        this.contact = new Contact(
                getIntent().getStringExtra("uuid"),
                getIntent().getStringExtra("name"),
                getIntent().getStringExtra("phoneNumber")
        );

        loadContact();
    }

    private void loadContact()
    {
        editTextName.setText(contact.getName());
        editTextPhoneNumber.setText(contact.getPhoneNumber());
    }

    public void toEditContact(View view)
    {
        contact.setName(String.valueOf(editTextName.getText()));
        contact.setPhoneNumber(String.valueOf(editTextPhoneNumber.getText()));

        databaseReference.child("Contact").child(contact.getUuid()).setValue(contact);
        Tools.generateToast(getActivity(), "Contacto actualizado");

        goMAP4();
    }

    public void toDeleteContact(View view)
    {
        databaseReference.child("Contact").child(contact.getUuid()).removeValue();
        Tools.generateToast(getActivity(), "Contacto Eliminado");

        goMAP4();
    }

    public Activity getActivity()
    {
        return this;
    }

    public void goMAP4()
    {
        Intent intent = new Intent(getActivity(), MAP4.class);
        startActivity(intent);
    }
}