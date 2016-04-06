package com.lcw.javacode.base;


import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class ShellUtil {

    /**
     * 在Linux下执行命令
     * @param cmd
     * @return
     */
    public static String execInLinux(String cmd) {
        String result = null;

        Process process = null;
        try {
            String[] cmdA = { "/bin/sh", "-c", cmd };
            process = Runtime.getRuntime().exec(cmdA);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(process != null) {
                process.destroy();
            }
        }
        return result;
    }

    /**
     * 在Windows下执行命令
     * @param cmd
     * @return
     */
    public static void execInWindows(String cmd) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(process != null) {
                process.destroy();
            }
        }
    }
}
