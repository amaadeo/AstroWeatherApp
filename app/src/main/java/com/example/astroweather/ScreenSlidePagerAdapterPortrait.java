package com.example.astroweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.astroweather.astro.MoonFragment;
import com.example.astroweather.astro.SunFragment;
import com.example.astroweather.weather.AdditionalDataFragment;
import com.example.astroweather.weather.BasicDataFragment;


public class ScreenSlidePagerAdapterPortrait extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 5;

    public ScreenSlidePagerAdapterPortrait(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new BasicDataFragment();
            }
            case 1: {
                return new AdditionalDataFragment();
            }
            case 2: {
                //return new LongTermWeatherForecastFragment();
            }
            case 3: {
                return new SunFragment();
            }
            case 4: {
                return new MoonFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
