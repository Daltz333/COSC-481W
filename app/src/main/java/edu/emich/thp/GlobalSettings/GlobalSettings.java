package edu.emich.thp.GlobalSettings;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import edu.emich.thp.api.DbClient;
import edu.emich.thp.models.GroutItem;

public class GlobalSettings {
    private int hexColor = 0;
    private boolean includeMapei = true;
    private boolean includeTec = true;
    private int numSearchResults = 3;

    private static GlobalSettings _GlobalSettings = null;

    public static GlobalSettings getInstance() {
        if (_GlobalSettings == null) {
            _GlobalSettings = new GlobalSettings();
        }

        return _GlobalSettings;
    }

    public int getNumSearchResults() {
        return numSearchResults;
    }

    public void setNumSearchResults(int numSearchResults) {
        this.numSearchResults = numSearchResults;
    }

    public boolean includeMapei() {
        return includeMapei;
    }

    public void setIncludeMapei(boolean includeMapei) {
        this.includeMapei = includeMapei;
    }

    public boolean includeTec() {
        return includeTec;
    }

    public void setIncludeTec(boolean includeTec) {
        this.includeTec = includeTec;
    }

    private GlobalSettings() {}

    public int getHexColor() {
        return hexColor;
    }

    public void setHexColor(int newColor) {
        hexColor = newColor;
    }

}
