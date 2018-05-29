package com.example.astroweather.settings;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.astro.R;

public class UnitSpinnerAdapter extends BaseAdapter {

    private String[] units;
    private LayoutInflater inflater;

    public UnitSpinnerAdapter(Activity activity, String[] units) {
        this.units = units;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return units.length;
    }

    @Override
    public Object getItem(int position) {
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
