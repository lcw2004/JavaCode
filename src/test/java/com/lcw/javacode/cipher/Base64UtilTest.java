package com.lcw.javacode.cipher;

import org.junit.Assert;
import org.junit.Test;

public class Base64UtilTest {

    @Test
    public void testBase64String() {
        String input = "你好，世界！";

        String output_defualt = Base64Util.encodeString(input);
        Assert.assertEquals(input, Base64Util.decodeString(output_defualt));

        String output_utf8  = Base64Util.encodeString(input, "UTF-8");
        Assert.assertEquals(input, Base64Util.decodeString(output_utf8, "UTF-8"));

        String output_gbk  = Base64Util.encodeString(input, "GBK");
        Assert.assertEquals(input, Base64Util.decodeString(output_gbk, "GBK"));
    }

    @Test
    public void testBase64Bytes() {
        byte[] input = "你好，世界！".getBytes();

        byte[] output_defualt = Base64Util.encodeBytes(input);
        Assert.assertArrayEquals(input, Base64Util.decodeBytes(output_defualt));
    }
}
