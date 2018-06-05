package com.example.astroweather.data;

import org.json.JSONObject;

public class Condition implements JSONPopulator {

    private int code;
    private int temperature;
    private String description;

    @Override
    public void populate(JSONObject data) {
        this.code = data.optInt("code");
        this.temperature = data.optInt("temp");
        this.description = data.optString("text");
    }
}
