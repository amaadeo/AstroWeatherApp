package com.example.astroweather.astro;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.astro.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SunFragment extends Fragment {

    private static final int MINUTE_IN_MILISECONDS = 60000;
    private TextView sunriseTimeText;
    private TextView sunriseAzimuthText;
    private TextView sunsetTimeText;
    private TextView sunsetAzimuthText;
    private TextView twilightTimeText;
    private TextView dawnTimeText;

    private Date date;
    private DateFormat yearFormat;
    private DateFormat monthFormat;
    private DateFormat dayFormat;
    private DateFormat hourFormat;
    private DateFormat minuteFormat;

    private int refreshTime;


    AstroCalculator astroCalculator;
    AstroCalculator.Location location;
    AstroDateTime astroDateTime;

    private Thread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_sun, container, false);


        initElements(rootView);
        initTime();
        setAstroCalculator();
        setData();

        return rootView;
    }

    private void initTime() {
        date = new Date();
        yearFormat = new SimpleDateFormat("yyyy");
        monthFormat = new SimpleDateFormat("MM");
        dayFormat = new SimpleDateFormat("dd");
        hourFormat = new SimpleDateFormat("hh");
        minuteFormat = new SimpleDateFormat("mm");
    }

    private void initElements(ViewGroup rootView) {
        sunriseTimeText = rootView.findViewById(R.id.sunriseTimeText);
        sunriseAzimuthText = rootView.findViewById(R.id.sunriseAzimuthText);
        sunsetTimeText = rootView.findViewById(R.id.sunsetTimeText);
        sunsetAzimuthText = rootView.findViewById(R.id.sunsetAzimuthText);
        twilightTimeText = rootView.findViewById(R.id.twilightTimeText);
        dawnTimeText = rootView.findViewById(R.id.dawnTimeText);
    }

    private void runThread(int refreshTime) {

        try {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getActivity(), "Odświeżon22o...", Toast.LENGTH_SHORT).show();
                    setAstroCalculator();
                    setData();
                }

            });
            Thread.sleep(MINUTE_IN_MILISECONDS * refreshTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        thread = new Thread() {
            @Override
            public void run() {
                runThread(refreshTime);
            }
        };
        System.out.println(thread.getName());
        thread.start();
    }

    @Override
    public void onStop() {
        super.onStop();

        thread.interrupt();
    }

    private int getTimeZone() {
        Calendar c = Calendar.getInstance();

        TimeZone z = c.getTimeZone();
        int offset = z.getRawOffset();
        if (z.inDaylightTime(new Date())) {
            offset = offset + z.getDSTSavings();
        }
        return offset / 1000 / 60 / 60;
    }

    private void setAstroCalculator() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("config.xml", 0);
        astroDateTime = new AstroDateTime(
                Integer.valueOf(yearFormat.format(date)),
                Integer.valueOf(monthFormat.format(date)),
                Integer.valueOf(dayFormat.format(date)),
                Integer.valueOf(hourFormat.format(date)),
                Integer.valueOf(minuteFormat.format(date)),
                Integer.valueOf(yearFormat.format(date)),
                getTimeZone(),
                false
        );

        location = new AstroCalculator.Location(
                Double.valueOf(sharedPref.getString("current_latitude", String.valueOf(getResources().getString(R.string.default_latitude)))),
                Double.valueOf(sharedPref.getString("current_longitude", String.valueOf(getResources().getString(R.string.default_longitude))))
        );

        astroCalculator = new AstroCalculator(
                astroDateTime,
                location
        );

        refreshTime = Integer.valueOf(sharedPref.getString("current_refresh", String.valueOf(getResources().getString(R.string.default_refresh))));
    }

    private void setData() {
        sunriseTimeText.setText(String.valueOf(astroCalculator.getSunInfo().getSunrise()));
        sunriseAzimuthText.setText(String.valueOf(astroCalculator.getSunInfo().getAzimuthRise()));
        sunsetTimeText.setText(String.valueOf(astroCalculator.getSunInfo().getSunset()));
        sunsetAzimuthText.setText(String.valueOf(astroCalculator.getSunInfo().getAzimuthSet()));
        twilightTimeText.setText(String.valueOf(astroCalculator.getSunInfo().getTwilightEvening()));
        dawnTimeText.setText(String.valueOf(astroCalculator.getSunInfo().getTwilightMorning()));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
