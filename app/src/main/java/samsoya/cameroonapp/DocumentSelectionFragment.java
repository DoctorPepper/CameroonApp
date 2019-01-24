package samsoya.cameroonapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.venmo.android.pin.PinFragment;
import com.venmo.android.pin.util.PinHelper;

public class DocumentSelectionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.document_selection_fragment, container, false);
        //TODO(team): Set the on-click listeners of all buttons for all of the functionalities
        ImageButton selectPhotoButton = view.findViewById(R.id.select_photo_button);
        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TakePhotoActivity.class));
            }
        });
        return view;
    }
}
