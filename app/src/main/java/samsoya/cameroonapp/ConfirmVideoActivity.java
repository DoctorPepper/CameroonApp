package samsoya.cameroonapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
    private Uri videoUri;
    private SharedPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_video);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        VideoView videoView = findViewById(R.id.videoView);
        videoUri = getIntent().getData();
        videoView.setVideoURI(videoUri);
        videoView.start();

        if (videoUri == null) {
            onBackPressed();
        }

        findViewById(R.id.retake_photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoUri != null) {
                    getApplicationContext().getContentResolver().delete(videoUri, null, null);
                }
                onBackPressed();
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, 1);
                }
            }
        });

        findViewById(R.id.confirm_photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (videoUri != null) {
                                mStorageRef = FirebaseStorage.getInstance().getReference();
                                manager = SharedPreferencesManager.getInstance();
                                String storageString = "Videos/" + manager.getCountyPreference() + "/new.mp4";
                                StorageReference videoStorage = mStorageRef.child(storageString);
                                final UploadTask uploadReturn = videoStorage.putFile(videoUri);
                                uploadReturn.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        NotificationsRunner runner = NotificationsRunner.getInstance();
                                        runner.postSuccessNotification(getApplicationContext());
                                        getApplicationContext().getContentResolver().delete(videoUri, null, null);
                                    }
                                });
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                startActivity(new Intent(ConfirmVideoActivity.this, DocumentUploadedConfirmationActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoUri != null) {
            getApplicationContext().getContentResolver().delete(videoUri, null, null);
        }
    }
}
