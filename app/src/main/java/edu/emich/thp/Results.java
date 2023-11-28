package edu.emich.thp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import edu.emich.thp.GlobalSettings.GlobalSettings;
import edu.emich.thp.api.DbClient;
import edu.emich.thp.models.GroutItem;

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
        ArrayList<GroutItem> initialItems = data.getGrout();
        ArrayList<GroutItem> items = new ArrayList<>();

        GlobalSettings settings = GlobalSettings.getInstance();

        // Filter the results by brand
        for(GroutItem initialItem : initialItems) {
            if(initialItem.getBrandName().equals("Mapei") && settings.includeMapei() == true) {
                items.add(initialItem);
            }
            else if(initialItem.getBrandName().equals("Tec") && settings.includeTec() == true) {
                items.add(initialItem);
            }
        }

        // Number of results to be displayed
        int numResults = settings.getNumSearchResults();


        String targetHex =  Integer.toHexString(settings.getHexColor());
        targetHex = targetHex.substring(2);
        if(targetHex.length() < 6) {
            targetHex = "0" + targetHex;
        }
        try {

        }
        catch (Exception e) {
            Log.e("invalidHexCode", "The following hex cannot be used: " + targetHex);
        }
        int targetRed = Integer.parseInt(targetHex.substring(0, 2), 16);
        int targetGreen = Integer.parseInt(targetHex.substring(2, 4), 16);
        int targetBlue = Integer.parseInt(targetHex.substring(4, 6), 16);

        // Perform nearest neighbor algorithm
        for(int i = 0; i < items.size(); i++) {
            // Grab the hex value of the item (as a decimal number, not actually hex)
            int currentHex = items.get(i).getColorHex();

            // Get the distance value based on RGB closeness with target RGB
            double distance = getDistance(currentHex, targetRed, targetGreen, targetBlue);

            // If there is room in the list, add to the list until the number of results has been achieved
            if(resultsList.size() < numResults) {
                resultsList.add(items.get(i)); // This list will contain the best matches at the end
            }

            // The list is currently full
            else {
                // For every item in the current closest match list...
                for(GroutItem item : resultsList) {
                    // If the distance of the current object is less than the distance of the current item in the best match list...
                    if(distance < getDistance(item.getColorHex(), targetRed, targetGreen, targetBlue)) {
                        double highestDistance = getDistance(resultsList.get(0).getColorHex(), targetRed, targetGreen, targetBlue);
                        int dropIndex = 0;
                        for(int j = 1; j < resultsList.size(); j++) {
                            if(getDistance(resultsList.get(j).getColorHex(), targetRed, targetGreen, targetBlue) > highestDistance) {
                                highestDistance = getDistance(resultsList.get(j).getColorHex(), targetRed, targetGreen, targetBlue);
                                dropIndex = j;
                            }
                        }
                        resultsList.remove(dropIndex);
                        resultsList.add(items.get(i));
                        break;
                    }
                }
            }
        }
    }

    private double getDistance(int currentHex, int targetRed, int targetGreen, int targetBlue) {
        int red;
        int green;
        int blue;

        // Represents color as hex. It will be a 9 digit number
        String stringHex = Integer.toString(currentHex);

        /*
        If the starting numbers of the hex code were 0s, they would have been dropped when they were
        converted to an int. This loop restores those 0s for string manipulation purposes.
        */
        while(stringHex.length() < 9) {
            stringHex = "0" + stringHex;
        }
        // Break string up into red, green, and blue values by taking substrings of 9 digit number
        red = Integer.parseInt(stringHex.substring(0, 3));
        green = Integer.parseInt(stringHex.substring(3, 6));
        blue = Integer.parseInt(stringHex.substring(6, 9));


        // Distance formula for nearest neighbor
        double distance = Math.sqrt( Math.pow(red - targetRed, 2) + Math.pow(green - targetGreen, 2) + Math.pow(blue - targetBlue, 2) );
        return distance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        return view;
    }
}