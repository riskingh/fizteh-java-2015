package ru.fizteh.fivt.students.riskingh.TwitterStream;

import org.junit.Assert;
import org.junit.Test;
import twitter4j.GeoLocation;

import static ru.fizteh.fivt.students.riskingh.TwitterStream.GeoUtils.getLocationByPlace;

/**
 * Created by max on 19/12/15.
 */

public class GeoUtilsTest {
    @Test
    public void testGetLocationByPlace() throws Exception {
        String city1 = "Moscow, Russia";
        String city2 = "Kostroma, Russia";

        GeoLocation geo1 = getLocationByPlace(city1);
        GeoLocation geo2 = getLocationByPlace(city2);

        Assert.assertEquals(geo1.getLatitude(), 55.755826, 1E-3);
        Assert.assertEquals(geo1.getLongitude(), 37.6173, 1E-3);

        Assert.assertEquals(geo2.getLatitude(), 57.77748, 1E-3);
        Assert.assertEquals(geo2.getLongitude(), 40.96989, 1E-3);
    }
}