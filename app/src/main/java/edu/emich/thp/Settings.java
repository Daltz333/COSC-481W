package edu.emich.thp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import edu.emich.thp.GlobalSettings.GlobalSettings;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        try {
            // Get switches and number input component
            Switch mapei = (Switch) view.findViewById(R.id.mapei_switch);
            Switch tec = (Switch) view.findViewById(R.id.tec_switch);
            EditText numInput = (EditText) view.findViewById(R.id.input_number);

            // Get access to global settings
            GlobalSettings settings = GlobalSettings.getInstance();

            // Check the last state of the switch and update switch state accordingly
            mapei.setChecked(settings.includeMapei());
            tec.setChecked(settings.includeTec());

            numInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                    // Required for TextWatcher
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    // Required for TextWatcher
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String enteredText = editable.toString();
                    try {
                        int enteredNumber = Integer.parseInt(enteredText);
                        if (enteredNumber < 0) {
                            throw new NumberFormatException("Input must not be negative");
                        }
                        else {
                            settings.setNumSearchResults(enteredNumber);
                        }

                    } catch (NumberFormatException e) {
                        // Handle the case where the input is not a valid integer
                    }
                }
            });

            // Create listeners
            mapei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // Switch is ON
                        settings.setIncludeMapei(true);
                    } else {
                        // Switch is OFF
                        settings.setIncludeMapei(false);
                    }
                }
            });
            tec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // Switch is ON
                        settings.setIncludeTec(true);
                    } else {
                        // Switch is OFF
                        settings.setIncludeTec(false);
                    }
                }
            });

        } catch (Exception ignored) {}
        return view;
    }
}