package xero.test.weatherapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xero.test.weatherapp.model.weathermap.Forecast;

/**
 * Created by Letícia on 06/10/2017.
 */

interface OpenWeatherMapAPI {

    @GET("forecast")
    Call<Forecast> getForecastByLatLng(@Query("lat") String lat, @Query("lon") String lng, @Query("units") String unit, @Query("appid") String appid);

    @GET("weather")
    Call<Forecast> getWeatherByLatLng(@Query("lat") String lat, @Query("lon") String lng, @Query("units") String unit, @Query("appid") String appid);

}
