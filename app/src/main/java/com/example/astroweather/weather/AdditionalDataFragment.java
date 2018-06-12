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

    public static final double SPEED_IN_MPH = 1.61;
    private TextView windForceText;
    private TextView windDirectionText;
    private TextView humidityText;
    private TextView visibilityText;
    private SharedPreferences sharedPreferences;
    private String speed;
    private String speedUnit;
    private String windSpeed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_additional_data, container, false);


        initElements(rootView);
        setAdditionalWeatherInfo();
        return rootView;
    }

    private void setAdditionalWeatherInfo() {
        windForceText.setText(String.format("Wind speed: %s %s", changeUnit(sharedPreferences.getString("wind_speed", "NULL")), speedUnit));
        windDirectionText.setText(String.format("Direction: %s", sharedPreferences.getString("wind_direction", "NULL")));
        humidityText.setText(String.format("Humidity: %s %%", sharedPreferences.getString("humidity", "NULL")));
        visibilityText.setText(String.format("Visibility: %s %%", sharedPreferences.getString("visibility", "NULL")));
    }

    private void initElements(ViewGroup rootView) {
        windForceText = rootView.findViewById(R.id.windForceText);
        windDirectionText = rootView.findViewById(R.id.windDirectionText);
        humidityText = rootView.findViewById(R.id.humidityText);
        visibilityText = rootView.findViewById(R.id.visibilityText);
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("weather.xml", 0);
        speed = sharedPreferences.getString("temperature_unit", "NULL");
    }

    private String changeUnit(String windSpeed) {
        if(speed.equals("0") || speed.equals("NULL")) {
            speedUnit = "mph";
        } else {
            speedUnit = "km/h";
            windSpeed = String.valueOf(Double.parseDouble(windSpeed) * SPEED_IN_MPH);
        }
        return windSpeed;
    }
}
