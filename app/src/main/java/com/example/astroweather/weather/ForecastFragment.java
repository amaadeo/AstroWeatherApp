package com.example.astroweather.weather;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.astro.R;

import org.w3c.dom.Text;

import java.util.Objects;

public class ForecastFragment extends Fragment {
    public static final int NUMBER_OF_DAYS = 5;
    private TextView[] maxTemps = new TextView[NUMBER_OF_DAYS];
    private TextView[] minTemps = new TextView[NUMBER_OF_DAYS];
    private ImageView[] weatherImages = new ImageView[NUMBER_OF_DAYS];
    private TextView[] days = new TextView[NUMBER_OF_DAYS];
    private SharedPreferences sharedPreferences;
    private int resource[] = new int[NUMBER_OF_DAYS];
    private Drawable[] weatherIconDrawable = new Drawable[NUMBER_OF_DAYS];
    private String temperatureUnit = "°F";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_long_term_weather_forecast, container, false);
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("weather.xml", 0);

        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            maxTemps[i] = rootView.findViewById(getResources().getIdentifier("maxTemp" + (i + 1), "id", getContext().getPackageName()));
            minTemps[i] = rootView.findViewById(getResources().getIdentifier("minTemp" + (i + 1), "id", getContext().getPackageName()));
            weatherImages[i] = rootView.findViewById(getResources().getIdentifier("weatherImage" + (i + 1), "id", getContext().getPackageName()));
            days[i] = rootView.findViewById(getResources().getIdentifier("day" + (i + 1), "id", getContext().getPackageName()));

            resource[i] = getResources().getIdentifier("icon_" + sharedPreferences.getString("image_code_" + (i + 1), "44"), "drawable", Objects.requireNonNull(getContext()).getPackageName());
            weatherIconDrawable[i] = getResources().getDrawable(resource[i], null);
            maxTemps[i].setText(sharedPreferences.getString("high_temperature_" + (i + 1), "NULL") + temperatureUnit);
            minTemps[i].setText(sharedPreferences.getString("low_temperature_" + (i + 1), "NULL") + temperatureUnit);
            weatherImages[i].setImageDrawable(weatherIconDrawable[i]);
            days[i].setText(sharedPreferences.getString("day_" + (i + 1), "NULL"));
        }

        return rootView;
    }
}
