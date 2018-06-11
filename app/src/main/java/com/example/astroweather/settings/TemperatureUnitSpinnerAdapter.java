package com.example.astroweather.settings;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.astro.R;

public class TemperatureUnitSpinnerAdapter extends BaseAdapter {

    private String[] units = {"°F", "°C"};
    private LayoutInflater inflater;

    public TemperatureUnitSpinnerAdapter(Activity activity) {
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return units.length;
    }

    @Override
    public Object getItem(int position) {
        switch (position){
            case 0: {
                return units[0];
            }
            case 1: {
                return units[1];
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_row, null);

        TextView unit = row.findViewById(R.id.unit);
        unit.setText(units[position]);
        return row;
    }
}
