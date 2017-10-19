package xero.test.weatherapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import xero.test.weatherapp.R;
import xero.test.weatherapp.model.weathermap.Forecast;
import xero.test.weatherapp.util.Util;

/**
 * Created by Letícia on 08/10/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private final Context context;
    private final Forecast forecast;

    public ForecastAdapter(Context context, Forecast forecast) {
        this.context = context;
        this.forecast = forecast;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return forecast.getListWithUniqueItens().size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.forecast_day_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String dayOfWeek = forecast.getListWithUniqueItens().get(position).getDayOfWeek();

        holder.txtViewWeekDay.setText(dayOfWeek);

        holder.txtViewMinTemp.setText(context.getString(R.string.temperature,
                String.format(Locale.getDefault(), "%.0f",
                        forecast.getMinTempByDay(dayOfWeek)), ""));

        holder.txtViewMaxTemp.setText(context.getString(R.string.temperature,
                String.format(Locale.getDefault(), "%.0f",
                        forecast.getMaxTempByDay(dayOfWeek)), ""));

        Glide.with(context)
                .load(Util.URL_ICON.replace("%ICON%",
                        forecast.getListWithUniqueItens().get(position).getWeather().get(0).getIcon()))
                .into(holder.imgWeatherIcon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.forecastItem_Day_WeatherIcon) ImageView imgWeatherIcon;
        @BindView(R.id.forecastItem_MinTemp) TextView txtViewMinTemp;
        @BindView(R.id.forecastItem_MaxTemp) TextView txtViewMaxTemp;
        @BindView(R.id.forecastItem_WeekDay) TextView txtViewWeekDay;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
