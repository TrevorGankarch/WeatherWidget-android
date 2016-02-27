package org.cerion.weatherwidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.cerion.weatherwidget.Forecast.Daily;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    TextView mTextLocation;
    ListView mListView;
    final List<String> mItems = new ArrayList<>();
    ForecastListAdapter mAdapter;


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadDetails();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mListView = (ListView)findViewById(android.R.id.list);
        mAdapter = new ForecastListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(findViewById(android.R.id.empty));

        mTextLocation = (TextView)findViewById(R.id.location);


        if(LocationDetailsService.mLastResult != null)
            loadDetails();

        Location location = GPS.getLastLocation(this);

        if(location != null) {
            boolean bUpdate = false;
            if(LocationDetailsService.mLastResult == null)
                bUpdate = true;
            else {
                Date updated = LocationDetailsService.mLastResult.updated;
                Date now = new Date();
                long diff = now.getTime() - updated.getTime();
                diff /= 1000;
                if(diff > 60 * 5)
                    bUpdate = true;
                else
                    Log.d(TAG,"Already updated");
            }

            if(bUpdate)
                LocationDetailsService.start(this, location);
        }

    }

    private void loadDetails() {

        mItems.clear();
        if(LocationDetailsService.mLastResult != null) {
            Daily daily = LocationDetailsService.mLastResult.forecast.daily;

            mAdapter.refresh(daily.entries);
            mTextLocation.setText(LocationDetailsService.mLastResult.getDisplayAddress());
        }

        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter(LocationDetailsService.ACTION_DETAILS_READY);
        registerReceiver(mReceiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

}
