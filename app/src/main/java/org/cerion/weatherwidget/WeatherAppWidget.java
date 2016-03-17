package org.cerion.weatherwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 */
public class WeatherAppWidget extends AppWidgetProvider {

    private static final String TAG = WeatherAppWidget.class.getSimpleName();
    //private static final String ACTION_TAP_WIDGET = "actionTapWidget";
    //private static final String ACTION_LOCATION = "actionLocation";

    //private static final String EXTRA_APPWIDGET_ID = "widgetId";
    //private static SimpleDateFormat mDateFormat = new SimpleDateFormat("hh:mm a");
    //private static Date mLastUpdate = new Date();
    //private static double mLongitude;
    //private static double mLatitude;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);
        views.setTextViewText(R.id.weather_text, "");

        //Intent intent = new Intent(context, WeatherAppWidget.class);
        //intent.setAction(ACTION_TAP_WIDGET);
        //Log.d(TAG,"using widgetId " + appWidgetId);
        //intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);

        Intent activityIntent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

        views.setOnClickPendingIntent(R.id.root, pendingIntent);


        // Instruct the widget manager to updateDisplay the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        //refresh(context, appWidgetId);
        //runUpdateService(context, appWidgetId);


        Location location = GPS.getLastLocation(context);
        if(location != null) {
            //get location details, will update display when complete
            LocationDetailsService.start(context, location);
        }
        else {
            //TODO, request location
            // update display to indicate there is no location
            updateDisplay(context, appWidgetId);
        }
    }

    /*
    public static void runUpdateService(Context context, int appWidgetId) {

        Log.d(TAG, "starting service id=" + appWidgetId);
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction("DO NOTHING ACTION");
        context.startService(intent);

    }
    */

    /*
    private static void refresh(Context context, int widgetId) {

        Log.d(TAG, "getting location for ID " + widgetId);

        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.putExtra(EXTRA_APPWIDGET_ID, widgetId);
        intent.setAction(ACTION_LOCATION);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        GPS.getLocation(context, pendingIntent);
    }
    */

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so updateDisplay all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        Log.d(TAG,"onEnabled");
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().contentEquals(LocationDetailsService.ACTION_DETAILS_READY)) {

            Log.d(TAG, "Details Ready");
            //Update display of all widgets
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int[] ids = manager.getAppWidgetIds(new ComponentName(context,WeatherAppWidget.class));
            for(int id : ids) {
                updateDisplay(context,id);
            }
            //onUpdate(context,manager,ids);
        }

    }


    private static void updateDisplay(Context context, int widgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);

        LocationDetails locationDetails = LocationDetailsService.mLastResult;
        if(locationDetails != null && locationDetails.forecast.current != null) {
            //views.setTextViewText(R.id.temperature, Formats.mTemperatureFormat.format(locationDetails.forecast.current.temperature) + "°");
            views.setTextViewText(R.id.temperature, locationDetails.forecast.current.getTemperature());
            views.setTextViewText(R.id.weather_text, locationDetails.forecast.current.summary);
            views.setTextViewText(R.id.location, locationDetails.getDisplayAddress());
            views.setImageViewResource(R.id.imageView,locationDetails.forecast.current.icon.getResourceId());
        }
        else {
            views.setTextViewText(R.id.temperature, "__°");
            views.setTextViewText(R.id.weather_text, "Unknown");
            views.setTextViewText(R.id.location, "Somewhere");
        }

        // Instruct the widget manager to updateDisplay the widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(widgetId, views);
    }

    /**
     * Uses last known location to update widget, makes net request for info such as city/weather/etc
     */
    /*
    public static class UpdateWidgetService extends IntentService {

        public UpdateWidgetService() {
            super("name");
        }

        @Override
        protected void onHandleIntent(Intent intent) {

            Log.d(TAG,"onHandleIntent action=" + intent.getAction());

            int widgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID, -1);
            //Location location = (Location)intent.getExtras().get(LocationManager.KEY_LOCATION_CHANGED);
            Location location = GPS.getLastLocation(this);

            if (widgetId != -1)
                processUpdate(widgetId,location);
            else
                Log.e(TAG,"missing id");

        }

        private void processUpdate(int widgetId, Location location) {

            mLastUpdate = new Date(); //last updateDisplay

            LocationDetails locationDetails = null;
            if(location != null)
                locationDetails = new LocationDetails(this,location);

            updateDisplay(this, widgetId, locationDetails);


        }

    }
    */




}

