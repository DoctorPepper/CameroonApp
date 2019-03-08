package samsoya.cameroonapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.IOException;

public class ConfirmAudioActivity extends AppCompatActivity {

    private MediaPlayer player = null;
    private String audioFile = null;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioFile = getIntent().getStringExtra("filename");
        setContentView(R.layout.activity_confirm_audio);

        final ImageButton playButton = findViewById(R.id.play_audio_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    stopPlaying();
                    playButton.setImageResource(R.drawable.ic_play_arrow_black_36dp);
                    isPlaying = false;
                } else {
                    startPlaying();
                    playButton.setImageResource(R.drawable.ic_stop_black_36dp);
                    isPlaying = true;
                }
            }
        });

        findViewById(R.id.retake_photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                //TODO(team): Go back to photo
            }
        });

        findViewById(R.id.confirm_photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                // This is how we're going to make sure that it can upload in the background
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO: Upload audio to Firestore.
                    }
                });
                startActivity(new Intent(ConfirmAudioActivity.this, DocumentUploadedConfirmationActivity.class));
            }
        });
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(audioFile);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }
}