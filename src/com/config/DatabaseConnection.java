package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.beans.ProAndSta;

public class DatabaseConnection {
	public static Connection databaseInitialized(Connection con){
		String uri = "jdbc:mysql://localhost:3306/waterquality?serverTimezone=UTC&characterEncoding=gbk&useSSL=false&autoReconnect=true";
	    String user ="root";
	    String password ="";
	    try{  
	    	Class.forName("com.mysql.jdbc.Driver"); 
	       con = DriverManager.getConnection(uri,user,password);
	    }
	    catch(SQLException e1){
	    	System.out.println(e1);
	    } catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
	    	System.out.println(e2);
		} 
	    return con;
	}
	
	public static ArrayList<String> getProvinceList(){
		ArrayList<String> plist = new ArrayList<String>();
		Connection dbconn = null;
		dbconn = DatabaseConnection.databaseInitialized(dbconn);
		String sql=null;
		PreparedStatement pstmt = null;
		sql="SELECT distinct province FROM all_data";
		try {
			pstmt = dbconn.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				String province = result.getString("province");
				plist.add(province);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if(!plist.isEmpty()) {
			System.out.println("链表不为空");
		}*/
	    return plist;
	}
	
	public static ArrayList<ProAndSta> getProAndStaList(){
		ArrayList<ProAndSta> pslist = new ArrayList<ProAndSta>();
		Connection dbconn = null;
		dbconn = DatabaseConnection.databaseInitialized(dbconn);
		String Spetime = "2022-01-01 12:00:00";
		try {
			String sql=null;
			PreparedStatement pstmt = null;
			sql="SELECT province,staname FROM all_data WHERE sta_time=?";
			pstmt = dbconn.prepareStatement(sql);
			pstmt.setString(1,Spetime);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				ProAndSta ps = new ProAndSta();
				ps.setProvince(result.getString("province"));
				ps.setStaname(result.getString("staname"));
				pslist.add(ps);
			}		
		}catch(SQLException e){
			e.printStackTrace();
		}
	    return pslist;
	}
}
