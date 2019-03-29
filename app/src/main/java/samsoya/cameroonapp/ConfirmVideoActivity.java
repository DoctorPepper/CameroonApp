package samsoya.cameroonapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ConfirmVideoActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_video);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        VideoView videoView = findViewById(R.id.videoView);
        final Uri videoUri = getIntent().getData();
        videoView.setVideoURI(videoUri);
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
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mStorageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference videoStorage = mStorageRef.child("Videos/aaaaaa.mp4");
                            final UploadTask uploadReturn = videoStorage.putFile(videoUri);
                            uploadReturn.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    NotificationsRunner runner = NotificationsRunner.getInstance();
                                    runner.postSuccessNotification(getApplicationContext());
                                    getApplicationContext().getContentResolver().delete(videoUri, null, null);
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                });
                startActivity(new Intent(ConfirmVideoActivity.this, DocumentUploadedConfirmationActivity.class));
            }
        });
    }

}
