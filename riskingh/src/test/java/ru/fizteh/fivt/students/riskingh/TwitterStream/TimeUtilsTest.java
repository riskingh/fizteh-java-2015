package ru.fizteh.fivt.students.riskingh.TwitterStream;

/**
 * Created by max on 19/12/15.
 */

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.fizteh.fivt.students.riskingh.TwitterStream.TimeUtils.formatTime;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtilsTest {
    @Test
    public void testFormatTime() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);

        Assert.assertEquals("только что", formatTime(calendar.getTime()));

        calendar.add(Calendar.MINUTE, -5);
        Assert.assertEquals("6 минут назад", formatTime(calendar.getTime()));

        calendar.add(Calendar.MINUTE, -54);
        if (calendar.get(Calendar.HOUR) < 22) {
            Assert.assertEquals("1 часов назад", formatTime(calendar.getTime()));
        }

        calendar.add(Calendar.HOUR, -23);
        Assert.assertEquals("вчера", formatTime(calendar.getTime()));

        calendar.add(Calendar.DAY_OF_YEAR, -5);
        Assert.assertEquals("6 дней назад", formatTime(calendar.getTime()));

    }
}