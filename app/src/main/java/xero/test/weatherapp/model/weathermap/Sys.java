package xero.test.weatherapp.model.weathermap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Letícia on 07/10/2017.
 */

class Sys {


    @SerializedName("pod")
    @Expose
    private String pod;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private Long sunrise;
    @SerializedName("sunset")
    @Expose
    private Long sunset;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("pod", pod).append("country", country).append("sunrise", sunrise).append("sunset", sunset).toString();
    }

}
