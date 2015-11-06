package com.lcw.javacode.cipher;

import java.security.MessageDigest;

/**
 * MD5工具
 */
public class MD5Util {

    /**
     * 对字节数组求MD5
     * @param input
     * @return
     */
    public static String md5(byte[] input) {
        String md5String = "";
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input);
            byte[] md = messageDigest.digest();
            int mdLength = md.length;
            char chars[] = new char[mdLength * 2];
            int k = 0;
            for (int i = 0; i < mdLength; i++) {
                byte byte0 = md[i];
                chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
                chars[k++] = hexDigits[byte0 & 0xf];
            }
            md5String = new String(chars).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5String;
    }

    /**
     * 对字符串求MD5
     * @param input
     * @return
     */
    public static String md5(String input) {
        return md5(input.getBytes());
    }
}
