package com.lcw.javacode.base;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class JarPathUtil {

    /**
     * 通过class获取class所在jar包的路径
     * 注：只能获取jar包中的类的路径，JDK中的类无法取到路径
     *
     * @param clazz 对象的class
     * @return jar包文件绝对路径
     */
    public static String getJarFilePathByClass(Class<?> clazz) {
        String jarFilePath = "";
        if (clazz.getResource("") != null) {
            jarFilePath = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        } else {
            System.out.println("JDK中的类的路径无法获取");
        }
        return new File(jarFilePath).getAbsolutePath();
    }

    /**
     * 通过class获取class所在jar包的名称。
     * 注：只能获取jar包中的类的路径，JDK中的类无法取到路径
     *
     * @param clazz 对象的class
     * @return jar包文件名
     */
    public static String getJarFileNameByClass(Class<?> clazz) {
        String jarFilePath = "";
        if (clazz.getResource("") != null) {
            jarFilePath = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        } else {
            System.out.println("JDK中的类的路径无法获取");
        }
        return new File(jarFilePath).getName();
    }

    /**
     * 检查class是否重复
     * @param cls 对象的class
     */
    public static void checkDuplicate(Class cls) {
        checkDuplicate(cls.getName().replace('.', '/') + ".class");
    }

    /**
     * 检查资源是否重复
     * @param path 资源路径
     */
    public static void checkDuplicate(String path) {
        try {
            // 在ClassPath搜文件
            Enumeration urls = Thread.currentThread().getContextClassLoader().getResources(path);
            Set files = new HashSet();
            while (urls.hasMoreElements()) {
                URL url = (URL)urls.nextElement();
                if (url != null) {
                    String file = url.getFile();
                    if (file != null && file.length() > 0) {
                        files.add(file);
                    }
                }
            }

            // 如果有多个，就表示重复
            if (files.size() > 1) {
                throw new RuntimeException("Duplicate class " + path + " in " + files.size() + " jar " + files);
            }
        } catch (Throwable e) {
            // 防御性容错
           e.printStackTrace();
        }
    }

}
