package samsoya.cameroonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class ConfirmPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_photo);

        byte[] photo = getIntent().getByteArrayExtra(getString(R.string.photo_extra));
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
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
                //TODO(team): Send the photo to the server
                startActivity(new Intent(ConfirmPhotoActivity.this, DocumentUploadedConfirmationActivity.class));

            }
        });
    }
}
