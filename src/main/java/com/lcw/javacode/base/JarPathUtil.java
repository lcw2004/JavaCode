package com.lcw.javacode.base;

import java.io.File;

public class JarPathUtil {
	/**
	 * ͨ��class��ȡclass����jar����·��
	 * ע��ֻ�ܻ�ȡjar���е����·����JDK�е����޷�ȡ��·��
	 * @param clazz �����class
	 * @return jar���ļ�����·��
	 */
	public static String getJarFilePathByClass(Class<?> clazz) {
		String jarFilePath = "";
		if(clazz.getResource("") != null) {
			jarFilePath =  clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
		} else {
			System.out.println("JDK�е����·���޷���ȡ");
		}
		return new File(jarFilePath).getAbsolutePath();
	}

	/**
	 * ͨ��class��ȡclass����jar�������ơ�
	 * ע��ֻ�ܻ�ȡjar���е����·����JDK�е����޷�ȡ��·��
	 * @param clazz �����class
	 * @return jar���ļ���
	 */
	public static String getJarFileNameByClass(Class<?> clazz) {
		String jarFilePath = "";
		if(clazz.getResource("") != null) {
			jarFilePath =  clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
		} else {
			System.out.println("JDK�е����·���޷���ȡ");
		}
 		return new File(jarFilePath).getName();
	}

}
