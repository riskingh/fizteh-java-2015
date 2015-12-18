package ru.fizteh.fivt.students.riskingh.TwitterStream;

/**
 * Created by max on 18/12/15.
 */

import com.beust.jcommander.JCommander;
import twitter4j.GeoLocation;
import twitter4j.TwitterException;

public class TwitterStream {
    public static void main(String[] args) throws TwitterException {
        Settings settings = new Settings();
        try {
            JCommander jcommander = new JCommander(settings, args);
            if (settings.printHelp()) {
                jcommander.usage();
                return;
            }
        } catch (Exception e) {
            JCommander jcommander = new JCommander(settings);
            jcommander.usage();
            return;
        }

        System.out.print("Твиты по запросу " + settings.getQuery() + " для " + settings.getPlace() + ":");
        System.out.println();
        try {
            GeoLocation location = GeoUtils.getLocationByPlace(settings.getPlace());
            if (settings.stream()) {
                StatusPrinter.stream(settings, location);
            } else {
                StatusPrinter.print(settings, location);
            }
        } catch (Exception e) {
            System.err.print("ERROR: " + e.toString());
            e.printStackTrace();
        }
    }
}
