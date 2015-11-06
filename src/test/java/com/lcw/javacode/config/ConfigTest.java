package com.lcw.javacode.config;

import com.lcw.javacode.BaseTestCase;
import org.junit.Assert;
import org.junit.Test;

public class ConfigTest extends BaseTestCase{

    @Test
    public void testGetValue() {
        Assert.assertEquals(Config.getBooleanValue("TEST_BOOLEAN"), true);
        Assert.assertEquals(Config.getIntValue("TEST_INT"), 2);
        Assert.assertTrue(Config.getFloatValue("TEST_FLOAT") == 2.1f);
        Assert.assertEquals(Config.getStringValue("TEST_STRING"), "hello world!");
    }

}
