package edu.emich.tilere.providers;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import edu.emich.tilere.models.Grout;
import edu.emich.tilere.utils.HttpUtils;

public class GroutProvider {
    public static final String kBaseDbUrl = "http://localhost:8080/app/api/1/";
    public static final String kGroutEndpoint = "grout/";

    /**
     * Retrieves all grout
     * This call is blocking as it queries the API and thus remote database
     * @return <code>ArrayList of Grout</code>
     */
    public static ArrayList<Grout> getAllGrout() {
        ArrayList<Grout> groutList = new ArrayList<>();

        try {
            HttpUtils.getPageContents(kBaseDbUrl);
        } catch (IOException ex) {
            Log.e(GroutProvider.class.getName(), "IO exception occurred when retrieving from endpoint." + Arrays.toString(ex.getStackTrace()));
        } catch (Exception ex) {
            Log.e(GroutProvider.class.getName(), "An unknown exception occurred" + Arrays.toString(ex.getStackTrace()));
        }

        return groutList;
    }
}
