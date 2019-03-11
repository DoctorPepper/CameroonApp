package samsoya.cameroonapp;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class DocumentSelectionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.document_selection_fragment, container, false);
        //TODO(team): Set the on-click listeners of all buttons for all of the functionalities
        ImageButton selectPhotoButton = view.findViewById(R.id.select_photo_button);
        ImageButton selectVideoButton = view.findViewById(R.id.select_video_button);
        ImageButton selectAudioButton = view.findViewById(R.id.select_audio_button);
        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TakePhotoActivity.class));
            }
        });

        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed; request the permission
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) {
            ActivityCompat.requestPermissions(getActivity(), permissions.toArray(new String[permissions.size()]), 1);
        }

        selectVideoButton.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent, 1);
                }
            }
        });

        selectAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RecordAudioActivity.class));
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Intent confirmVideo = new Intent(getActivity(), ConfirmVideoActivity.class);
            confirmVideo.setData(data.getData());
            startActivity(confirmVideo);
        }
    }
}
