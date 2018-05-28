package com.example.astro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 5;

    public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
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
                return new LongTermWeatherForecastFragment();
            }
            case 3: {
                return new MoonFragment();
            }
            case 4: {
                return new SunFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
