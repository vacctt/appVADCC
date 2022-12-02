package com.example.appvadcc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.appvadcc.database.Contact;
import com.example.appvadcc.tools.Tools;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class MAP6 extends AppCompatActivity
{
    //Variables de la base de datos
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //Elementos del layout
    EditText name;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map6);
    }

    public void toCreateContact(View view)
    {
        name = (EditText) findViewById(R.id.textInputTextName);
        phoneNumber = (EditText) findViewById(R.id.textInputTextNumber);

        Contact contact = new Contact(String.valueOf(name.getText()),String.valueOf(phoneNumber.getText()));

        if (contact.isValid())
        {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

            databaseReference.child("Contact").child(contact.getUuid()).setValue(contact);

            Tools.generateToast(getActivity(),"Contacto guardado");
            Intent intent = new Intent(getActivity(), MAP4.class);
            startActivity(intent);
        }
        else
        {
            Tools.generateToast(this,"Favor de verificar los datos");
        }
    }

    public Activity getActivity()
    {
        return this;
    }
}