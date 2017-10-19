package xero.test.weatherapp.view.forecast;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xero.test.weatherapp.R;
import xero.test.weatherapp.model.weathermap.Forecast;
import xero.test.weatherapp.model.weathermap.Weather;
import xero.test.weatherapp.network.OpenWeatherMapConnection;
import xero.test.weatherapp.util.Util;

/**
 * Created by Letícia on 09/10/2017.
 */

public class ForecastPresenter {

    private final Activity activity;
    private String metric;
    private OpenWeatherMapConnection openWeatherMapConnection;

    @NonNull
    private final ForecastFragment forecastView;
    private final String[] latLongLocation;

    public ForecastPresenter(@NonNull Activity activity, @NonNull ForecastFragment forecastView, String[] latLongLocation) {
        this.activity = activity;
        this.forecastView = forecastView;
        this.latLongLocation = latLongLocation;
    }

    public void start() {
        openWeatherMapConnection = new OpenWeatherMapConnection();

        metric = Util.GET_UNIT_PREF_FOR_SERVICE(activity);

        if(!latLongLocation[0].equals("0")) {
            loadWeather();
            loadForecast5Days();
        }
    }

    public void loadForecast5Days() {
        openWeatherMapConnection.getForecastByLatLng(latLongLocation[0], latLongLocation[1], metric, new Callback<Forecast>() {
            @Override
            public void onResponse(@NonNull Call<Forecast> call, @NonNull Response<Forecast> response) {
                if(response.isSuccessful()) {
                    Forecast forecast = response.body();
                    forecastView.setForecast5Days(forecast);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void loadWeather() {
        openWeatherMapConnection.getWeatherByLatLng(latLongLocation[0], latLongLocation[1], metric, new Callback<Forecast>() {
            @Override
            public void onResponse(@NonNull Call<Forecast> call, @NonNull Response<Forecast> response) {
                if(response.isSuccessful()) {
                    Forecast forecast = response.body();
                    forecastView.uploadCityName(forecast != null ? forecast.getName() : null);

                    int backgroundColor = activity.getResources().getColor(R.color.colorPrimary);

                    for (Weather w : forecast != null ? forecast.getWeather() : null) {
                        long id = w.getId();
                        String icon = w.getIcon();
                        backgroundColor = getBackgroundColorByWeatherID(id, icon);
                    }

                    forecastView.setColorScheme(backgroundColor);
                    forecastView.setWeather(forecast);
                } else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Forecast> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void changeUnit() {
        metric = new Util().ChangeUnitPref(activity);
        loadWeather();
        loadForecast5Days();
    }

    private int getBackgroundColorByWeatherID(long id, String icon) {
        Resources resources = activity.getResources();
        int color = resources.getColor(R.color.colorPrimary);

        if ((id >= 200 && id < 300) || id == 903)
            color =  resources.getColor(R.color.colorDrizzle);
        else if ((id >= 300 && id < 400) || (id >= 900 && id <= 902) || (id >= 960 && id <= 962))
            color =  resources.getColor(R.color.colorThunderstorm);
        else if ((id >= 500 && id < 600) || id == 905 || id == 906)
            color =  resources.getColor(R.color.colorRain);
        else if (id >= 600 && id < 700)
            color =  resources.getColor(R.color.colorSnow);
        else if (id >= 700 && id < 800)
            color =  resources.getColor(R.color.colorFog);
        else if (id == 800) {
            if(icon.equals("01d"))
                color =  resources.getColor(R.color.colorClearSkyDay);
            else
                color =  resources.getColor(R.color.colorClearSkyNight);
        } else if (id >= 801 && id < 900) {
            color = resources.getColor(R.color.colorCloudy);
        } else if (id == 904) {
            color =  resources.getColor(R.color.colorHot);
        } else if (id >= 951 && id <= 956) {
            color =  resources.getColor(R.color.colorBreeze);
        } else if (id >= 957 && id <= 959)
            color =  resources.getColor(R.color.colorGale);

        Util.setContrastColor(color);

        return color;
    }

}
