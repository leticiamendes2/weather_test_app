package xero.test.weatherapp.model.weathermap;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Comparator;
import java.util.List;

import xero.test.weatherapp.util.Util;

/**
 * Created by Letícia on 07/10/2017.
 */

public class ForecastItem implements Comparable<ForecastItem> {

    @SerializedName("dt")
    @Expose
    private Long dt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("rain")
    @Expose
    private Rain rain;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public String getDayOfWeek() {
        return Util.GET_DAY_OF_WEEK(dt);
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dt", dt)
                .append("main", main.toString())
                .append("weather", weather.toString())
                .append("clouds", clouds.toString())
                .append("wind", wind)
                .append("rain", rain)
                .append("sys", sys)
                .append("dtTxt", dtTxt).toString();
    }

    @Override
    public int compareTo(@NonNull ForecastItem item) {
        if(item.getDayOfWeek().equals(this.getDayOfWeek())) {
            return 0;
        }

        return 1;
    }

    public static class ForecastItemMinTempComparator implements Comparator<ForecastItem> {
        @Override
        public int compare(ForecastItem f1, ForecastItem f2) {
            int t = f1.getMain().getTempMin().compareTo(f2.getMain().getTempMin());
            return (t != 0) ? t : f2.getMain().getTempMin().compareTo(f1.getMain().getTempMin());
        }
    }

    public static class ForecastItemMaxTempComparator implements Comparator<ForecastItem> {
        @Override
        public int compare(ForecastItem f1, ForecastItem f2) {
            int t = f1.getMain().getTempMax().compareTo(f2.getMain().getTempMax());
            return (t != 0) ? t : f2.getMain().getTempMax().compareTo(f1.getMain().getTempMax());
        }
    }

}


