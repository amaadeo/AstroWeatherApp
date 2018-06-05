package com.example.astroweather.service;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import com.example.astroweather.data.Channel;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class YahooWeatherService {

    private WeatherServiceCallBack callBack;
    private Exception error;
    private String location;

    public YahooWeatherService(WeatherServiceCallBack callBack) {
        this.callBack = callBack;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshWeather(final String location) {
        this.location = location;
        new  AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", location);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return result.toString();

                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s == null && error != null) {
                    callBack.serviceFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.getJSONObject("query");

                    int count = queryResults.optInt("count");

                    if (count == 0) {
                        callBack.serviceFailure(new Exception());
                        return;
                    }

                    Channel channel = new Channel();
                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callBack.serviceSuccess(channel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute(location);
    }


    public String getLocation() {
        return location;
    }

}
