package com.lcw.javacode.cipher;

import org.junit.Assert;
import org.junit.Test;


public class MD5UtilTest {

    @Test
    public void testMD5() {
        String input = "123";
        Assert.assertTrue("202cb962ac59075b964b07152d234b70".equals(MD5Util.md5(input)));
    }
}
