package xero.test.weatherapp.view;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import xero.test.weatherapp.R;
import xero.test.weatherapp.util.LocationUtils;
import xero.test.weatherapp.util.Util;
import xero.test.weatherapp.view.forecast.ForecastFragment;
import xero.test.weatherapp.view.forecast.ForecastPresenter;
import xero.test.weatherapp.view.permissions.PermissionsFragment;
import xero.test.weatherapp.view.permissions.PermissionsPresenter;

import static xero.test.weatherapp.view.permissions.PermissionsPresenter.LOCATION_PERMISSION;

public class MainActivity extends AppCompatActivity implements LocationListener {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.titleToolbar)
    public TextView toolbarTitle;
    private LocationUtils locationUtils;
    private PermissionsPresenter permissionsPresenter;
    private ForecastPresenter forecastPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayShowTitleEnabled(false);

        Util util = new Util();
        if(!util.checkUnitPrefExists(this)) {
            util.SetUnitPref(this, Util.UNIT_METRIC);
        }

        locationUtils = new LocationUtils(this);

        permissionsPresenter = new PermissionsPresenter(this);

        if (permissionsPresenter.hasPermissions()) {
            openForecastFragment();
        } else {
            openPermissionsFragment();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openForecastFragment();
                else openPermissionsFragment();
            }
        }
    }

    public void openForecastFragment() {
        String[] latLong = locationUtils.getLatLongLocation();

        ForecastFragment forecastFragment  = new ForecastFragment();
        forecastPresenter = new ForecastPresenter(this, forecastFragment, latLong);
        forecastFragment.setPresenter(forecastPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFrame, forecastFragment)
                .addToBackStack(null)
                .commit();
    }

    private void openPermissionsFragment() {
        PermissionsFragment permissionsFragment = new PermissionsFragment();
        permissionsFragment.setPresenter(permissionsPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFrame, permissionsFragment)
                .addToBackStack(null)
                .commit();
    }

    public void setActionBarTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void setActionBarColor(int color) {
        toolbar.setBackgroundColor(color);
    }

    public void setActionBarTextColor(int actionBarTextColor) { toolbarTitle.setTextColor(actionBarTextColor); }

    @Override
    public void onLocationChanged(Location location) {
        Calendar tenMinutesAgo = Calendar.getInstance();
        tenMinutesAgo.add(Calendar.MINUTE, -10);

        if(Util.LAST_SEARCH != null && Util.LAST_SEARCH.before(tenMinutesAgo))
            if(!locationUtils.getLatLongLocation()[0].equals("0")) {
                forecastPresenter.loadWeather();
                forecastPresenter.loadForecast5Days();
            }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

}
