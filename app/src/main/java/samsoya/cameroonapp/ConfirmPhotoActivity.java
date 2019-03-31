package samsoya.cameroonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ConfirmPhotoActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_confirm_photo);

        final byte[] photo = PicturePreviewHolder.getInstance().getCapturedPhotoData();
        final Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        ImageView preview = findViewById(R.id.preview_photo_view);
        final Bitmap rotatedBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, photoBitmap.getWidth(), photoBitmap.getHeight(), matrix, true);
        ((ImageView) findViewById(R.id.preview_photo_view)).setImageBitmap(rotatedBitmap);

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
                        UploadTask uploadReturn = photosStorage.putBytes(photo);
                        uploadReturn.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                NotificationsRunner runner = NotificationsRunner.getInstance();
                                runner.postSuccessNotification(getApplicationContext());
                            }
                        });
                    }
                });
                startActivity(new Intent(ConfirmPhotoActivity.this, DocumentUploadedConfirmationActivity.class));
            }
        });
    }
}
