package org.cerion.weatherwidget;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Tools {

    /*
    public static void logIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle == null)
            Log.d("LogIntent", "null bundle");
        else {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d("LogIntent", String.format("%s %s (%s)", key,
                        value.toString(), value.getClass().getName()));
            }
        }
    }
    */

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
