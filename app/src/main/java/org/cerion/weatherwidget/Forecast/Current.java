package org.cerion.weatherwidget.Forecast;


import org.json.JSONException;
import org.json.JSONObject;

public class Current {

    /* Possible fields
    "time":1455252189,
    "summary":"Partly Cloudy",
    "icon":"partly-cloudy-night",
    "nearestStormDistance":16,
    "nearestStormBearing":324,
    "precipIntensity":0,
    "precipProbability":0,
    "temperature":50.34,
    "apparentTemperature":50.34,
    "dewPoint":48.26,
    "humidity":0.93,
    "windSpeed":0.57,
    "windBearing":211,
    "visibility":3.82,
    "cloudCover":0.55,
    "pressure":1019.34,
    "ozone":319.36
     */

    //private final Date time;
    public String summary;
    public Icon icon;
    private double temperature;

    protected Current(JSONObject currently) throws JSONException {

        //time = new Date();
        //time.setTime(1000 * currently.getLong("time"));
        summary = currently.getString("summary");
        temperature = currently.getDouble("temperature");
        icon = new Icon(currently.getString("icon"));
    }

    public String getTemperature() {
        return Formats.mTemperatureFormat.format(temperature);
    }
}
