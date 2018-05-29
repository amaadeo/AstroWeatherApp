package com.example.astroweather.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.astro.R;

public class AdditionalDataFragment extends Fragment {

    private TextView windForceText;
    private TextView windDirectionText;
    private TextView humidityText;
    private TextView visibilityText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_additional_data, container, false);


        windForceText = rootView.findViewById(R.id.windForceText);
        windDirectionText = rootView.findViewById(R.id.windDirectionText);
        humidityText = rootView.findViewById(R.id.humidityText);
        visibilityText = rootView.findViewById(R.id.visibilityText);

        return rootView;
    }
}
