package xero.test.weatherapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.ColorInt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import xero.test.weatherapp.R;

/**
 * Created by Letícia on 10/10/2017.
 */

public class Util {

    public final static String URL_ICON = "http://openweathermap.org/img/w/%ICON%.png";
    public final static String UNIT_METRIC = "Metric";
    private final static String UNIT_IMPERIAL = "Imperial";
    public static Calendar LAST_SEARCH;
    @ColorInt
    public static int TEXT_COLOR = Color.BLACK;

    public static String GET_FORMATTED_DATES(Long date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        Calendar dtCalendar = Calendar.getInstance();
        Date d = new Date(date*1000L);
        dtCalendar.setTime(d);

        return format.format(dtCalendar.getTime());
    }

    public static String GET_DAY_OF_WEEK(Long date) {
        Calendar dtCalendar = Calendar.getInstance();
        Date d = new Date(date*1000L);
        dtCalendar.setTime(d);

        int dayOfWeek = dtCalendar.get(Calendar.DAY_OF_WEEK);

        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        return days[dayOfWeek-1];
    }

    public static String GET_UNIT_PREF_FOR_SERVICE(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(activity.getString(R.string.unit), activity.getString(R.string.unit_metric));
    }

    public static String GET_UNIT_PREF_FOR_VIEWING(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String prefUnit = sharedPref.getString(activity.getString(R.string.unit), activity.getString(R.string.unit_metric));
        return prefUnit.equals(UNIT_METRIC) ? activity.getString(R.string.unit_metric) : activity.getString(R.string.unit_imperial);
    }

    public boolean checkUnitPrefExists(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.contains(activity.getString(R.string.unit));
    }

    public void SetUnitPref(Activity activity, String unit) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.unit), unit);
        editor.apply();
    }

    public String ChangeUnitPref(Activity activity) {
        String currentUnit = GET_UNIT_PREF_FOR_SERVICE(activity);
        if(currentUnit.equals(UNIT_METRIC)) {
            SetUnitPref(activity, UNIT_IMPERIAL);
            return UNIT_IMPERIAL;
        } else {
            SetUnitPref(activity, UNIT_METRIC);
            return UNIT_METRIC;
        }
    }

    public static void setContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        TEXT_COLOR = a < 0.5 ? Color.DKGRAY : Color.WHITE;
    }
}
