package org.cerion.weatherwidget.Forecast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Daily {

    /*
    "summary":"Light rain throughout the week, with temperatures peaking at 60°F on Wednesday.",
            "icon":"rain",
            "data":[
    {
        "time":1455523200,
            "summary":"Light rain in the morning and overnight.",
            "icon":"rain",
            "sunriseTime":1455549324,
            "sunsetTime":1455586723,
            "moonPhase":0.27,
            "precipIntensity":0.0027,
            "precipIntensityMax":0.0057,
            "precipIntensityMaxTime":1455523200,
            "precipProbability":0.26,
            "precipType":"rain",
            "temperatureMin":49.51,
            "temperatureMinTime":1455606000,
            "temperatureMax":58.33,
            "temperatureMaxTime":1455577200,
            "apparentTemperatureMin":47,
            "apparentTemperatureMinTime":1455606000,
            "apparentTemperatureMax":58.33,
            "apparentTemperatureMaxTime":1455577200,
            "dewPoint":48.47,
            "humidity":0.84,
            "windSpeed":4.37,
            "windBearing":204,
            "visibility":9.63,
            "cloudCover":0.57,
            "pressure":1020.82,
            "ozone":300.68
           */


    //public Date date;
    public String summary;
    public List<Entry> entries;

    public class Entry {

        public final Date date;
        public String summary;
        public double temperatureHigh;
        public double temperatureLow;
        public Icon icon;

        private Entry(JSONObject entry) throws JSONException {

            date = new Date();
            date.setTime(1000 * entry.getLong("time"));
            summary = entry.getString("summary");
            temperatureHigh = entry.getDouble("temperatureMax");
            temperatureLow = entry.getDouble("temperatureMin");
            icon = new Icon(entry.getString("icon"));
        }

        public String getHigh() {
            return Formats.mTemperatureFormat.format(temperatureHigh);
        }

        public String getLow() {
            return Formats.mTemperatureFormat.format(temperatureLow);
        }
    }

    Daily(JSONObject daily) throws JSONException {

        //date = new Date();
        //date.setTime(1000 * daily.getLong("time"));
        summary = daily.getString("summary");

        entries = new ArrayList<>();

        JSONArray data = daily.getJSONArray("data");
        for(int i = 0; i < data.length(); i++) {
            Entry entry = new Entry(data.getJSONObject(i));
            entries.add(entry);
        }
    }
}
