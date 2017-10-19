package xero.test.weatherapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import xero.test.weatherapp.view.MainActivity;
import xero.test.weatherapp.view.forecast.ForecastFragment;
import xero.test.weatherapp.view.forecast.ForecastPresenter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForecastInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class, false, false);

    @Before
    public void init(){
        activityTestRule.launchActivity(null);

        String[] latLong = new String[]{"-35.00","138.33"};

        ForecastFragment forecastFragment  = new ForecastFragment();
        ForecastPresenter forecastPresenter = new ForecastPresenter(activityTestRule.getActivity(), forecastFragment, latLong);
        forecastFragment.setPresenter(forecastPresenter);

        activityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFrame, forecastFragment)
                .addToBackStack(null)
                .commit();
    }

    @Test
    public void a_titleBarDisplayed() throws  Exception {
        onView(withId(R.id.titleToolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void b_titleCityName() throws Exception {
        onView(allOf(withId(R.id.titleToolbar), withText("Adelaide")));
    }
}
