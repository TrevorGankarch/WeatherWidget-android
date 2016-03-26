package org.cerion.weatherwidget;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.util.Log;

public class LocationDetailsService extends IntentService {

    private static final String TAG = LocationDetailsService.class.getSimpleName();
    private static final String ACTION_RUN = "org.cerion.weatherwidget.action.RUN";
    private static final String EXTRA_LATITUDE = "org.cerion.weatherwidget.extra.LATITUDE";
    private static final String EXTRA_LONGITUDE = "org.cerion.weatherwidget.extra.LONGITUDE";

    //Broadcast intent
    public static final String ACTION_DETAILS_READY= "org.cerion.action.DETAILS_READY";

    //New Location available, run this service to get the details
    public static final String ACTION_LOCATION_READY = "org.cerion.action.LOCATION_READY";

    public static LocationDetails mLastResult;

    public LocationDetailsService() {
        super("LocationDetailsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RUN.contentEquals(action)) {

                double latitude = intent.getDoubleExtra(EXTRA_LATITUDE,0);
                double longitude = intent.getDoubleExtra(EXTRA_LONGITUDE,0);
                onActionRun(latitude, longitude);
            } else if(ACTION_LOCATION_READY.contentEquals(action)) {

                Location location = GPS.getLastLocation(this);
                if(location != null)
                    start(this,location);
                else
                    Log.e(TAG,"Location event triggered when no location is available");

            }
        }
    }

    private void onActionRun(double latitude, double longitude) {

        mLastResult = new LocationDetails(this,latitude,longitude);

        Intent intent = new Intent();
        intent.setAction(ACTION_DETAILS_READY);
        sendBroadcast(intent);
    }

    public static void start(Context context, Location location) {
        start(context,location.getLatitude(),location.getLongitude());
    }

    private static void start(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, LocationDetailsService.class);
        intent.setAction(ACTION_RUN);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        context.startService(intent);
    }


}
