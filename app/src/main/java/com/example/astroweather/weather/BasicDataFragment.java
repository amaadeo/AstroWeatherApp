package com.example.astroweather.weather;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.astro.R;
import com.example.astroweather.settings.TemperatureUnitSpinnerAdapter;

import java.util.Objects;

public class BasicDataFragment extends Fragment {

    private TextView cityNameText;
    private TextView dateTimeText;
    private ImageView weatherImage;
    private TextView temperatureText;
    private TextView descriptionText;
    private TextView pressureText;
    private SharedPreferences sharedPreferences;
    private String temperatureUnit;
    private String temperature;
    private Drawable weatherIconDrawable;
    private String tempUnit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_basic_data, container, false);

        initElements(rootView);
        getSharedPreferences();
        changeUnit();
        setWeatherBasicInfo();
        return rootView;
    }

    private void setWeatherBasicInfo() {
        cityNameText.setText(String.format("%s, %s", sharedPreferences.getString("city", "NULL"), sharedPreferences.getString("country", "NULL")));
        dateTimeText.setText(sharedPreferences.getString("current_date", "NULL"));
        weatherImage.setImageDrawable(weatherIconDrawable);
        temperatureText.setText(String.format("%s%s", temperature, temperatureUnit));
        descriptionText.setText(sharedPreferences.getString("current_description", "NULL"));
        pressureText.setText(String.format("%shPa", sharedPreferences.getString("pressure", "NULL")));
    }

    private void getSharedPreferences() {
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("weather.xml", 0);
        temperature = sharedPreferences.getString("current_temperature", "NULL");
        int resource = getResources().getIdentifier("icon_" + sharedPreferences.getString("current_image_code", "44"), "drawable", Objects.requireNonNull(getContext()).getPackageName());
        weatherIconDrawable = getResources().getDrawable(resource, null);
        tempUnit = sharedPreferences.getString("temperature_unit", "NULL");
    }

    private void initElements(ViewGroup rootView) {
        cityNameText = rootView.findViewById(R.id.cityNameText);
        dateTimeText = rootView.findViewById(R.id.dateTimeText);
        weatherImage = rootView.findViewById(R.id.weatherImage);
        temperatureText = rootView.findViewById(R.id.temperatureText);
        descriptionText = rootView.findViewById(R.id.descriptionText);
        pressureText = rootView.findViewById(R.id.pressureText);
    }

    private void changeUnit() {
        if(tempUnit.equals("0") || tempUnit.equals("NULL")) {
            temperatureUnit = "°F";
        } else {
            temperatureUnit = "°C";
            temperature = String.valueOf((int)((Integer.parseInt(temperature) - 32) / 1.8));
        }
    }
}
