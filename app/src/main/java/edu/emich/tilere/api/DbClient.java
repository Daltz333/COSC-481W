package edu.emich.tilere.api;

import java.util.ArrayList;
import java.util.Random;

import edu.emich.tilere.models.GroutItem;

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

    /**
     * Returns the list of Grout from memory and attempts to refresh in the background.
     * If no grout exists in memory, this call is blocking.
     * NOTE: Currently this method is stubbed
     * @return List of grout items from the remote database
     */
    public ArrayList<GroutItem> getGrout() {
        ArrayList<GroutItem> items = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            int nextInt = random.nextInt(256*256*256);
            items.add(new GroutItem(
                    (long)i,
                    i + "Funny Brand",
                    i + "-code",
                    i + "-name",
                    nextInt,
                    "https://m.media-amazon.com/images/I/81m8NtsfFML.__AC_SX300_SY300_QL70_FMwebp_.jpg")
            );
        }

        return items;
    }
}
