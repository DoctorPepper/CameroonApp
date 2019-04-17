package samsoya.cameroonapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {
    private String currentlySelected;
    private SharedPreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Spinner spinner = findViewById(R.id.spinner_select_county);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.counties_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        preferencesManager = SharedPreferencesManager.getInstance();
        currentlySelected = preferencesManager.getCountyPreference();

        spinner.setSelection(adapter.getPosition(currentlySelected));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                currentlySelected = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.settings_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferencesManager.setCountyPreference(currentlySelected);
                onBackPressed();
            }
        });

        findViewById(R.id.settings_change_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(Intent.ACTION_MAIN);
                Intent receiver = new Intent(SettingsActivity.this, ChooserReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 0, receiver, PendingIntent.FLAG_UPDATE_CURRENT);
                Intent chooser = Intent.createChooser(startIntent, "Pick", pendingIntent.getIntentSender());
                startActivity(chooser);
            }
        });

    }
}
