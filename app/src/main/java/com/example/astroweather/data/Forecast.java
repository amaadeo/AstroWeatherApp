package com.example.astroweather.data;

import org.json.JSONObject;

public class Forecast implements JSONPopulator {

    private int codeImage;
    private String day;
    private String lowTemperature;
    private String highTemperature;
    private String description;


    @Override
    public void populate(JSONObject data) {
        this.codeImage = data.optInt("code");
        this.day = data.optString("day");
        this.lowTemperature = data.optString("low");
        this.highTemperature = data.optString("high");
        this.description = data.optString("text");
    }

    public int getCodeImage() {
        return codeImage;
    }

    public String getDay() {
        return day;
    }

    public String getLowTemperature() {
        return lowTemperature;
    }

    public String getHighTemperature() {
        return highTemperature;
    }

    public String getDescription() {
        return description;
    }
}
