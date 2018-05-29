package com.example.astroweather.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.astro.R;

public class BasicDataFragment extends Fragment {

    private TextView cityNameText;
    private TextView temperatureText;
    private TextView pressureText;
    private TextView descriptionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_basic_data, container, false);


        cityNameText = rootView.findViewById(R.id.cityNameText);
        temperatureText = rootView.findViewById(R.id.temperatureText);
        pressureText = rootView.findViewById(R.id.pressureText);
        descriptionText = rootView.findViewById(R.id.descriptionText);

        return rootView;
    }
}
