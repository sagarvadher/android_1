package com.example.android.sunshine;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by sagar on 6/28/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {
    public String[] mWeatherData;

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView mWeatherTextView;

        public ForecastAdapterViewHolder(View view)
        {
            super(view);
            mWeatherTextView = (TextView)view.findViewById(R.id.tv_weather_data);
        }

}
@Override
public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
{
    Context context=viewGroup.getContext();
    int layoutIdForListItem = (R.layout.forecast_list_item);
    LayoutInflater inflater= LayoutInflater.from(context);
    boolean shouldAttachToParentImmediately = false;
    View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
    return new ForecastAdapterViewHolder(view);
}
@Override
public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position){
    String DayWeather=mWeatherData[position];
    forecastAdapterViewHolder.mWeatherTextView.setText(DayWeather);

}
@Override
public int getItemCount(){
    if(null==mWeatherData)
        return 0;
    return mWeatherData.length;
}
public void setmWeatherData(String [] weatherData){
    mWeatherData = weatherData;
    notifyDataSetChanged();
}
}
