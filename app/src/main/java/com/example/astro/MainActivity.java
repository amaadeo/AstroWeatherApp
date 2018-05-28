package com.example.astro;

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

public class MainActivity extends AppCompatActivity {


    private TextView longitudeText;
    private TextView latitudeText;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private String longitude;
    private String latitude;
    private int refresh;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = getResources().getConfiguration();
        if (checkSize(config)) {
            if (config.orientation == 1) {
                setContentView(R.layout.activity_main_portrait_tablet);
            } else if (config.orientation == 2) {
                setContentView(R.layout.activity_main_landscape_tablet);
            }
        } else {
            if (config.orientation == 1) {
                setContentView(R.layout.activity_main_portrait_phone);
                if (pagerAdapter == null) {
                    pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                }
                viewPager = findViewById(R.id.viewPager);
                viewPager.setAdapter(pagerAdapter);
            } else if (config.orientation == 2) {
                setContentView(R.layout.activity_main_landscape_phone);
            }
        }


        initElements();
        getConfigValues();
        setLongitudeLatitude();
/*
        thread = new Thread() {
            @Override
            public void run() {
                runThread(refresh);
            }
        };

        thread.start();*/

    }
/* private void runThread(int refreshTime) {
        while (!thread.isInterrupted()) {
            try {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Odświeżono...", Toast.LENGTH_SHORT).show();
                        setLongitudeLatitude();
                    }

                });
                Thread.sleep(MINUTE_IN_MILISECONDS * refreshTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

    private boolean checkSize(Configuration config) {
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
        refresh = Integer.valueOf(sharedPreferences.getString("current_refresh", String.valueOf(getResources().getString(R.string.default_refresh))));
    }

    private void setLongitudeLatitude() {
        longitudeText.setText(longitude);
        latitudeText.setText(latitude);
    }

    @Override
    public void onBackPressed() {

        Configuration config = getResources().getConfiguration();
        if (checkSize(config) && config.orientation == 2) {
            if (viewPager.getCurrentItem() == 0) {
                System.exit(1);
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

/*
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
*/
}
