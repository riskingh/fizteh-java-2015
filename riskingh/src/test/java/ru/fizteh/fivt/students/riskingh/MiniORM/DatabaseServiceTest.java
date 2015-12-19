package ru.fizteh.fivt.students.riskingh.MiniORM;

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import static ru.fizteh.fivt.students.riskingh.MiniORM.NameResolver.convertCamelToUnderscore;

public class DatabaseServiceTest {
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalClass_1() throws Exception {
        class X {
            @Column
            int y;
        }
        new DatabaseService<X>(X.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalClass_2() throws Exception {
        @Table
        class X {
            @Column
            int y;

            @Column(name = "y")
            String x;
        }
        new DatabaseService<X>(X.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalClass_3_0() throws Exception {
        @Table
        class X {
            @Column
            int y;
        }
        DatabaseService<X> x = new DatabaseService<>(X.class);
        x.delete(new X());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalClass_3_1() throws Exception {
        @Table
        class X {
            @Column
            int y;
        }
        DatabaseService<X> x = new DatabaseService<>(X.class);
        x.update(new X());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalClass_4() throws Exception {
        @Table
        class X {
            @PrimaryKey
            @Column
            int y;

            @PrimaryKey
            String x;
        }
        new DatabaseService<X>(X.class);
    }

    @Test
    public void convertTest() throws Exception {
        Assert.assertEquals(convertCamelToUnderscore("mayTheForceBeWithYou"), "may_the_force_be_with_you");
        Assert.assertEquals(convertCamelToUnderscore("WhatIsLove"), "what_is_love");
    }

    private static final String CONNECTION_NAME;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties credits = new Properties();
        try (InputStream inputStream = DatabaseServiceTest.class.getResourceAsStream("/h2test.properties")) {
            credits.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CONNECTION_NAME = credits.getProperty("connection_name");
        USERNAME = credits.getProperty("username");
        PASSWORD = credits.getProperty("password");
    }

    @Table
    static class MyClass {
        @PrimaryKey
        @Column
        Integer x;

        @Column
        String y;

        MyClass() {
        }

        MyClass(Integer x, String y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof MyClass) {
                MyClass omc = (MyClass) o;
                return x.equals(omc.x) && y.equals(omc.y);
            }
            return false;
        }
    }

    @Test
    public void bigTest() throws Exception {
        DatabaseService<MyClass> x = new DatabaseService<>(MyClass.class, "/h2test.properties");
        JdbcConnectionPool pool = JdbcConnectionPool.create(CONNECTION_NAME, USERNAME, PASSWORD);
        Connection tmp;
        tmp = pool.getConnection();
        tmp.createStatement().execute("DROP TABLE IF EXISTS my_class");
        tmp.close();

        x.createTable();
        tmp = pool.getConnection();
        tmp.createStatement().execute("INSERT INTO my_class (x, y) VALUES (1, 'one')");
        tmp.close();
        Assert.assertEquals(x.queryById(1).y, "one");
        x.insert(new MyClass(2, "X"));
        x.insert(new MyClass(3, "X"));
        tmp = pool.getConnection();
        ResultSet rs = tmp.createStatement().executeQuery("SELECT * FROM my_class WHERE y = 'X'");
        int count = 0;
        int sum = 0;
        while(rs.next()) {
            Assert.assertTrue(rs.getInt(1) == 2 || rs.getInt(1) == 3);
            ++count;
            sum += rs.getInt(1);
        }

        rs.close();
        tmp.close();

        Assert.assertEquals(count, 2);
        Assert.assertEquals(sum, 5);

        Assert.assertEquals(x.queryForAll().size(), 3);
        Assert.assertTrue(x.queryForAll().contains(new MyClass(1, "one")));
        x.update(new MyClass(1, "two"));
        Assert.assertTrue(!x.queryForAll().contains(new MyClass(1, "one")));
        Assert.assertTrue(x.queryForAll().contains(new MyClass(1, "two")));
        x.delete(new MyClass(1, ""));
        Assert.assertTrue(!x.queryForAll().contains(new MyClass(1, "two")));

        x.dropTable();
        x.close();
    }
}
