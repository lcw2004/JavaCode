package com.lcw.javacode.base;

import org.apache.log4j.Logger;
import org.junit.Test;

public class JarPathUtilTest {

    @Test
    public void testGetJarFilePathByClass() {
        System.out.println(JarPathUtil.getJarFileNameByClass(Logger.class));
        System.out.println(JarPathUtil.getJarFilePathByClass(Logger.class));
    }
}
