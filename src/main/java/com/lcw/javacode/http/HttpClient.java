package com.lcw.javacode.http;

import com.lcw.javacode.base.BaseUtil;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpClient {
    private static Logger logger = Logger.getLogger(HttpClient.class);

    private String url = "";
    private int connectTimeout = 3000;
    private int readTimeout = 3000;
    private SSLContext sslContext = null;

    /**
     * @param url URL路径
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读数据超时时间
     */
    public HttpClient(String url, int connectTimeout, int readTimeout) {
        logger.info(String.format("URL[%s], 连接超时时间[%s], 读数据超时时间[%d]", url, connectTimeout, readTimeout));
        this.url = url;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * @param url URL路径
     */
    public HttpClient(String url) {
        this(url, 3000, 3000);
    }

    /**
     * 设置SSL需要的证书信息
     * @param pfxPath 客户端证书路径
     * @param pfxPassword 客户端证书密码
     * @param jksPath 可信证书存储文件路径
     * @param jksPassword 可信证书存储文件密码
     */
    public void initSSL(String pfxPath, String pfxPassword, String jksPath, String jksPassword) throws Exception{
        //初始化客户端证书
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        KeyStore pfxKeyStore = KeyStore.getInstance("PKCS12");
        pfxKeyStore.load(HttpClient.class.getResourceAsStream(pfxPath), pfxPassword.toCharArray());
        keyManagerFactory.init(pfxKeyStore, pfxPassword.toCharArray());

        //初始化可信证书存储文件(JKS文件)
        KeyStore jksKeyStore = KeyStore.getInstance("JKS");
        jksKeyStore.load(HttpClient.class.getResourceAsStream(jksPath), jksPassword.toCharArray());
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(jksKeyStore);

        sslContext = SSLContext.getInstance("SSL", "SunJSSE");

        //初始化SSL配置
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
    }

    /**
     * 获取Http链接
     *
     * @return
     * @throws IOException
     */
    public HttpURLConnection getHttpURLConnection() throws IOException {
        URL myURL = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        httpConn.setUseCaches(false);
        httpConn.setReadTimeout(50000);
        httpConn.setConnectTimeout(50000);
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        return httpConn;
    }

    /**
     * 获取Https链接
     *
     * @return
     * @throws IOException
     */
    public HttpsURLConnection getHttpsConnection() throws IOException {

        SSLSocketFactory ssf = sslContext.getSocketFactory();
        URL myURL = new URL(url);
        HttpsURLConnection httpsConn = (HttpsURLConnection) myURL.openConnection();
        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setRequestMethod("POST");

        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connectTimeout));
        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(readTimeout));
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true"); 

        //设置允许输出
        httpsConn.setDoOutput(true);
        httpsConn.setInstanceFollowRedirects(false);

        return httpsConn;
    }

    public URLConnection getURLConnection() throws IOException {
        URLConnection urlConnection = null;
        if (isHttps(url)) {
            urlConnection = getHttpsConnection();
        } else {
            urlConnection = getHttpURLConnection();
        }
        return urlConnection;
    }

    /**
     * 提交数据
     *
     * @param data
     * @return
     * @throws IOException
     */
    public byte[] post(byte[] data) throws IOException {
        return post(getURLConnection(), data);
    }

    /**
     * 将键值对以参数的信息提交到指定的URL
     *
     * @param paramMap
     * @return
     * @throws IOException
     */
    public byte[] post(Map<String, String> paramMap) throws IOException {
        String paramStr = buildParamString(paramMap);
        logger.info("提交参数：" + paramStr);
        return post(paramStr.getBytes());
    }

    /**
     * 从指定的URL里面Get数据
     *
     * @return
     * @throws IOException
     */
    public byte[] get() throws IOException {
        return get(getURLConnection());
    }

    /**
     * 从指定的URL里面Get数据
     *
     * @param urlConnection
     * @return
     */
    private byte[] get(URLConnection urlConnection) {
        byte[] returnBytes = null;
        InputStream is = null;
        try {
            is = urlConnection.getInputStream();
            returnBytes = BaseUtil.transIsToBytes(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnBytes;
    }

    /**
     * 将数据Post到指定的URL里面
     *
     * @param urlConnection
     * @param data
     * @return
     */
    private byte[] post(URLConnection urlConnection, byte[] data) {
        // 发送数据
        OutputStream os = null;
        try {
            os = urlConnection.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 接收数据
        byte[] returnBytes = null;
        InputStream is = null;
        try {
            is = urlConnection.getInputStream();
            returnBytes = BaseUtil.transIsToBytes(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return returnBytes;
    }

    /**
     * 将参数转为用于提交的字符串
     *
     * @param paramMap
     * @return
     */
    private static String buildParamString(Map<String, String> paramMap) {
        StringBuffer sb = new StringBuffer();
        if (paramMap != null) {
            Set set = paramMap.keySet();
            for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                String value = (String) paramMap.get(key);
                sb.append(key).append("=").append(value).append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 判断是否是Https协议
     *
     * @param url
     * @return
     */
    private static boolean isHttps(String url) {
        return url.contains("https");
    }
}
