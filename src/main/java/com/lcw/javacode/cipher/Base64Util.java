package com.lcw.javacode.cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Base64Util {

    /**
     * 对字符串进行Base64编码
     *
     * @param value 需编码的字符串
     * @return 编码后的字符串
     */
    public static String encodeString(String value) {
        String encodeValue = null;
        if (value != null) {
            encodeValue = new sun.misc.BASE64Encoder().encode(value.getBytes());
            return encodeValue;
        }
        return encodeValue;
    }

    /**
     * 对字符串进行Base64编码
     *
     * @param value   需编码的字符串
     * @param charSet 编码格式
     * @return 编码后的字符串
     */
    public static String encodeString(String value, String charSet) {
        String encodeValue = null;
        if (value != null) {
            try {
                encodeValue = new BASE64Encoder().encode(value.getBytes(charSet));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return encodeValue;
    }

    /**
     * 对字符串进行Base64解码
     *
     * @param value 要编码的字符串
     * @return 编码后的字符串
     */
    public static String decodeString(String value) {
        return decodeString(value, null);
    }

    /**
     * 对字符串进行Base64解码
     *
     * @param value   要编码的字符串
     * @param charSet 编码格式
     * @return 编码后的字符串
     */
    public static String decodeString(String value, String charSet) {
        String decodeValue = null;
        if (value != null) {
            try {
                BASE64Decoder decoder = new BASE64Decoder();

                byte[] decodeBytes = decoder.decodeBuffer(value);
                if(charSet != null) {
                    decodeValue = new String(decodeBytes, charSet);
                } else {
                    decodeValue = new String(decodeBytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return decodeValue;
    }

    /**
     * 对字节数组进行Base64编码
     *
     * @param input 要编码的字节数组
     * @return 编码后的字节数组
     */
    public static byte[] encodeBytes(byte[] input) {
        byte[] b = null;
        try {
            if (input != null && input.length > 0) {
                BASE64Encoder encoder = new BASE64Encoder();
                b = encoder.encode(input).getBytes();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * 对字节数组进行Base64解码
     *
     * @param input 要编码的字节数组
     * @return 编码后的字节数组
     */
    public static byte[] decodeBytes(byte[] input) {
        byte[] b = null;
        try {
            if (input != null && input.length > 0) {
                BASE64Decoder bd = new BASE64Decoder();
                b = bd.decodeBuffer(new ByteArrayInputStream(input));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

}
