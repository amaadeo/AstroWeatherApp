package com.example.astroweather.service;

import com.example.astroweather.data.Channel;

public interface WeatherServiceCallBack {

    void serviceSuccess(Channel channel);
    void serviceFailure(Exception excepiton);
}
