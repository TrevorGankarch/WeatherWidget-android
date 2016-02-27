package org.cerion.weatherwidget;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.cerion.weatherwidget.Forecast.Daily;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ForecastListAdapter extends ArrayAdapter<Daily.Entry> {

    private static final SimpleDateFormat mDayFormat = new SimpleDateFormat("EEE", Locale.US);
    private static final SimpleDateFormat mDateFormat = new SimpleDateFormat("d MMM", Locale.US);

    public ForecastListAdapter(Context context) {
        super(context, R.layout.forecast_item);
    }

    public void refresh(List<Daily.Entry> items) {
        clear();
        addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Daily.Entry item = getItem(position);

        if(convertView == null) {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.forecast_item, parent, false);
        }

        ((TextView)convertView.findViewById(R.id.high)).setText(item.getHigh());
        ((TextView)convertView.findViewById(R.id.low)).setText(item.getLow());
        ((TextView)convertView.findViewById(R.id.summary)).setText(item.summary);
        ((ImageView)convertView.findViewById(R.id.imageView)).setImageDrawable(item.icon.getDrawable( getContext() ));

        String day = mDayFormat.format(item.date);
        String date = mDateFormat.format(item.date);
        ((TextView)convertView.findViewById(R.id.date)).setText(date);
        ((TextView)convertView.findViewById(R.id.day)).setText(day);

        return convertView;
    }

}
