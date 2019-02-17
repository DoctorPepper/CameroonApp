package samsoya.cameroonapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.VideoView;
import java.io.File;

public class ConfirmVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_video);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(getIntent().getData());
        videoView.start();

        findViewById(R.id.retake_photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO(team): Go back to photo and delete video from files
            }
        });

        findViewById(R.id.confirm_photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO(team): Send the photo to the server
                if (getIntent().getData().getPath() != null) {
                    File videoFile = new File(getIntent().getData().getPath());
                    videoFile.delete();
                }
                startActivity(new Intent(ConfirmVideoActivity.this, DocumentUploadedConfirmationActivity.class));

            }
        });
    }

}
