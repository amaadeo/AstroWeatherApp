package com.example.astroweather.weather;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.astro.R;
import com.example.astroweather.data.Atmosphere;
import com.example.astroweather.data.Channel;
import com.example.astroweather.data.Item;
import com.example.astroweather.data.Location;
import com.example.astroweather.service.WeatherServiceCallBack;
import com.example.astroweather.service.YahooWeatherService;

import java.util.Objects;

public class BasicDataFragment extends Fragment implements WeatherServiceCallBack {

    private TextView cityNameText;
    private TextView dateTimeText;
    private ImageView weatherImage;
    private TextView temperatureText;
    private TextView descriptionText;
    private TextView pressureText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_basic_data, container, false);


        cityNameText = rootView.findViewById(R.id.cityNameText);
        dateTimeText = rootView.findViewById(R.id.dateTimeText);
        weatherImage = rootView.findViewById(R.id.weatherImage);
        temperatureText = rootView.findViewById(R.id.temperatureText);
        descriptionText = rootView.findViewById(R.id.descriptionText);
        pressureText = rootView.findViewById(R.id.pressureText);


        YahooWeatherService service = new YahooWeatherService(this);

        service.refreshWeather("Lodz");

        return rootView;
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();
        Location location = channel.getLocation();
        Atmosphere atmosphere = channel.getAtmosphere();

        int resource = getResources().getIdentifier("icon_" + item.getCondition().getCode(), "drawable", Objects.requireNonNull(getContext()).getPackageName());

        Drawable weatherIconDrawable = getResources().getDrawable(resource, null);

        cityNameText.setText(String.format("%s, %s", location.getCity(), location.getCountry()));
        dateTimeText.setText(item.getCondition().getDate().substring(0, 16));
        weatherImage.setImageDrawable(weatherIconDrawable);
        temperatureText.setText(String.format("%s°%s", String.valueOf(item.getCondition().getTemperature()), channel.getUnits().getTempertature()));
        descriptionText.setText(item.getCondition().getDescription());
        pressureText.setText(String.format("%s hPa", atmosphere.getPressure()));

    }

    @Override
    public void serviceFailure(Exception excepiton) {

    }
}
