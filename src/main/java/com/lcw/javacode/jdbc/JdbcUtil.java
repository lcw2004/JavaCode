package com.lcw.javacode.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public final class JdbcUtil {
	
	private static String url;
	private static String driverName;
	private static String userName;
	private static String password;

	private JdbcUtil() {
	}

	static {
		Properties prop = new Properties();
		InputStream is = JdbcUtil.class.getResourceAsStream("db.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		url = prop.getProperty("URL");
		driverName = prop.getProperty("DriverName");
		userName = prop.getProperty("UserName");
		password = prop.getProperty("PassWord");

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 释放资源文件
	 * @return
	 */
	public static void free(ResultSet rs, Statement st, Connection conn) {
		free(rs);
		free(st);
		free(conn);
	}

	/**
	 * 释放Connection
	 * @param conn
	 */
	private static void free(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放Statement
	 * @param st
	 */
	private static void free(Statement st) {
		try {
			if (st != null)
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 释放ResultSet
	 * @param rs
	 */
	private static void free(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
