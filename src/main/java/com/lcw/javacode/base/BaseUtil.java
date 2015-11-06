package com.lcw.javacode.base;


import java.io.*;
import java.util.List;

public class BaseUtil {
    private static final int BUFF_SIZE = 1024;

    /**
     * 检查数组是否为空
      * @param input
     * @return
     */
    public static boolean isNotNull(byte[] input) {
        if (input != null && input.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查List是否为空
     * @param input
     * @return
     */
    public static boolean isNotNull(List<?> input) {
        if (input != null && input.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查字符串是否为空
     * @param input
     * @return
     */
    public static boolean isNotNull(String input) {
        if (input != null && input.trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将InputStream中的内容读取出来，转为字节数据 InputStream --> byte[]
     *
     * @param is
     * @return
     */
    public static byte[] transIsToBytes(InputStream is) {
        byte[] in2b = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buff = new byte[BUFF_SIZE];
            int len = 0;
            while ((len = is.read(buff, 0, BUFF_SIZE)) > 0) {
                baos.write(buff, 0, len);
                baos.flush();
            }
            in2b = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return in2b;
    }

    /**
     * 获取异常信息，将异常信息输出为一个字符串
     * @param e 异常
     * @return
     */
    public static String getExceptionInfo(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

}
