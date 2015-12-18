package ru.fizteh.fivt.students.riskingh.TwitterStream;

/**
 * Created by max on 18/12/15.
 */

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static String formatTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar today = Calendar.getInstance();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);

        Date current = new Date();
        long difference = current.getTime() - date.getTime();

        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(difference);
        long differenceInHours = TimeUnit.MILLISECONDS.toHours(difference);
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(difference);

        if (differenceInMinutes < 2) {
            return "только что";
        } else if (differenceInHours < 1) {
            return Long.toString(differenceInMinutes) + " минут назад";
        } else if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return Long.toString(differenceInHours) + " часов назад";
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR)
                && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "вчера";
        } else {
            return Long.toString(differenceInDays) + " дней назад";
        }
    }
}
