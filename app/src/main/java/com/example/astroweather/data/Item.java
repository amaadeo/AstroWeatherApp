package com.example.astroweather.data;

import org.json.JSONObject;

public class Item implements JSONPopulator {

    private Condition condition;

    @Override
    public void populate(JSONObject data) {
        this.condition = new Condition();
        this.condition.populate(data.optJSONObject("condition"));
    }
}
