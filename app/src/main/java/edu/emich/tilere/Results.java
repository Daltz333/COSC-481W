package edu.emich.tilere;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import edu.emich.tilere.api.DbClient;
import edu.emich.tilere.models.GroutItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Results#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Results extends Fragment {
    private ArrayList<GroutItem> resultsList;
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Results() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Results.
     */
    // TODO: Rename and change types and number of parameters
    public static Results newInstance(String param1, String param2) {
        Results fragment = new Results();
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
    public void onViewCreated(@androidx.annotation.NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        resultsList = new ArrayList<>();
        setResultInfo();
        setAdapter();
    }


    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(resultsList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private void setResultInfo() {
        DbClient data = DbClient.getInstance();
        ArrayList<GroutItem> items = data.getGrout();
        for(GroutItem item : items) {
            resultsList.add(item);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        try {
            Button settings_button = (Button) view.findViewById(R.id.results_settings_button);
            Button menu_button = (Button) view.findViewById(R.id.results_menu_button);
            settings_button.setOnClickListener(args -> {
                NavHostFragment.findNavController(this).navigate(R.id.settings);
            });
            menu_button.setOnClickListener(args -> {
                NavHostFragment.findNavController(this).navigate(R.id.startPage);
            });
        } catch (Exception ignored) {}
        return view;
    }
}