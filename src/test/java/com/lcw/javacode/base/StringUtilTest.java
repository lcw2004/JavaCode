package com.lcw.javacode.base;

import com.lcw.javacode.base.StringUtil;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void testNumber10To16() {
        Assert.assertTrue("0".equals(StringUtil.number10To16("0")));
        Assert.assertTrue("a8".equals(StringUtil.number10To16("168")));
        Assert.assertTrue("7fffffff".equals(StringUtil.number10To16(String.valueOf(Integer.MAX_VALUE))));
    }

    @Test
    public void testNumber16To110() {
        Assert.assertTrue("0".equals(StringUtil.number16To10("0")));
        Assert.assertTrue("168".equals(StringUtil.number16To10("a8")));
        Assert.assertTrue(String.valueOf(Integer.MAX_VALUE).equals(StringUtil.number16To10("7fffffff")));
    }

    @Test
    public void randomString() {
        StringUtil.randomString(12);
    }

    @Test
    public void testIsInteger() {
        Assert.assertTrue(StringUtil.isInteger("-1"));
        Assert.assertTrue(StringUtil.isInteger("0"));
        Assert.assertTrue(StringUtil.isInteger("01"));
        Assert.assertTrue(StringUtil.isInteger("10000000"));

        Assert.assertFalse(StringUtil.isInteger("0.1"));
        Assert.assertFalse(StringUtil.isInteger("a"));
    }

    @Test
    public void testIsDouble() {
        Assert.assertTrue(StringUtil.isDouble("0.1"));
        Assert.assertTrue(StringUtil.isDouble("-0.1"));

        Assert.assertFalse(StringUtil.isDouble("0"));
        Assert.assertFalse(StringUtil.isDouble("01"));
        Assert.assertFalse(StringUtil.isDouble("10000000"));
        Assert.assertFalse(StringUtil.isDouble("a"));

        Assert.assertFalse(StringUtil.isInteger("-1"));
    }

    @Test
    public void testIsLetter() {
//        StringUtil.isLetter()
    }

    @Test
    public void testIsEmail() {
        Assert.assertTrue(StringUtil.isEmail("test@163.com"));
        Assert.assertTrue(StringUtil.isEmail("test@163.com.123"));
        Assert.assertFalse(StringUtil.isEmail("test@@163.com.123"));
        Assert.assertFalse(StringUtil.isEmail("test163.com.123"));
    }

    @Test
    public void testHangeToBig() {
        System.out.println(StringUtil.hangeToBig("123456789"));
    }

}
