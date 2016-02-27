package org.cerion.weatherwidget;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import org.cerion.weatherwidget.Forecast.Forecast;
import org.cerion.weatherwidget.Forecast.ForecastIO;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LocationDetails {

    //public Location location;
    public final Forecast forecast;
    //double latitude;
    //double longitude;
    public final Date updated;

    private String mDisplayAddress;



    public LocationDetails(Context context, double latitude, double longitude) {

        //Get location
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {

            addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {

                Address addr = addresses.get(0);
                if (addr != null) {
                    if (addr.getThoroughfare() != null)
                        mDisplayAddress = addr.getThoroughfare();
                    else
                        mDisplayAddress = addr.getLocality();
                } else
                    mDisplayAddress  = "Unknown";
            }

        } catch (IOException e) {
            mDisplayAddress = "Network Unavailable";
        }

        //Get weather forecast
        forecast = ForecastIO.getForecast(latitude,longitude);

        updated = new Date();
    }

    public String getDisplayAddress() {
        return mDisplayAddress;
    }



}
