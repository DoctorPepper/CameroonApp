package samsoya.cameroonapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.venmo.android.pin.PinFragment;
import com.venmo.android.pin.PinFragmentConfiguration;
import com.venmo.android.pin.PinSaver;
import com.venmo.android.pin.Validator;

public class DocumentSelectionActivity extends AppCompatActivity implements PinFragment.Listener {

    private SharedPreferencesManager preferencesManager;
    private final String TAG = "tagtagtag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_document_selection);

        // This block of code exists because it's the only way to edit the fail-case of the pin
        // (i.e. when the pin fails, we want it to go to the game).
        SharedPreferences prefs = getSharedPreferences(SharedPreferencesManager.SHARED_PREFS_STRING, 0);
        preferencesManager = SharedPreferencesManager.getInstance();
        preferencesManager.setSharedPrefs(prefs);
        PinFragmentConfiguration config =
                new PinFragmentConfiguration(this)
                        .pinSaver(new PinSaver() {
                            @Override
                            public void save(String pin) {
                                // When the pin is saved, put it into the secure
                                // SharedPreferences for the app.
                                preferencesManager.setPin(pin);
                            }
                        }).validator(new Validator() {
                    public boolean isValid(String submission) {
                        // Check to see if the stored pin is equal to the entered pin.

                        String savedPin = preferencesManager.getPin();
                        if (savedPin == null || !savedPin.equals(submission)) {
                            // If there is no stored pin, or if the stored pin does not match the
                            // entered pin, take the user to their stored deception app.
                            String packageName = preferencesManager.getAppPreference();
                            Intent deceptionIntent;
                            if (packageName == null) {
                                deceptionIntent = new Intent(DocumentSelectionActivity.this, GameActivity.class);
                            } else {
                                PackageManager pm = getPackageManager();
                                deceptionIntent = pm.getLaunchIntentForPackage(packageName);
                            }
                            startActivity(deceptionIntent);
                            return false;
                        }
                        return true;
                    }
                });

        if (preferencesManager.getCountyPreference() == null) {
            preferencesManager.setCountyPreference("Boyo");
        }

        // Determine which fragment (Creation or verification) needs to be shown
        Fragment toShow = (preferencesManager.getPin() != null) ?
                PinFragment.newInstanceForVerification(config) :
                PinFragment.newInstanceForCreation(config);

        // put the fragment on the screen.
        getFragmentManager().beginTransaction()
                .replace(R.id.container, toShow)
                .commit();
    }

    @Override
    public void onValidated() {
        showDocumentSelection();
    }

    @Override
    public void onPinCreated() {
        showDocumentSelection();
    }

    private void showDocumentSelection() {
        Fragment toShow = new DocumentSelectionFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, toShow)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }
}
