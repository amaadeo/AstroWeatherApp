package com.example.astroweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.astro.R;
import com.example.astroweather.data.Atmosphere;
import com.example.astroweather.data.Channel;
import com.example.astroweather.data.Condition;
import com.example.astroweather.data.Item;
import com.example.astroweather.data.Location;
import com.example.astroweather.data.Wind;
import com.example.astroweather.service.WeatherServiceCallBack;
import com.example.astroweather.service.YahooWeatherService;
import com.example.astroweather.settings.SelectCityActivity;
import com.example.astroweather.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallBack {

    public static final int PORTRAIT = 1;
    public static final int LANDSCAPE = 2;
    public static final int NUMBERS_OF_DAYS = 6;
    private TextView longitudeText;
    private TextView latitudeText;
    private PagerAdapter pagerAdapter;
    private String longitude;
    private String latitude;
    private YahooWeatherService service;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = new YahooWeatherService(this);
        service.refreshWeather("Lodz");
        sharedPreferences = getSharedPreferences("weather.xml", 0);

    }

    private void setUpLayout() {
        Configuration config = getResources().getConfiguration();

        if (isTablet(config)) {
            setTabletLayout(config);
        } else {
            setPhoneLayout(config);
        }
    }

    private void setPhoneLayout(Configuration config) {
        if (config.orientation == PORTRAIT) {
            setContentView(R.layout.activity_main_portrait_phone);
            ViewPager viewPagerPhonePortrait = findViewById(R.id.viewPagerPhonePortrait);
            setUpPortraitAdapter(viewPagerPhonePortrait);
        } else if (config.orientation == LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape_phone);
            ViewPager viewPagerPhoneLandscape = findViewById(R.id.viewPagerPhoneLandscape);
            setUpLandscapeAdapter(viewPagerPhoneLandscape);
        }
    }

    private void setTabletLayout(Configuration config) {
        if (config.orientation == PORTRAIT) {
            setContentView(R.layout.activity_main_portrait_tablet);
            ViewPager viewPagerTabletPortrait = findViewById(R.id.viewPagerTabletPortrait);
            setUpLandscapeAdapter(viewPagerTabletPortrait);
        } else if (config.orientation == LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape_tablet);
        }
    }

    private void setUpPortraitAdapter(ViewPager viewPager) {
        pagerAdapter = new ScreenSlidePagerAdapterPortrait(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    private void setUpLandscapeAdapter(ViewPager viewPager) {
        pagerAdapter = new ScreenSlidePagerAdapterLandscape(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    private boolean isTablet(Configuration config) {
        boolean xlarge = ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    private void initElements() {
        longitudeText = findViewById(R.id.longitudeText);
        latitudeText = findViewById(R.id.latitudeText);
    }

    private void getConfigValues() {
        longitude = sharedPreferences.getString("longitude", String.valueOf(getResources().getString(R.string.default_longitude)));
        latitude = sharedPreferences.getString("latitude", String.valueOf(getResources().getString(R.string.default_latitude)));
    }

    private void setLongitudeLatitude() {
        longitudeText.setText(longitude);
        latitudeText.setText(latitude);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        service = new YahooWeatherService(this);
        service.refreshWeather("Lodz");
        sharedPreferences = getSharedPreferences("weather.xml", 0);
        setUpLayout();
        initElements();
        getConfigValues();
        setLongitudeLatitude();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh: {
                //TODO
                return true;
            }

            case R.id.city: {
                startActivity(new Intent(this, SelectCityActivity.class));
                return true;
            }

            case R.id.settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }

            case R.id.about: {
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            }

            case R.id.exit: {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();
        Location location = channel.getLocation();
        Atmosphere atmosphere = channel.getAtmosphere();
        Wind wind = channel.getWind();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("city", location.getCity());
        editor.putString("country", location.getCountry());
        editor.putString("wind_direction", wind.getDirection());
        editor.putString("wind_speed", wind.getSpeed());
        editor.putString("humidity", atmosphere.getHumidity());
        editor.putString("pressure", atmosphere.getPressure());
        editor.putString("visibility", atmosphere.getVisibility());
        editor.putString("longitude", item.getLongitude());
        editor.putString("latitude", item.getLatitude());
        editor.putString("current_image_code", item.getCondition().getCode());
        editor.putString("current_date", item.getCondition().getDate());
        editor.putString("current_temperature", item.getCondition().getTemperature());
        editor.putString("current_description", item.getCondition().getDescription());

        for (int i = 1; i < NUMBERS_OF_DAYS; i++) {
            editor.putString("image_code_" + i, item.getForecast(i).getCodeImage());
            editor.putString("day_" + i, item.getForecast(i).getDay());
            editor.putString("high_temperature_" + i, item.getForecast(i).getHighTemperature());
            editor.putString("low_temperature_" + i, item.getForecast(i).getLowTemperature());
            editor.putString("description_" + i, item.getForecast(i).getDescription());
        }
        editor.apply();

        setUpLayout();
        initElements();
        getConfigValues();
        setLongitudeLatitude();
    }

    @Override
    public void serviceFailure(Exception excepiton) {
        Toast.makeText(MainActivity.this, "No internet connection!", Toast.LENGTH_SHORT).show();
    }
}
