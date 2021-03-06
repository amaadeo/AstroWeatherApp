package com.example.astroweather.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.astro.R;
import com.example.astroweather.MainActivity;
import com.example.astroweather.data.Atmosphere;
import com.example.astroweather.data.Channel;
import com.example.astroweather.data.Item;
import com.example.astroweather.data.Location;
import com.example.astroweather.data.Wind;
import com.example.astroweather.database.DatabaseHelper;
import com.example.astroweather.service.WeatherServiceCallBack;
import com.example.astroweather.service.YahooWeatherService;

import java.sql.SQLOutput;
import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String VALUE_REGEX = "^-?\\d*\\.\\d+$|^-?\\d+$";
    private final static String TIME_REGEX = "\\d+";
    private static final int NUMBERS_OF_DAYS = 6;

    private EditText longitudeValue;
    private EditText latitudeValue;
    private EditText refreshValue;
    private Button saveButton;
    private Button loadButton;
    private Button defaultButton;
    private Spinner spinnerTemperatureUnit;
    private Spinner spinnerSpeedUnit;

    private String longitude;
    private String latitude;
    private String refresh;
    private SharedPreferences sharedPreferences;
    private SharedPreferences shared;
    private TemperatureUnitSpinnerAdapter temperatureUnitSpinnerAdapter;
    private SpeedUnitSpinnerAdapter speedUnitSpinnerAdapter;
    private String temperatureUnit;
    private String speedUnit;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initElements();
        sharedPreferences = getSharedPreferences("config.xml", 0);
        shared = getSharedPreferences("weather.xml", 0);
        loadConfig("current");
        setValuesText();
        temperatureUnit = shared.getString("temperature_unit", "0");
        speedUnit = shared.getString("speed_unit", "0");

        initTemperatureUnitSpinnerAdapter();
        initSpeedUnitSpinnerAdapter();
    }

    private void initTemperatureUnitSpinnerAdapter() {
        temperatureUnitSpinnerAdapter = new TemperatureUnitSpinnerAdapter(this);
        spinnerTemperatureUnit.setAdapter(temperatureUnitSpinnerAdapter);

        spinnerTemperatureUnit.setSelection(Integer.parseInt(temperatureUnit));
        spinnerTemperatureUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        temperatureUnit = "0";
                        break;
                    case 1:
                        temperatureUnit = "1";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpeedUnitSpinnerAdapter() {
        speedUnitSpinnerAdapter = new SpeedUnitSpinnerAdapter(this);
        spinnerSpeedUnit.setAdapter(speedUnitSpinnerAdapter);

        spinnerSpeedUnit.setSelection(Integer.parseInt(speedUnit));
        spinnerSpeedUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        speedUnit = "0";
                        break;
                    case 1:
                        speedUnit = "1";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initElements() {
        longitudeValue = findViewById(R.id.longitudeValue);
        latitudeValue = findViewById(R.id.latitudeValue);
        refreshValue = findViewById(R.id.refreshValue);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);
        defaultButton = findViewById(R.id.defaultButton);
        spinnerTemperatureUnit = findViewById(R.id.spinnerTemperatureUnit);
        spinnerSpeedUnit = findViewById(R.id.spinnerSpeedUnit);

        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        defaultButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                setValues();
                saveCustomConfig(latitude, longitude, refresh);
                break;

            case R.id.loadButton:
                setValues();
                loadConfig("custom");
                saveConfig("current");
                setValuesText();
                Toast.makeText(SettingsActivity.this, "Poprawnie wczytano dane!", Toast.LENGTH_LONG).show();
                break;

            case R.id.defaultButton:
                setValues();
                loadConfig("default");
                saveConfig("current");
                setValuesText();
                Toast.makeText(SettingsActivity.this, "Poprawnie wczytano dane!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void saveCustomConfig(String latitudeValue, String longitudeValue, String refreshValue) {
        if (!latitudeValue.equals("") && !longitudeValue.equals("") && !refreshValue.equals("")) {
            if (latitudeValue.matches(VALUE_REGEX) && longitudeValue.matches(VALUE_REGEX) && refreshValue.matches(TIME_REGEX)) {
                if ((Double.valueOf(latitudeValue) >= -90 && Double.valueOf(latitudeValue) <= 90) &&
                        (Double.valueOf(longitudeValue) >= -180 && Double.valueOf(longitudeValue) <= 180) &&
                        (Integer.valueOf(refreshValue) > 0 && Integer.valueOf(refreshValue) <= 60)) {
                    saveConfig("custom");
                    saveConfig("current");
                    Toast.makeText(SettingsActivity.this, "Poprawnie zapisano nowe dane!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Dane są poza zakresem!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(SettingsActivity.this, "Wprowadzone dane są niepoprawne!", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(SettingsActivity.this, "Wszystkie pola muszą być wypełnione!", Toast.LENGTH_LONG).show();
        }
    }

    private void saveConfig(String configType) {
       // YahooWeatherService service = new YahooWeatherService(this);

        SharedPreferences.Editor edit = shared.edit();

        edit.putString("temperature_unit", temperatureUnit);
        edit.putString("speed_unit", speedUnit);

        edit.apply();

        Intent myIntent = new Intent(SettingsActivity.this, MainActivity.class);
        myIntent.putExtra("cityName", latitude + "," + longitude); //Optional parameters
        startActivity(myIntent);
    }

    private void loadConfig(String configType) {
        longitude = shared.getString("longitude", String.valueOf(getResources().getString(R.string.default_longitude)));
        latitude = shared.getString("latitude", String.valueOf(getResources().getString(R.string.default_latitude)));
        refresh = shared.getString(configType + "_refresh", String.valueOf(getResources().getString(R.string.default_refresh)));
    }

    private void setValues() {
        setLatitude(String.valueOf(latitudeValue.getText()));
        setLongitude(String.valueOf(longitudeValue.getText()));
        setRefresh(String.valueOf(refreshValue.getText()));
    }

    private void setValuesText() {
        latitudeValue.setText(latitude);
        longitudeValue.setText(longitude);
        refreshValue.setText(refresh);
    }

    private void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    private void setRefresh(String refresh) {
        this.refresh = refresh;
    }
/*
    @Override
    public void serviceSuccess(Channel channel) {
        databaseHelper = new DatabaseHelper(this);
        Item item = channel.getItem();
        Location location = channel.getLocation();
        Atmosphere atmosphere = channel.getAtmosphere();
        Wind wind = channel.getWind();

        edit.putString("city", location.getCity());
        edit.putString("country", location.getCountry());
        edit.putString("wind_direction", wind.getDirection());
        edit.putString("wind_speed", wind.getSpeed());
        edit.putString("humidity", atmosphere.getHumidity());
        edit.putString("pressure", atmosphere.getPressure());
        edit.putString("visibility", atmosphere.getVisibility());
        edit.putString("longitude", item.getLongitude());
        edit.putString("latitude", item.getLatitude());
        edit.putString("current_image_code", item.getCondition().getCode());
        edit.putString("current_date", item.getCondition().getDate());
        edit.putString("current_temperature", item.getCondition().getTemperature());
        edit.putString("current_description", item.getCondition().getDescription());

        for (int i = 1; i < NUMBERS_OF_DAYS; i++) {
            edit.putString("image_code_" + i, item.getForecast(i).getCodeImage());
            edit.putString("day_" + i, item.getForecast(i).getDay());
            edit.putString("high_temperature_" + i, item.getForecast(i).getHighTemperature());
            edit.putString("low_temperature_" + i, item.getForecast(i).getLowTemperature());
            edit.putString("description_" + i, item.getForecast(i).getDescription());
        }


        databaseHelper.insertData(location.getCity());

        System.out.println("CITY: " + location.getCity());
        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude: " + longitude);
    }

    @Override
    public void serviceFailure(Exception excepiton) {

    }*/
}
