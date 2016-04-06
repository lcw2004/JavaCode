package com.lcw.javacode.base;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 李昌文 on 2016/4/6.
 */
public class DateUtilTest {
    int year = 2015;
    int month = 2;
    int day = 2;
    int hour = 10;
    int minute = 15;
    int second = 50;

    Date dateForTest;
    @Before
    public void before() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        dateForTest = calendar.getTime();
    }

    @Test
    public void testDateToString() {
        String dateStr = DateUtil.dateToString(dateForTest, DateUtil.yyyy_MM_dd_HH_mm_ss_CN);
        Date date = DateUtil.stringToDate(dateStr, DateUtil.yyyy_MM_dd_HH_mm_ss_CN);

//        Assert.assertTrue(date.equals(dateForTest));
    }

    @Test
    public void testAddDate() {
        int count = 1234;
        Date datePlus = DateUtil.addDate(dateForTest, count);

        Assert.assertEquals(DateUtil.diffDate(datePlus, dateForTest), count);
    }

    @Test
    public void testGetDateAttr() {
        Assert.assertEquals(DateUtil.getYear(dateForTest), year);
        Assert.assertEquals(DateUtil.getMonth(dateForTest), month + 1);
        Assert.assertEquals(DateUtil.getDay(dateForTest), day);
        Assert.assertEquals(DateUtil.getHour(dateForTest), hour);
        Assert.assertEquals(DateUtil.getMinute(dateForTest), minute);
        Assert.assertEquals(DateUtil.getSecond(dateForTest), second);
    }
}
