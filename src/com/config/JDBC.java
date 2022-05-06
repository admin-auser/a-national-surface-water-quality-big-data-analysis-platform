package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JDBC {
	private static String USERNAME = "root";
	private static String PASSWORD = "";
	private static String URL = "jdbc:mysql://192.168.81.128:3306/waterquality?serverTimezone=UTC&characterEncoding=gbk&useSSL=false&autoReconnect=true";
	private static String DricerClass = "com.mysql.jdbc.Driver";
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
	
		System.out.println("你好");
		//1.加载JDBC驱动
		try {
			Class.forName(DricerClass);
		}catch (Exception e) {
			System.out.println("加载JDBC驱动失败");
		}
		//2.获得数据库连接
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("连接成功");
		}catch (Exception e) {
			System.out.println("获取数据库连接失败!");
			e.printStackTrace();
		}
		
		//3.通过数据库的链接操作数据库
		String sql = "select * from all_result limit 10";
		PreparedStatement pre = conn.prepareStatement(sql);
		ResultSet rs = pre.executeQuery();
		while( rs.next() ){
			String username = rs.getString(1);
			System.out.println("the province is " + username);
		}
	}
}