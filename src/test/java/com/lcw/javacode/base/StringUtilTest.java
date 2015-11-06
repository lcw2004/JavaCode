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
}
