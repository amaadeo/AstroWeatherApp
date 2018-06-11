package com.example.astroweather.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.astro.R;

import java.util.Objects;

public class AdditionalDataFragment extends Fragment {

    private TextView windForceText;
    private TextView windDirectionText;
    private TextView humidityText;
    private TextView visibilityText;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_additional_data, container, false);


        windForceText = rootView.findViewById(R.id.windForceText);
        windDirectionText = rootView.findViewById(R.id.windDirectionText);
        humidityText = rootView.findViewById(R.id.humidityText);
        visibilityText = rootView.findViewById(R.id.visibilityText);
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("weather.xml", 0);

        windForceText.setText("Wind speed: " + sharedPreferences.getString("wind_speed", "NULL") + " mph");
        windDirectionText.setText("Direction: " + sharedPreferences.getString("wind_direction", "NULL"));
        humidityText.setText("Humidity: " + sharedPreferences.getString("humidity", "NULL") + " %");
        visibilityText.setText("Visibility: " + sharedPreferences.getString("visibility", "NULL") + " %");
        return rootView;
    }
}
