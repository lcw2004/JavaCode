package com.lcw.javacode.http;

import com.lcw.javacode.base.BaseUtil;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpClient {
    private static Logger logger = Logger.getLogger(HttpClient.class);

    private String url = "";
    private int connectTimeout = 3000;
    private int readTimeout = 3000;
    private SSLContext sslContext = null;
    
    public HttpClient(String url, int connectTimeout, int readTimeout) {
        this.url = url;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public void initSSL(String pfxPath, String pfxPassword, String jksPath, String jksPassword) {
        try {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Http链接
     *
     * @return
     * @throws IOException
     */
    public HttpURLConnection getHttpURLConnection() throws IOException {
        logger.info("URL:" + url);
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
        logger.info("URL:" + url);
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

    /**
     * 提交数据
     *
     * @param data
     * @return
     * @throws IOException
     */
    public byte[] httpPost(byte[] data) throws IOException {
        URLConnection urlConnection = null;
        if (isHttps(url)) {
            urlConnection = getHttpsConnection();
        } else {
            urlConnection = getHttpURLConnection();
        }

        return httpPost(urlConnection, data);
    }

    /**
     * 将键值对以参数的信息提交到指定的URL
     *
     * @param paramMap
     * @return
     * @throws IOException
     */
    public byte[] httpPost(Map<String, String> paramMap) throws IOException {
        StringBuffer sb = buildParamString(paramMap);
        return httpPost(sb.toString().getBytes());
    }

    /**
     * 将数据发送到指定的URL里面
     *
     * @param urlConnection
     * @param data
     * @return
     */
    private byte[] httpPost(URLConnection urlConnection, byte[] data) {
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
        InputStream inputStream = null;
        try {
        	long sendTimeStart = System.currentTimeMillis();
            inputStream = urlConnection.getInputStream();
            long sendTimeEnd = System.currentTimeMillis();
            
            logger.info("申请证书请求——响应耗时:" + (sendTimeEnd - sendTimeStart));
            
            returnBytes = BaseUtil.transIsToBytes(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
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
    private static StringBuffer buildParamString(Map<String, String> paramMap) {
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
        return sb;
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
