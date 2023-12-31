package edu.emich.thp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import edu.emich.thp.colorpicker.ColorPicker;
import edu.emich.thp.utils.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColorPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorPickerFragment extends Fragment {

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

        Button next_button = (Button)view.findViewById(R.id.colorPickerDoneButton);
        next_button.setOnClickListener(args -> {
            ProgressBar progress = (ProgressBar)view.findViewById(R.id.progressBar);
            if (progress == null || getActivity() == null) {
                Log.e("ColorPicker", "Progress bar or activity is null! Cannot continue!");
                return;
            }

            progress.setVisibility(View.VISIBLE);

            Runnable runnable = () -> {
                // Introduce artificial sleep to pretend we are loading
                try {
                    var initTime = System.currentTimeMillis();
                    var targetTime = initTime + 2000;
                    var currentTime = initTime;

                    while (currentTime < targetTime) {
                        currentTime = System.currentTimeMillis();
                        long finalCurrentTime = currentTime;

                        getActivity().runOnUiThread(() -> {
                            progress.setProgress((int)((double)targetTime / (double)finalCurrentTime) * 100);
                        });

                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    return;
                }

                // Navigate on UI thread
                getActivity().runOnUiThread(() -> {
                    progress.setVisibility(View.INVISIBLE);
                    NavHostFragment.findNavController(this).navigate(R.id.results);
                });
            };

            Thread t = new Thread(runnable);
            t.start();

        });

        TextView text = view.findViewById(R.id.helpText);
        text.setOnClickListener(s -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Usage Tutorial")
                    .setMessage("Tap on the image any grout you identify. A preview of your selection will be located in the top right. \n\n Once done, select I'M READY.")
                    .show();
        });

        return view;
    }
}