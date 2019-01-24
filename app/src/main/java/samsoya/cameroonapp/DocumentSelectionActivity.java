package samsoya.cameroonapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.venmo.android.pin.PinFragment;
import com.venmo.android.pin.PinFragmentConfiguration;
import com.venmo.android.pin.PinSaver;
import com.venmo.android.pin.Validator;

public class DocumentSelectionActivity extends AppCompatActivity implements PinFragment.Listener {

    private static final String SHARED_PREFS_STRING = "CAMEROON_APP_PREFS";
    private static final String SHARED_PREFS_PINCODE_KEY = "CAMEROON_APP_PREFS_PIN_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_document_selection);
        getSupportActionBar().hide(); //hide the title bar

        // This block of code exists because it's the only way to edit the fail-case of the pin
        // (i.e. when the pin fails, we want it to go to the game).
        PinFragmentConfiguration config =
                new PinFragmentConfiguration(this)
                        .pinSaver(new PinSaver() {
                            @Override
                            public void save(String pin) {
                                // When the pin is saved, put it into the secure
                                // SharedPreferences for the app.
                                SharedPreferences prefs
                                        = getSharedPreferences(SHARED_PREFS_STRING, 0);
                                prefs.edit().putString(SHARED_PREFS_PINCODE_KEY, pin).apply();
                            }
                        }).validator(new Validator() {
                    public boolean isValid(String submission) {
                        // Check to see if the stored pin is equal to the entered pin.
                        String savedPin = getSharedPreferences(SHARED_PREFS_STRING, 0)
                                .getString(SHARED_PREFS_PINCODE_KEY, null);
                        if (savedPin == null || !savedPin.equals(submission)) {
                            // If there is no stored pin, or if the stored pin does not match the
                            // entered pin, take the user to a blank activity.
                            //TODO(team): Either make the blank activity a game, or link directly to a game.
                            Intent intent = new Intent(DocumentSelectionActivity.this, GameActivity.class);
                            startActivity(intent);
                            return false;
                        }
                        return true;
                    }
                });

        // Determine which fragment (Creation or verification) needs to be shown
        Fragment toShow = (getSharedPreferences(SHARED_PREFS_STRING, 0)
                .getString(SHARED_PREFS_PINCODE_KEY, null) != null) ?
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
}
