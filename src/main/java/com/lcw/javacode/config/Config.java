package com.lcw.javacode.config;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件加载模板
 */
public class Config {

	private static Logger logger = Logger.getLogger(Config.class);

	private static Properties props;
	static  {
		try {
			props = new Properties();
			InputStream is = Config.class.getClassLoader().getResourceAsStream("Config.properties");
			props.load(is);
			is.close();

			logger.info("加载配置文件成功");
			logger.debug(props);
		} catch (Exception e) {
			props = null;
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置文件属性
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String getValue(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public static String getStringValue(String key) {
		return getValue(key, "");
	}

	/**
	 * 获取Boolean值
	 * @param key 键
	 * @return
	 */
	public static boolean getBooleanValue(String key) {
		return Boolean.valueOf(getValue(key, "false"));
	}

	/**
	 * 获取Int值
	 * @param key 键
	 * @return
	 */
	public static int getIntValue(String key) {
		return Integer.valueOf(getValue(key, "0"));
	}

	/**
	 * 获取Float值
	 * @param key 建
	 * @return
	 */
	public static float getFloatValue(String key) {
		return Float.valueOf(getValue(key, "0"));
	}
}
