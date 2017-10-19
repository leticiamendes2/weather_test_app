package xero.test.weatherapp.model.weathermap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Letícia on 07/10/2017.
 */

public class Forecast {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Double message;
    @SerializedName("cnt")
    @Expose
    private Long cnt;
    @SerializedName("list")
    @Expose
    private List<ForecastItem> forecastItem = null;
    @SerializedName("city")
    @Expose
    private City city;

    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private Long dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    private List<ForecastItem> forecastUniqueItens = null;

    private List<ForecastItem> getForecastItem() {
        return forecastItem;
    }

    private void setListUniqueItens() {
        ForecastItem previous;
        ForecastItem item;
        List<ForecastItem> uniqueForecastItem = new ArrayList<>();
        for (int i = 1; i < getForecastItem().size(); i++) {
            previous = getForecastItem().get(i-1);
            item = getForecastItem().get(i);

            if (previous.getDayOfWeek().equals(item.getDayOfWeek()))
                continue;

            uniqueForecastItem.add(previous);
        }

        uniqueForecastItem.add(getForecastItem().get(getForecastItem().size()-1));
        forecastUniqueItens = uniqueForecastItem;
    }

    public List<ForecastItem> getListWithUniqueItens() {
        if(forecastUniqueItens == null) setListUniqueItens();
        return forecastUniqueItens;
    }

    public Double getMinTempByDay(String dayOfWeek) {
        Double minTemp = 0.0;

        List<ForecastItem> forecastItemsSameDay = new ArrayList<>();
        for(ForecastItem item : getForecastItem()) {
            if(item.getDayOfWeek().equals(dayOfWeek)) {
                forecastItemsSameDay.add(item);
            }
            else {
                if(forecastItemsSameDay.size() > 0) {
                    Collections.sort(forecastItemsSameDay, new ForecastItem.ForecastItemMinTempComparator());
                    return forecastItemsSameDay.get(0).getMain().getTempMin();
                } else {
                    minTemp = item.getMain().getTempMin();
                }
            }
        }

        if(forecastItemsSameDay.size() > 0)
            return forecastItemsSameDay.get(0).getMain().getTempMin();

        return minTemp;
    }

    public Double getMaxTempByDay(String dayOfWeek) {
        Double maxTemp = 0.0;

        List<ForecastItem> forecastItemsSameDay = new ArrayList<>();
        for(ForecastItem item : getForecastItem()) {
            if(item.getDayOfWeek().equals(dayOfWeek)) {
                forecastItemsSameDay.add(item);
            }
            else {
                if(forecastItemsSameDay.size() > 0) {
                    Collections.sort(forecastItemsSameDay, new ForecastItem.ForecastItemMaxTempComparator());
                    return forecastItemsSameDay.get(forecastItemsSameDay.size()-1).getMain().getTempMax();
                } else {
                    maxTemp = item.getMain().getTempMax();
                }
            }
        }

        if(forecastItemsSameDay.size() > 0)
            return forecastItemsSameDay.get(forecastItemsSameDay.size()-1).getMain().getTempMax();

        return maxTemp;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    private void setDt(Long dt) {
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cod", cod)
                .append("message", message)
                .append("cnt", cnt)
                .append("forecastItem", forecastItem.toString())
                .append("city", city.toString())
                .append("coord", coord.toString())
                .append("weather", weather.toString())
                .append("base", base)
                .append("main", main.toString())
                .append("visibility", visibility)
                .append("wind", wind.toString())
                .append("clouds", clouds.toString())
                .append("dt", dt)
                .append("sys", sys.toString())
                .append("id", id)
                .append("name", name)
                .toString();
    }

}
