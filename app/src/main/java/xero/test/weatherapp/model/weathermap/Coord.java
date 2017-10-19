package xero.test.weatherapp.model.weathermap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Letícia on 07/10/2017.
 */

class Coord {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("lat", lat).append("lon", lon).toString();
    }

}
