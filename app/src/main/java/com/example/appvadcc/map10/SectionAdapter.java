package com.example.appvadcc.map10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appvadcc.R;
import com.example.appvadcc.database.Route;
import com.example.appvadcc.database.Section;

import java.util.ArrayList;

public class SectionAdapter extends ArrayAdapter<Section>
{
    public SectionAdapter(Context context, ArrayList<Section> sections)
    {
        super(context, 0, sections);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Section section = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_section_map10, parent, false);
        }
        // Lookup view for data population
        TextView hour = (TextView) convertView.findViewById(R.id.hour);
        TextView level = (TextView) convertView.findViewById(R.id.level);
        // Populate the data into the template view using the data object
        hour.setText(section.getDate().toTime());
        level.setText(""+section.getLevel());
        // Return the completed view to render on screen
        return convertView;
    }
}
