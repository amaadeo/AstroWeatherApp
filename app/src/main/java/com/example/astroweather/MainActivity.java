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

import com.example.astro.R;
import com.example.astroweather.settings.SelectCityActivity;
import com.example.astroweather.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    public static final int PORTRAIT = 1;
    public static final int LANDSCAPE = 2;
    private TextView longitudeText;
    private TextView latitudeText;
    private ViewPager viewPagerPhonePortrait;
    private ViewPager viewPagerPhoneLandscape;
    private ViewPager viewPagerTabletPortrait;
    private PagerAdapter pagerAdapter;
    private String longitude;
    private String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpLayout();
        initElements();
        getConfigValues();
        setLongitudeLatitude();
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
            viewPagerPhonePortrait = findViewById(R.id.viewPagerPhonePortrait);
            setUpPortraitAdapter(viewPagerPhonePortrait);
        } else if (config.orientation == LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape_phone);
            viewPagerPhoneLandscape = findViewById(R.id.viewPagerPhoneLandscape);
            setUpLandscapeAdapter(viewPagerPhoneLandscape);
        }
    }

    private void setTabletLayout(Configuration config) {
        if (config.orientation == PORTRAIT) {
            setContentView(R.layout.activity_main_portrait_tablet);
            viewPagerTabletPortrait = findViewById(R.id.viewPagerTabletPortrait);
            setUpPortraitAdapter(viewPagerTabletPortrait);
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
        SharedPreferences sharedPreferences = getSharedPreferences("config.xml", 0);
        longitude = sharedPreferences.getString("current_longitude", String.valueOf(getResources().getString(R.string.default_longitude)));
        latitude = sharedPreferences.getString("current_latitude", String.valueOf(getResources().getString(R.string.default_latitude)));
    }

    private void setLongitudeLatitude() {
        longitudeText.setText(longitude);
        latitudeText.setText(latitude);
    }


    //TODO

    @Override
    public void onBackPressed() {
        setUpLayout();
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
}
