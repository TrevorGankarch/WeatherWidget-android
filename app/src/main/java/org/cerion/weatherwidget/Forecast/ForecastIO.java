package org.cerion.weatherwidget.Forecast;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForecastIO {

    private static final String TAG = ForecastIO.class.getSimpleName();
    private static final String API_KEY = "35904f73085928bbf656d40a58952a7c";


    public static Forecast getForecast(double latitude, double longitude) {

        //https://api.forecast.io/forecast/APIKEY/LATITUDE,LONGITUDE
        String url = String.format("https://api.forecast.io/forecast/%s/%f,%f", API_KEY, latitude, longitude);
        Log.d(TAG,url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();

            JSONObject json = new JSONObject(result);
            return new Forecast(json);

        } catch (JSONException|IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
