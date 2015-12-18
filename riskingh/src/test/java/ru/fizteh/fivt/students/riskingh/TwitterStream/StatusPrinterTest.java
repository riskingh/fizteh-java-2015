package ru.fizteh.fivt.students.riskingh.TwitterStream;

import com.sun.org.apache.xpath.internal.operations.Bool;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twitter4j.Status;
import twitter4j.User;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static ru.fizteh.fivt.students.riskingh.TwitterStream.StatusPrinter.printStatus;

/**
 * Created by max on 19/12/15.
 */
public class StatusPrinterTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testPrintStatus() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -2);

        User user1 = mock(User.class);
        when(user1.getScreenName()).thenReturn("UserNumber1");

        User user2 = mock(User.class);
        when(user2.getScreenName()).thenReturn("UserNumber2");

        Status status1 = mock(Status.class);
        when(status1.getCreatedAt()).thenReturn(calendar.getTime());
        when(status1.getUser()).thenReturn(user1);
        when(status1.isRetweet()).thenReturn(Boolean.FALSE);
        when(status1.getText()).thenReturn("lorem ipsum");
        when(status1.getRetweetCount()).thenReturn(10);

        printStatus(status1);

        calendar.add(Calendar.MINUTE, 1);

        Status status2 = mock(Status.class);
        when(status2.getCreatedAt()).thenReturn(calendar.getTime());
        when(status2.getUser()).thenReturn(user2);
        when(status2.isRetweet()).thenReturn(Boolean.TRUE);
        when(status2.getRetweetedStatus()).thenReturn(status1);

        printStatus(status2);

        BufferedReader reader = new BufferedReader(new StringReader(outputStream.toString()));

        Assert.assertEquals("[2 минут назад]@UserNumber1: lorem ipsum(10 ретвитов)", reader.readLine());
        Assert.assertEquals("[только что]@UserNumber2: ретвитнул UserNumber1:lorem ipsum", reader.readLine());
    }
}