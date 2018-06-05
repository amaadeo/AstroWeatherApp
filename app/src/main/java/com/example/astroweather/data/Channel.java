package com.example.astroweather.data;

import org.json.JSONObject;

public class Channel implements JSONPopulator {

    private Item item;
    private Units units;


    @Override
    public void populate(JSONObject data) {
        this.units = new Units();
        this.units.populate(data.optJSONObject("units"));

        this.item = new Item();
        this.item.populate(data.optJSONObject("item"));
    }

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }
}
