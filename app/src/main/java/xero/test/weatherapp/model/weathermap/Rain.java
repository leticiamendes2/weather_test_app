package xero.test.weatherapp.model.weathermap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Letícia on 07/10/2017.
 */
class Rain {

    @SerializedName("3h")
    @Expose
    private Double _3h;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("_3h", _3h).toString();
    }

}
