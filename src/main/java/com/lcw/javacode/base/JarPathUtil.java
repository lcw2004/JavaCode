package com.lcw.javacode.base;

import java.io.File;

public class JarPathUtil {
	/**
	 * 通过class获取class所在jar包的路径
	 * 注：只能获取jar包中的类的路径，JDK中的类无法取到路径
	 * @param clazz 对象的class
	 * @return jar包文件绝对路径
	 */
	public static String getJarFilePathByClass(Class<?> clazz) {
		String jarFilePath = "";
		if(clazz.getResource("") != null) {
			jarFilePath =  clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
		} else {
			System.out.println("JDK中的类的路径无法获取");
		}
		return new File(jarFilePath).getAbsolutePath();
	}

	/**
	 * 通过class获取class所在jar包的名称。
	 * 注：只能获取jar包中的类的路径，JDK中的累无法取到路径
	 * @param clazz 对象的class
	 * @return jar包文件名
	 */
	public static String getJarFileNameByClass(Class<?> clazz) {
		String jarFilePath = "";
		if(clazz.getResource("") != null) {
			jarFilePath =  clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
		} else {
			System.out.println("JDK中的类的路径无法获取");
		}
 		return new File(jarFilePath).getName();
	}

}
