package edu.emich.tilere;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import edu.emich.tilere.colorpicker.ColorPicker;
import edu.emich.tilere.utils.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColorPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorPickerFragment extends Fragment {

    private Intent mPickImageIntent;
    private ActivityResultLauncher<Intent> mPickImageLauncher;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColorPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColorPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorPickerFragment newInstance(String param1, String param2) {
        ColorPickerFragment fragment = new ColorPickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);
        try {
            Button back_button = (Button) view.findViewById(R.id.back_button);
            back_button.setOnClickListener(args -> {
                NavHostFragment.findNavController(this).navigate(R.id.startPage);
            });
        } catch (Exception ignored) {}

        Button importColorPicker = (Button)view.findViewById(R.id.importColorPickerImageButton);
        importColorPicker.setOnClickListener(this::startImportImageIntent);

        setupIntents(view);
        return view;
    }

    private void setupIntents(View view) {
        mPickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mPickImageIntent.setType("image/*");

        mPickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            // Handle activity result
            var data = result.getData();
            if (data != null) {
                Uri imageUri = data.getData();

                ColorPicker colorPicker = (ColorPicker)view.findViewById(R.id.colorPicker);

                // handle event colorpicker is null
                // this should only ever happen if developer is bad
                if (colorPicker == null) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Failed to retrieve color picker instance")
                            .setMessage("ColorPicker instance or view was null. Please contact the application developers.")
                            .show();

                    return;
                }

                try {
                    colorPicker.setImage(ImageUtils.getBitmapFromUri(view.getContext(), imageUri));
                } catch (IOException ex) {
                    String err = "Failed to import image from gallery with exception: " + ex.getMessage();
                    Log.i(this.getClass().getName(), err);

                    new AlertDialog.Builder(getContext())
                            .setTitle("An error has occurred")
                            .setMessage(err)
                            .show();
                }
            }
        });
    }

    public void startImportImageIntent(View sender) {
        mPickImageLauncher.launch(mPickImageIntent);
    }
}