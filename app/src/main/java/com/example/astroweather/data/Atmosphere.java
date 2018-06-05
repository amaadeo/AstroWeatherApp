package com.example.astroweather.data;

import org.json.JSONObject;

public class Atmosphere implements JSONPopulator {

    private String pressure;
    private String humidity;

    @Override
    public void populate(JSONObject data) {
        this.pressure = data.optString("pressure");
        this.humidity = data.optString("humidity");
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }
}
