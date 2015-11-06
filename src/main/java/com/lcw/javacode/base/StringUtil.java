package com.lcw.javacode.base;

import java.math.BigInteger;
import java.util.Random;

public class StringUtil {

    /**
     * 将十进制数据转换为十六进制数据
     * @param input 待转换字符
     * @return
     */
    public static String number10To16(String input) {
        BigInteger bigInteger = new BigInteger(input, 10);
        return bigInteger.toString(16);
    }

    /**
     * 将十六进制数据转换为十进制数据
     * @param input 待转换字符
     * @return
     */
    public static String number16To10(String input) {
        BigInteger bigInteger = new BigInteger(input, 16);
        return bigInteger.toString(10);
    }

    /**
     * 生成一个随机的字符串
     * @param length 字符串长度
     * @return
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }

        String letters = "0123456789" + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random randGen = null;
        char[] numbersAndLetters = null;
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = letters.toCharArray();
        }

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(numbersAndLetters.length)];
        }
        return new String(randBuffer);
    }
}
