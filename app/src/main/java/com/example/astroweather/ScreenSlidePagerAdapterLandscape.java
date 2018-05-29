package com.example.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.astroweather.astro.SunMoonFragment;
import com.example.astroweather.weather.WeatherFragment;


public class ScreenSlidePagerAdapterLandscape extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 2;

    public ScreenSlidePagerAdapterLandscape(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new WeatherFragment();
            }
            case 1: {
                return new SunMoonFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
