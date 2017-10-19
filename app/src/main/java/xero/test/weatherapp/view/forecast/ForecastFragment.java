package xero.test.weatherapp.view.forecast;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import xero.test.weatherapp.R;
import xero.test.weatherapp.adapters.ForecastAdapter;
import xero.test.weatherapp.model.weathermap.Forecast;
import xero.test.weatherapp.model.weathermap.Weather;
import xero.test.weatherapp.util.LocationUtils;
import xero.test.weatherapp.util.Util;
import xero.test.weatherapp.view.MainActivity;

/**
 * Created by Letícia on 09/10/2017.
 */

public class ForecastFragment extends Fragment {

    @BindView(R.id.root_layout)
    public LinearLayout linearLayoutRoot;
    @BindView(R.id.layout_main_temp)
    public LinearLayout linearLayoutMainTemp;
    @BindView(R.id.txtTitle)
    public TextView txtViewTitle;
    @BindView(R.id.layout_measures)
    public LinearLayout linearLayoutMeasures;
    @BindView(R.id.txtTemp)
    public TextView txtViewTemp;
    @BindView(R.id.txtMetricUnit)
    public TextView txtViewMetricUnit;
    @BindView(R.id.txtHumidity)
    public TextView txtViewHumidity;
    @BindView(R.id.txtWindSpeed)
    public TextView txtViewWindSpeed;
    @BindView(R.id.txtWindDeg)
    public TextView txtViewWindDeg;
    @BindView(R.id.txtClouds)
    public TextView txtViewCloudPercentage;
    @BindView(R.id.imgWeatherIcon)
    public ImageView imgWeatherIcon;
    @BindView(R.id.listForecast5Days)
    public RecyclerView listViewForecast5Days;

    private ForecastPresenter presenter;

    public void setPresenter(@NonNull ForecastPresenter presenter) {
        this.presenter = presenter;
    }

    public void uploadCityName(String cityName) {
        if(!cityName.isEmpty())
            ((MainActivity)getActivity()).setActionBarTitle(cityName);
    }

    @Override
    public void onResume() {
        if(presenter == null)
            setPresenter(new ForecastPresenter(getActivity(), this, new LocationUtils(getActivity()).getLatLongLocation()));

        presenter.start();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        ButterKnife.bind(this, view);

        txtViewMetricUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeUnit();
            }
        });

        txtViewTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.changeUnit();
            }
        });

        return view;
    }

    public void setWeather(Forecast forecast) {

        txtViewTemp.setText(String.format(Locale.getDefault(), "%.0f", forecast.getMain().getTemp()));
        txtViewMetricUnit.setText(getActivity().getString(R.string.temperature,
                "", Util.GET_UNIT_PREF_FOR_VIEWING(getActivity())));

        for (Weather w : forecast.getWeather()) {
            txtViewTitle.setText(w.getMain());
            Glide.with(this)
                    .load(Util.URL_ICON.replace("%ICON%", w.getIcon()))
                    .into(imgWeatherIcon);
        }

        txtViewHumidity.setText(getActivity().getString(R.string.percentage, forecast.getMain().getHumidity().toString()));
        txtViewCloudPercentage.setText(getActivity().getString(R.string.percentage, forecast.getClouds().getAll().toString()));
        txtViewWindSpeed.setText(String.format(Locale.getDefault(), "%.0f", forecast.getWind().getSpeed()));
        txtViewWindDeg.setText(getActivity().getString(R.string.temperature,
                String.format(Locale.getDefault(),
                        "%.0f",forecast.getWind().getDeg()), ""));
        linearLayoutMeasures.setVisibility(View.VISIBLE);
    }

    public void setColorScheme(int color) {
        linearLayoutRoot.setBackgroundColor(color);
        linearLayoutMainTemp.setBackgroundColor(color);
        ((MainActivity)getActivity()).setActionBarColor(color);
        Util.setContrastColor(color);
        ((MainActivity)getActivity()).setActionBarTextColor(Util.TEXT_COLOR);
        getTextViewsFromViewGroupAndSetColor(linearLayoutRoot);
    }

    private void getTextViewsFromViewGroupAndSetColor(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if(viewGroup.getChildAt(i) instanceof TextView) {
                ((TextView)viewGroup.getChildAt(i)).setTextColor(Util.TEXT_COLOR);
            } else if(viewGroup.getChildAt(i) instanceof ViewGroup) {
                getTextViewsFromViewGroupAndSetColor((ViewGroup)viewGroup.getChildAt(i));
            }
        }
    }

    public void setForecast5Days(Forecast forecast5Days) {
        ForecastAdapter forecastAdapter = new ForecastAdapter(getContext(), forecast5Days);
        forecastAdapter.notifyDataSetChanged();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listViewForecast5Days.setLayoutManager(layoutManager);
        listViewForecast5Days.setHasFixedSize(true);
        listViewForecast5Days.setAdapter(forecastAdapter);
    }
}
