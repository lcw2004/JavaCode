package com.lcw.javacode;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

public class BaseTestCase {

    protected static Logger logger = Logger.getLogger(BaseTestCase.class);
    long start;
    long end;

    @Before
    public void before() {
        start = System.currentTimeMillis();
    }

    @After
    public void after() {
        end = System.currentTimeMillis();

        logger.info(String.format("耗时：%d ms", Long.valueOf(end - start)));
    }
}
