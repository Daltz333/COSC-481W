package edu.emich.tilere.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {
    private static final StringBuilder stringBuilder = new StringBuilder();
    public static String getPageContents(String url) throws IOException {
        stringBuilder.delete(0, stringBuilder.length() - 1);

        URL yahoo = new URL("url");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yahoo.openStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(inputLine);
        }

        in.close();
        return stringBuilder.toString();
    }
}
