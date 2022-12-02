package com.example.appvadcc.map4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appvadcc.R;
import com.example.appvadcc.database.Contact;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact>
{

    public ContactAdapter(Context context, ArrayList<Contact> contacts)
    {
        super(context, 0, contacts);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Contact contact = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_contact_map4, parent, false);
        }
        // Lookup view for data population
        TextView contactName = (TextView) convertView.findViewById(R.id.ContactName);
        TextView contactNumber = (TextView) convertView.findViewById(R.id.ContactNumber);
        // Populate the data into the template view using the data object
        contactName.setText(contact.getName());
        contactNumber.setText(contact.getPhoneNumber());
        // Return the completed view to render on screen
        return convertView;
    }
}
