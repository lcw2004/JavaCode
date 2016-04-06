package com.lcw.javacode.http;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李昌文 on 2016/4/6.
 */
public class HttpClientTest {

    @Test
    public void testHttpClientGet() throws IOException {
        String url = "http://www.baidu.com";
        HttpClient httpClient = new HttpClient(url);
        byte[] bytes = httpClient.get();
        System.out.println(new String(bytes));
    }

    @Test
    public void testHttpClientPost() throws IOException {
        String url = "http://www.baidu.com/s";
        HttpClient httpClient = new HttpClient(url);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("wd", "test");
        byte[] bytes = httpClient.post(paramMap);
        System.out.println(new String(bytes));
    }
}
