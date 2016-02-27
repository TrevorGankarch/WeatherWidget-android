package org.cerion.weatherwidget.Forecast;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.cerion.weatherwidget.R;

public class Icon {

    /*
     A machine-readable text summary of this data point, suitable for selecting an icon for display.
     If defined, this property will have one of the following values:
     clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night.
     (Developers should ensure that a sensible default is defined, as additional values, such as hail, thunderstorm, or tornado, may be defined in the future.)
    */

    private final String mName;

    public Icon(String name) {
        mName = name;
    }

    public int getResourceId() {
        int id = 0;
        if(mName.contentEquals("clear-day"))
            id = R.drawable.sunny;
        else if(mName.contentEquals("clear-night"))
            id = R.drawable.clear_night;
        else if(mName.contentEquals("rain"))
            id = R.drawable.rain;
        else if(mName.contentEquals("snow"))
            id = R.drawable.snow;
        else if(mName.contentEquals("sleet"))
            id = R.drawable.hail;
        else if(mName.contentEquals("wind"))
            id = R.drawable.sunny; //TODO, need icon
        else if(mName.contentEquals("fog"))
            id = R.drawable.fog;
        else if(mName.contentEquals("cloudy"))
            id = R.drawable.cloudy;
        else if(mName.contentEquals("partly-cloudy-day"))
            id = R.drawable.partly_cloudy;
        else if(mName.contentEquals("partly-cloudy-night"))
            id = R.drawable.cloudy_night;

        return id;
    }

    public Drawable getDrawable(Context context) {
        return context.getResources().getDrawable(getResourceId(),context.getTheme());
    }

}
