package ru.fizteh.fivt.students.riskingh.TwitterStream;

/**
 * Created by max on 18/12/15.
 */
import com.beust.jcommander.*;

public class Settings {

    private static final int DEFAULT_TWEET_LIMIT = 10;

    @Parameter(names = {"-q", "--query"}, description = "Query or keywords for stream")
    private String query = "";

    @Parameter(names = {"-p", "--place"}, description = "Location")
    private String place = "nearby";

    @Parameter(names = {"-s", "--stream"}, description = "Stream")
    private boolean stream = false;

    @Parameter(names = {"--hideRetweets"}, description = "Hide retweets")
    private boolean hideRetweets = false;

    @Parameter(names = {"-l", "--limit"}, description = "Tweets limit")
    private Integer limit = DEFAULT_TWEET_LIMIT;

    @Parameter(names = {"-h", "--help"}, description = "Show this help")
    private boolean printHelp = false;

    public String getQuery() {
        return query;
    }

    public String getPlace() {
        return place;
    }

    public boolean stream() {
        return stream;
    }

    public boolean hideRetweets() {
        return hideRetweets;
    }

    public Integer getLimitNumber() {
        return limit;
    }

    public boolean printHelp() {
        return printHelp;
    }
}

