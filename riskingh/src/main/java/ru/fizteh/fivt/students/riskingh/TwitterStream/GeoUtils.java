package ru.fizteh.fivt.students.riskingh.TwitterStream;

/**
 * Created by max on 18/12/15.
 */

import twitter4j.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

public class GeoUtils {
    public static GeoLocation getLocationByPlace(String placeString) throws IOException,
            URISyntaxException, org.json.JSONException {
        Properties properties = new Properties();
        try (InputStream inputStream = GeoLocation.class.getResourceAsStream("/googleMaps.properties")) {
            properties.load(inputStream);
        }
        String googleMapsKey = properties.getProperty("google");
        String urlString = "https://maps.googleapis.com/maps/api/geocode/json?key="
                + googleMapsKey + "&address=" + URLEncoder.encode(placeString, "UTF-8");
        URL url = new URL(urlString);
        org.json.JSONObject jsonObject = JSONReader.readJSONFromUrl(url.toString());
        jsonObject = jsonObject.getJSONArray("results").getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location");
        return new GeoLocation(jsonObject.getDouble("lat"), jsonObject.getDouble("lng"));
    }
}
