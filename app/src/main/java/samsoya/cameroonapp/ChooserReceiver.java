package samsoya.cameroonapp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ChooserReceiver extends BroadcastReceiver {

    private SharedPreferencesManager prefsManager;
    private final String LOG_TAG = "RECEIVER_TAG";

    @Override
    public void onReceive(Context context, Intent intent) {

        prefsManager = SharedPreferencesManager.getInstance();
        try {
            ComponentName name = (ComponentName) intent.getExtras().get(Intent.EXTRA_CHOSEN_COMPONENT);
            prefsManager.setAppPreference(name.getPackageName());
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "There was a problem getting the chosen app");
        }
    }
}
