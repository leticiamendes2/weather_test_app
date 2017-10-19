package xero.test.weatherapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import xero.test.weatherapp.util.LocationUtils;
import xero.test.weatherapp.view.MainActivity;
import xero.test.weatherapp.view.permissions.PermissionsFragment;
import xero.test.weatherapp.view.permissions.PermissionsPresenter;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissionInstrumentedTest {

    private UiDevice device;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule(MainActivity.class, false, false);

    @Before
    public void setUp() {
        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Before
    public void init(){
        activityTestRule.launchActivity(null);

        PermissionsFragment permissionsFragment  = new PermissionsFragment();
        PermissionsPresenter permissionsPresenter = new PermissionsPresenter(activityTestRule.getActivity());
        permissionsFragment.setPresenter(permissionsPresenter);

        activityTestRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentFrame, permissionsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Test
    public void a_buttonDisplayed() throws  Exception {
        onView(withId(R.id.btnGivePermission)).check(matches(isDisplayed()));
    }

    @Test
    public void b_shouldDisplayPermissionRequestDialogAtClickButton() throws Exception {
        onView(withId(R.id.btnGivePermission)).perform(click());

        assertViewWithTextIsVisible("ALLOW");
        assertViewWithTextIsVisible("DENY");

        answerPermission(device,"DENY");
    }

    @Test
    public void c_shouldAccessLocationIfPermissionWasGranted() throws Exception {
        onView(withId(R.id.btnGivePermission)).perform(click());

        answerPermission(device,"ALLOW");

        LocationUtils locationUtils = new LocationUtils(activityTestRule.getActivity());
        Assert.assertNotEquals(locationUtils.getLatLongLocation(), new String[]{"0","0"});
    }

    public void assertViewWithTextIsVisible(String text) throws Exception {
        UiObject allowButton = device.findObject(new UiSelector().text(text));
        if (!allowButton.exists()) {
            throw new AssertionError("View with text <" + text + "> not found!");
        }
    }

    public static void answerPermission(UiDevice device, String text) throws UiObjectNotFoundException {
        UiObject denyButton = device.findObject(new UiSelector().text(text));
        denyButton.click();
    }
}
