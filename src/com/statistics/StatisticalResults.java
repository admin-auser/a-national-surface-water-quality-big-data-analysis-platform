package com.statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.beans.Result;
import com.config.Auxiliary;
import com.config.DatabaseConnection;
import com.config.LinuxDatabaseConnection;

public class StatisticalResults {
	private static Connection dbconn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet result = null;
	public static void endConn() {
		try {
	         dbconn.close();
	      }catch(Exception e){
		     e.printStackTrace();
	      }
	}
	//桔子洲2021年月平均值
    //public static ArrayList<String[]> index() throws SQLException{
	public static ArrayList<Result> averageValue(String staname,String parameter,String monthOryear) throws SQLException{
        //ArrayList<String[]> list = new ArrayList<String[]>();
        ArrayList<Result> talist = null;
        talist = new ArrayList<Result>();
        //dbconn = LinuxDatabaseConnection.databaseInitialized(dbconn);
        dbconn = DatabaseConnection.databaseInitialized(dbconn);
        String sql=null;
        sql="SELECT * FROM all_result WHERE staname=? AND parameter=? AND monthOryear=? ORDER BY `time` ASC";
        pstmt = dbconn.prepareStatement(sql);
        pstmt.setString(1,staname);
		pstmt.setString(2,parameter);
		pstmt.setString(3,monthOryear);
        result = pstmt.executeQuery();
        while(result.next()){
        	Result ta = new Result();
        	String time = result.getString("time");
        	Double average = result.getDouble("averageValue");
        	ta.setStaname(staname);
        	ta.setTime(time);
        	ta.setParameter(parameter);
        	ta.setMonthOryear(monthOryear);
        	ta.setAverage(average);
        	talist.add(ta);
            //String[] temp={result.getString("time"),result.getString("averageValue")};
            //list.add(temp);
        }
            endConn();
        //return list;
            return talist;
    }
	
	//各断面某段时间参数值
    //public static ArrayList<String[]> index() throws SQLException{
	public static ArrayList<Result> parameterValue(String staname,String parameter,String monthOryear) throws SQLException{
        //ArrayList<String[]> list = new ArrayList<String[]>();
        ArrayList<Result> talist = null;
        talist = new ArrayList<Result>();
        String parameterValue = null;
        switch(parameter) {
		case "temp": parameterValue = "water_temp";break;
		case "ph": parameterValue = "sta_ph_v";break;
		case "do": parameterValue = "sta_do_v";break;
		case "pp": parameterValue = "sta_pp_v";break;
		case "an": parameterValue = "sta_an_v";break;
		case "tp": parameterValue = "sta_tp_v";break;
		}
        dbconn = DatabaseConnection.databaseInitialized(dbconn);
        String sql=null;
        sql="SELECT * FROM all_data WHERE staname=? ORDER BY `sta_time` ASC";
        pstmt = dbconn.prepareStatement(sql);
        pstmt.setString(1,staname);
        result = pstmt.executeQuery();
        while(result.next()){
        	String readTime = result.getString("sta_time");
        	String readValue = result.getString(parameterValue);
        	if(readTime.length()>10 && Auxiliary.isStartWithNumber(readValue)) {
        		Result ta = new Result();
            	String time = readTime.substring(0,10);
            	//System.out.println(time);
            	Double average = Double.valueOf(readValue);
            	//System.out.println(average);
            	ta.setStaname(staname);
            	ta.setTime(time);
            	ta.setParameter(parameter);
            	ta.setMonthOryear(monthOryear);
            	ta.setAverage(average);
            	talist.add(ta);
                //String[] temp={result.getString("time"),result.getString("averageValue")};
                //list.add(temp);
        	}
        }
            endConn();
        //return list;
            return talist;
    }
}
