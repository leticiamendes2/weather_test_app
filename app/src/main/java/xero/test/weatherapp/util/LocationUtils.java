package xero.test.weatherapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;

import java.util.Calendar;

import xero.test.weatherapp.R;

/**
 * Created by Letícia on 12/10/2017.
 */

public class LocationUtils {

    private final Activity activity;
    private boolean isGPS;
    private boolean isNetwork;
    private LocationManager locationManager;

    public LocationUtils(Activity activity) {
        this.activity = activity;
    }

    private boolean hasGPSOrNetworkConnection() {
        locationManager = (LocationManager) activity.getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager != null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return !(!isGPS && !isNetwork);
    }

    private void turnLocationServiceOn() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle(R.string.gps_not_enabled);
        alertDialog.setMessage(R.string.ask_turn_gps_on);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public String[] getLatLongLocation() {
        String[] latLng = new String[]{"0","0"};

        try {
            long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
            long MIN_TIME_BW_UPDATES = 1000 * 60;
            Location loc;

            if (!hasGPSOrNetworkConnection()) {
                turnLocationServiceOn();
            }

            if (isGPS) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) activity);

                if (locationManager != null) {
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (loc != null) {
                        latLng[0] = Double.toString(loc.getLatitude());
                        latLng[1] = Double.toString(loc.getLongitude());
                        Util.LAST_SEARCH = Calendar.getInstance();
                        return latLng;
                    }
                }
            } else if (isNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) activity);

                if (locationManager != null) {
                    loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (loc != null) {
                        latLng[0] = Double.toString(loc.getLatitude());
                        latLng[1] = Double.toString(loc.getLongitude());
                        Util.LAST_SEARCH = Calendar.getInstance();
                        return latLng;
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return latLng;
    }
}