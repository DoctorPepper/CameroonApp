package samsoya.cameroonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfirmPhotoActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_confirm_photo);

        final byte[] photo = getIntent().getByteArrayExtra(getString(R.string.photo_extra));
        final Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        ((ImageView) findViewById(R.id.preview_photo_view)).setImageBitmap(photoBitmap);

        findViewById(R.id.retake_photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO(team): Go back to photo
            }
        });

        findViewById(R.id.confirm_photo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is how we're going to make sure that it can upload in the background
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        mStorageRef = FirebaseStorage.getInstance().getReference();
                        final StorageReference photosStorage = mStorageRef.child("Photos/aaaaaa.png");
                        photosStorage.putBytes(photo);
                    }
                });
                startActivity(new Intent(ConfirmPhotoActivity.this, DocumentUploadedConfirmationActivity.class));
            }
        });
    }
}
