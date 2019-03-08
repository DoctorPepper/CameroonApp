package samsoya.cameroonapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

import java.io.IOException;

public class RecordAudioActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private boolean isPaused = false;
    private long currentTime = 0;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }

    private void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(fileName);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
            recorder.start();
        } else {
            recorder.resume();
        }
        isPaused = false;
    }

    private void pauseRecording() {
        recorder.pause();
        isPaused = true;
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getSupportActionBar().hide(); //hide the title bar

        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/tempaudio.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        setContentView(R.layout.activity_record_audio);

        final Chronometer chronometer = findViewById(R.id.audio_stopwatch);
        final ImageButton recordAudioButton = findViewById(R.id.record_audio_button);
        final ImageButton pauseAudioButton = findViewById(R.id.pause_audio_button);
        final ImageButton stopAudioRecordingButton = findViewById(R.id.stop_audio_recording_button);


        recordAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime() + currentTime);
                chronometer.start();
                startRecording();
                disableButton(recordAudioButton);
                enableButton(stopAudioRecordingButton);
                enableButton(pauseAudioButton);
                pauseAudioButton.setImageResource(R.drawable.ic_pause_black_24dp);
            }
        });

        disableButton(pauseAudioButton);
        pauseAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPaused) {
                    chronometer.stop();
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    currentTime = 0;
                    stopRecording();
                    pauseAudioButton.setImageResource(R.drawable.ic_pause_black_24dp);
                    isPaused = false;
                    disableButton(pauseAudioButton);
                    disableButton(stopAudioRecordingButton);
                } else {
                    chronometer.stop();
                    currentTime = chronometer.getBase() - SystemClock.elapsedRealtime();
                    pauseRecording();
                    enableButton(recordAudioButton);
                    pauseAudioButton.setImageResource(R.drawable.ic_refresh_black_24dp);
                    isPaused = true;
                }
            }
        });

        disableButton(stopAudioRecordingButton);
        stopAudioRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                stopRecording();
                Intent intent = new Intent(RecordAudioActivity.this, ConfirmAudioActivity.class);
                intent.putExtra("filename", fileName);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void disableButton(ImageButton button) {
        button.setEnabled(false);
        button.setAlpha(.5f);
    }

    private void enableButton(ImageButton button) {
        button.setEnabled(true);
        button.setAlpha(1f);
    }
}