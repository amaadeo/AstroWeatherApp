package com.example.astro;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import javax.xml.validation.Validator;

import static java.util.regex.Pattern.compile;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String VALUE_REGEX = "^-?\\d*\\.\\d+$|^-?\\d+$";
    private final static String TIME_REGEX = "\\d+";

    private EditText longitudeValue;
    private EditText latitudeValue;
    private EditText refreshValue;
    private Button saveButton;
    private Button loadButton;
    private Button defaultButton;

    private String longitude;
    private String latitude;
    private String refresh;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        longitudeValue = findViewById(R.id.longitudeValue);
        latitudeValue = findViewById(R.id.latitudeValue);
        refreshValue = findViewById(R.id.refreshValue);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);
        defaultButton = findViewById(R.id.defaultButton);

        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        defaultButton.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("config.xml", 0);

        loadConfig("current");
        setValuesText();

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
                saveCurrentConfig();
                setValuesText();
                Toast.makeText(SettingsActivity.this, "Poprawnie wczytano dane!", Toast.LENGTH_LONG).show();
                break;

            case R.id.defaultButton:
                setValues();
                loadConfig("default");
                saveCurrentConfig();
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
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("custom_longitude", longitudeValue);
                    editor.putString("custom_latitude", latitudeValue);
                    editor.putString("custom_refresh", refreshValue);

                    editor.apply();
                    saveCurrentConfig();
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

    private void saveCurrentConfig() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_longitude", longitude);
        editor.putString("current_latitude", latitude);
        editor.putString("current_refresh", refresh);

        editor.apply();
    }

    private void loadConfig(String configType) {
        longitude = sharedPreferences.getString(configType + "_longitude", String.valueOf(getResources().getString(R.string.default_longitude)));
        latitude = sharedPreferences.getString(configType + "_latitude", String.valueOf(getResources().getString(R.string.default_latitude)));
        refresh = sharedPreferences.getString(configType + "_refresh", String.valueOf(getResources().getString(R.string.default_refresh)));

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
}
