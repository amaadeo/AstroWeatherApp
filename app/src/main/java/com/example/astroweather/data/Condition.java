package com.example.astroweather.data;

import org.json.JSONObject;

public class Condition implements JSONPopulator {

    private int code;
    private int temperature;
    private String description;
    private String date;

    @Override
    public void populate(JSONObject data) {
        this.code = data.optInt("code");
        this.temperature = data.optInt("temp");
        this.description = data.optString("text");
        this.date = data.optString("date");
    }

    public String getDate() {
        return date;
    }

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }
}
