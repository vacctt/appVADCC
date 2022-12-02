package com.example.appvadcc.map9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appvadcc.R;
import com.example.appvadcc.database.Route;

import java.util.ArrayList;

public class RouteAdapter extends ArrayAdapter<Route>
{
    public RouteAdapter(Context context, ArrayList<Route> routes)
    {
        super(context, 0, routes);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Route route = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_route_map9, parent, false);
        }
        // Lookup view for data population
        TextView idRoute = (TextView) convertView.findViewById(R.id.idRoute);
        TextView dateRoute = (TextView) convertView.findViewById(R.id.DateRoute);
        TextView timeRoute = (TextView) convertView.findViewById(R.id.TimeRoute);
        // Populate the data into the template view using the data object
        idRoute.setText(""+position);
        dateRoute.setText(route.getDateStart().toDate());
        timeRoute.setText(route.getDateStart().toTime());

        // Return the completed view to render on screen
        return convertView;
    }
}
