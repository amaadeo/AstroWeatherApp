package com.example.astroweather.weather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.astro.R;
import com.example.astroweather.data.Channel;
import com.example.astroweather.service.WeatherServiceCallBack;
import com.example.astroweather.service.YahooWeatherService;

public class BasicDataFragment extends Fragment implements WeatherServiceCallBack {

    private TextView cityNameText;
    private TextView temperatureText;
    private TextView pressureText;
    private TextView descriptionText;
    private ImageView weatherImage;

    private YahooWeatherService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_basic_data, container, false);


        cityNameText = rootView.findViewById(R.id.cityNameText);
        temperatureText = rootView.findViewById(R.id.temperatureText);
        pressureText = rootView.findViewById(R.id.pressureText);
        descriptionText = rootView.findViewById(R.id.descriptionText);
        weatherImage = rootView.findViewById(R.id.weatherImage);


        service = new YahooWeatherService(this);

        service.refreshWeather("Lodz, PL");

        return rootView;
    }

    @Override
    public void serviceSuccess(Channel channel) {

    }

    @Override
    public void serviceFailure(Exception excepiton) {

    }
}
