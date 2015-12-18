package ru.fizteh.fivt.students.riskingh.TwitterStream;

/**
 * Created by max on 18/12/15.
 */

import twitter4j.*;
import twitter4j.TwitterStream;

import java.util.List;

public class StatusPrinter {
    public static final int SEARCH_DISTANCE_IN_KM = 5;
    public static final int MILLISECONDS_IN_SECOND = 1000;

    public static void printStatus(Status status) {
        System.out.print("[" + TimeUtils.formatTime(status.getCreatedAt()) + "]");
        System.out.print("@" + status.getUser().getScreenName() + ": ");
        if (status.isRetweet()) {
            System.out.print("ретвитнул ");
            System.out.print(status.getRetweetedStatus().getUser().getScreenName() + ":");
            System.out.print(status.getRetweetedStatus().getText());
        } else {
            System.out.print(status.getText());
            int retweetsCount = status.getRetweetCount();
            if (retweetsCount > 0) {
                System.out.print("(" + retweetsCount + " ретвитов)");
            }
        }
        System.out.println();
    }

    public static void print(Settings settings, GeoLocation location) throws TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();
        Query query = new Query(settings.getQuery()).geoCode(location, SEARCH_DISTANCE_IN_KM, "km");
        query.setCount(settings.getLimitNumber());
        QueryResult queryResult = twitter.search(query);
        List<Status> statuses = queryResult.getTweets();
        statuses.stream().
                filter(status -> !settings.hideRetweets() || !status.isRetweet()).
                forEach(ru.fizteh.fivt.students.riskingh.TwitterStream.StatusPrinter::printStatus);
    }

    public static void stream(Settings settings, GeoLocation location) throws TwitterException {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                if (settings.hideRetweets() && status.isRetweet()) {
                    return;
                }
                printStatus(status);
                try {
                    Thread.sleep(MILLISECONDS_IN_SECOND);
                } catch (Exception e) {
                    System.err.print("ERROR!");
                }
            }
        });
        FilterQuery filterQuery = new FilterQuery();
        double[][] locations = {
                {location.getLongitude() - SEARCH_DISTANCE_IN_KM, location.getLatitude() - SEARCH_DISTANCE_IN_KM},
                {location.getLongitude() + SEARCH_DISTANCE_IN_KM, location.getLatitude() + SEARCH_DISTANCE_IN_KM}
        };
        filterQuery.track(settings.getQuery()).locations(locations);
        twitterStream.filter(filterQuery);
    }
}
