package edu.emich.thp.api;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Endpoints for API
 * Used to build URIs
 */
public class Endpoints {
    public static final String kDbHostUri = "http://groot.daltzsmith.com";
    public static final String kDbPort = "8080";
    public static final String kSupportedApiVersion= "/api/v1";
    public static final String kAllGroutEndpoint = "/grout/";

    /**
     * Exception safe Db URL retrieval. Java is bad
     * @return URL of the DB grout all endpoint
     */
    public static URL getDbHostUri() {
        URL url = null;

        try {
            url = new URL(kDbHostUri + ":" +kDbPort + kSupportedApiVersion + kAllGroutEndpoint + "all");
        } catch (MalformedURLException unused) {}

        return url;
    }
}
