package org.cerion.weatherwidget;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

class GPS {

    private static final String TAG = GPS.class.getSimpleName();

    public static Address getLocationFromLL(Context context, double latitude, double longitude) {

        Address result = null;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            result = addresses.get(0);
        }

        return result;
    }

    public static Location getLastLocation(Context context) {

        // Acquire a reference to the system Location Manager
        //LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        if ( ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            String provider = manager.getBestProvider(new Criteria(), true);
            Log.d(TAG, "provider = " + provider);

            Location location = manager.getLastKnownLocation(provider);
            if(location == null) {
                Log.e(TAG, "Failed to get location");
                requestLocationUpdate(context);
            } else {
                long ms = System.currentTimeMillis() - location.getTime();
                long diff = TimeUnit.MILLISECONDS.toMinutes(ms);
                if(diff > 60)
                    requestLocationUpdate(context);
            }

            return location;

        } else
            Log.d(TAG,"no permission");


        return null;
    }

    private static void requestLocationUpdate(Context context) {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        if ( ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            boolean bGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //boolean bNetwork = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            //When location is available notify LocationDetailsService to update
            Intent intent = new Intent(context,LocationDetailsService.class);
            intent.setAction(LocationDetailsService.ACTION_LOCATION_READY);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

            Log.d(TAG,"Requesting location update");
            if(bGPS) {
                Log.d(TAG,"using GPS");
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, pendingIntent);
                //} else if (bNetwork){
                //Log.d(TAG,"using Network");
                //locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, pendingIntent);
            } else {
                Log.d(TAG,"using passive");
                locationManager.requestSingleUpdate(LocationManager.PASSIVE_PROVIDER, pendingIntent);
            }

        } else
            Log.d(TAG,"no permission to request location update");

    }
}
