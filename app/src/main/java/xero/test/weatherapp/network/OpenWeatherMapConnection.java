package xero.test.weatherapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xero.test.weatherapp.model.weathermap.Forecast;

/**
 * Created by Letícia on 06/10/2017.
 */

public class OpenWeatherMapConnection {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String API_ID = "66fa319740c73c67595927d3e782a5dc";
    private final OpenWeatherMapAPI openWeatherMapAPI;

    public OpenWeatherMapConnection() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        openWeatherMapAPI = retrofit.create(OpenWeatherMapAPI.class);
    }

    public void getForecastByLatLng(String lat, String lng, String metric, Callback<Forecast> callback) {
        Call<Forecast> call = openWeatherMapAPI.getForecastByLatLng(lat, lng, metric, API_ID);
        call.enqueue(callback);
    }

    public void getWeatherByLatLng(String lat, String lng, String metric, Callback<Forecast> callback) {
        Call<Forecast> call = openWeatherMapAPI.getWeatherByLatLng(lat, lng, metric, API_ID);
        call.enqueue(callback);
    }
}
