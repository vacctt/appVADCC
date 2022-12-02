package com.example.appvadcc.map2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appvadcc.R;
import com.example.appvadcc.database.DeviceBT;

import java.util.ArrayList;

public class DeviceBTAdapter extends ArrayAdapter<DeviceBT>
{
    public DeviceBTAdapter(Context context, ArrayList<DeviceBT> devices)
    {
        super(context, 0, devices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        DeviceBT device = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_device_map2, parent, false);
        }
        // Lookup view for data population
        TextView Name = (TextView) convertView.findViewById(R.id.Name);
        TextView Address = (TextView) convertView.findViewById(R.id.Address);
        // Populate the data into the template view using the data object
        Name.setText(device.getName());
        Address.setText(device.getAddress());
        // Return the completed view to render on screen
        return convertView;
    }
}
