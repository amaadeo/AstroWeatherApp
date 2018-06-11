package com.example.astroweather.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Item implements JSONPopulator {

    private static final int NUMBER_OF_DAYS = 6;
    private Condition condition;
    private Forecast[] forecast;
    private JSONArray jsonArray;
    private float longitude;
    private float latitude;

    @Override
    public void populate(JSONObject data) {
        this.condition = new Condition();
        this.condition.populate(data.optJSONObject("condition"));

        this.longitude = (float) data.optDouble("long");
        this.latitude = (float) data.optDouble("lat");

        this.jsonArray = data.optJSONArray("forecast");
        this.forecast = new Forecast[NUMBER_OF_DAYS];

        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            try {
                forecast[i] = new Forecast();
                forecast[i].populate(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public Condition getCondition() {
        return condition;
    }

    public Forecast getForecast(int day) {
        return forecast[day];
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
