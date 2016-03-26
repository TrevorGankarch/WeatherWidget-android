package org.cerion.weatherwidget.Forecast;

import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {

    public Current current;
    public Daily daily;


    Forecast(JSONObject forecast) throws JSONException {

        current = new Current(forecast.getJSONObject("currently"));
        daily = new Daily(forecast.getJSONObject("daily"));
    }

}
