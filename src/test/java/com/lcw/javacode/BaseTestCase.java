package com.lcw.javacode;

import com.lcw.javacode.config.Log4jLoader;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

public class BaseTestCase {

    protected static Logger logger = Logger.getLogger(BaseTestCase.class);
    long start;
    long end;

    @Before
    public void before() {
        Log4jLoader.loadLog4j();
        start = System.currentTimeMillis();
    }

    @After
    public void after() {
        end = System.currentTimeMillis();

        logger.info(String.format("耗时：%d ms", Long.valueOf(end - start)));
    }

}

/**
 * 用于打印方法执行的探针
 */
class MethodPin {
    private static Long timeBefore;
    private static Long timeAfter;
    private static int time = 0;

    public static void insert() {
        if(timeBefore == null) {
            timeBefore = System.currentTimeMillis();
        }
        if(timeAfter == null) {
            timeAfter = System.currentTimeMillis();
        }
        timeBefore = timeAfter;
        timeAfter = System.currentTimeMillis();
        ++time;
        System.out.println("timeBefore:" + timeBefore);
        System.out.println("timeAfter:" + timeAfter);
        System.out.println("耗时" + time + ":" + (timeAfter - timeBefore));
    }
}
