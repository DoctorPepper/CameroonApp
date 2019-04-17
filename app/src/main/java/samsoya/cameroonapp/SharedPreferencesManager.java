package samsoya.cameroonapp;

import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static SharedPreferencesManager manager;
    private static SharedPreferences prefs;
    protected static final String SHARED_PREFS_STRING = "CAMEROON_APP_PREFS";
    private static final String SHARED_PREFS_APP_KEY = "CAMEROON_APP_PREFS_APP_KEY";
    private static final String SHARED_PREFS_PINCODE_KEY = "CAMEROON_APP_PREFS_PIN_KEY";
    private static final String SHARED_PREFS_COUNTY_KEY = "CAMEROON_APP_PREFS_COUNTY_KEY";

    public static SharedPreferencesManager getInstance() {
        if (manager == null) {
            manager = new SharedPreferencesManager();
        }

        return manager;
    }

    protected void setSharedPrefs(SharedPreferences prefs) {
        SharedPreferencesManager.prefs = prefs;
    }

    public SharedPreferences getSharedPrefs() {
        return prefs;
    }

    private void onCreate() {
        manager = this;
    }

    protected void setAppPreference(String packageName) {
        prefs.edit().putString(SHARED_PREFS_APP_KEY, packageName).apply();
    }

    protected String getAppPreference() {
        return prefs.getString(SHARED_PREFS_APP_KEY, null);
    }

    protected void setPin(String pin) {
        prefs.edit().putString(SHARED_PREFS_PINCODE_KEY, pin).apply();
    }

    protected String getPin() {
        return prefs.getString(SHARED_PREFS_PINCODE_KEY, null);
    }

    protected void setCountyPreference(String countyPreference) {
        prefs.edit().putString(SHARED_PREFS_COUNTY_KEY, countyPreference).apply();
    }

    protected String getCountyPreference() {
        return prefs.getString(SHARED_PREFS_COUNTY_KEY, null);
    }

}
