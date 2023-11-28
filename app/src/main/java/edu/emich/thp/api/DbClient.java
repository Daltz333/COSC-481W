package edu.emich.thp.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import edu.emich.thp.models.GroutItem;

/**
 * Class for retrieving grout from the remote database
 */
public class DbClient {
    private static DbClient _dbClient = null;

    /**
     * Retrieves a singleton instance of the DbClient
     * For information on the singleton paradigm, see
     * <a href="https://betterprogramming.pub/what-is-a-singleton-2dc38ca08e92">this</a>
     * @return Reused instance of DbClient. May be blocking on first call.
     */
    public static DbClient getInstance() {
        if (_dbClient == null) {
            _dbClient = new DbClient();
        }

        return _dbClient;
    }

    private DbClient() {}

    private ArrayList<GroutItem> groutItems = new ArrayList<>();
    private boolean isGroutThreadRunning = false;
    private final Object lock = new Object();

    /**
     * Refreshes the list of grout asynchronously by starting up another thread
     * And fetching it asynchronously
     */
    public void refreshGroutAsync() {
        if (isGroutThreadRunning) {
            return; // We're already running
        }

        isGroutThreadRunning = true;
        Thread t = new Thread(() -> {
            synchronized (lock) {
                try {
                    // Start connection to endpoint
                    URL dbUrl = Endpoints.getDbHostUri();
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    dbUrl.openStream()
                            )
                    );

                    // Read response
                    String json = "";
                    while (true) {
                        String newLine = in.readLine();
                        if (newLine == null) {
                            break;
                        } else {
                            json = json + newLine;
                        }
                    }
                    in.close();

                    // Copy into memory
                    ArrayList<GroutItem> tempItems = new ArrayList<>();

                    // Parse JSON
                    JSONArray jsonRoot = new JSONArray(json);
                    for (int i = 0; i < jsonRoot.length(); i++) {
                        JSONObject obj = jsonRoot.getJSONObject(i);
                        String brandName = obj.getString("brandName");
                        String brandCode = obj.getString("brandCode");
                        String groutName = obj.getString("groutName");
                        String hexAsString = obj.getString("colorHex");
                        int hex = 0;

                        try {
                            String red = Integer.toString(Integer.parseInt(hexAsString.substring(1, 3), 16));
                            if (red.length() < 3) {
                                red = "0" + red;
                            }

                            String green = Integer.toString(Integer.parseInt(hexAsString.substring(3, 5), 16));
                            if (green.length() < 3) {
                                green = "0" + green;
                            }
                            String blue = Integer.toString(Integer.parseInt(hexAsString.substring(5, 7), 16));
                            if (blue.length() < 3) {
                                blue = "0" + blue;
                            }

                            hex = Integer.parseInt(red + green + blue);

                        } catch (NumberFormatException e) {
                            Log.e("refreshGroutAsync", "Failed to parse hex input " + hexAsString);
                        }
                        if (hex != 0) {
                            boolean duplicate = false;
                            for (GroutItem item : tempItems) {
                                if (item.getGroutName().equals(groutName)) {
                                    duplicate = true;
                                    break;
                                }
                            }
                            if (!duplicate) {
                                tempItems.add(new GroutItem(brandName, brandCode, groutName, hex));
                            }
                        }
                    }

                    // Acquire mutex and replace the public copy
                    groutItems = tempItems;
                } catch (Exception ex) {
                    Log.e("refreshGroutAsync", "Exception occurred while attempting to refresh grout: " + ex.toString());
                }
            }

            isGroutThreadRunning = false;
        });

        t.setName("refreshGroutThread");
        t.setDaemon(true); // kill thread on application sleep
        t.start();
    }

    /**
     * Returns the list of Grout from memory
     */
    public ArrayList<GroutItem> getGrout() {
        synchronized (lock) {
            return groutItems;
        }
    }
}
