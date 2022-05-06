package com.survey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.beans.ProAndPolPro;
import com.beans.ProAndStaNum;
import com.beans.Result;
import com.beans.TypeAndNum;
import com.config.DatabaseConnection;

public class CollectionResults {
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
	//页面上层数据
    //public static ArrayList<String[]> index() throws SQLException{
	public static String survey(int flag) throws SQLException{
        //ArrayList<String[]> list = new ArrayList<String[]>();
        dbconn = DatabaseConnection.databaseInitialized(dbconn);
        String sql=null;
        String quantity = null;
        switch(flag) {
        //总采集数据量
        case 0:{
        	int count = 0;
        	sql="SELECT count(*) FROM all_data";
        	pstmt = dbconn.prepareStatement(sql);
        	result = pstmt.executeQuery();
        	result.next();
        	count = result.getInt(1);
        	quantity = String.valueOf(count);
        	break;
        	}
        //当前采集数据量
        case 1:{
        	int count = 0;
        	sql="SELECT count(*) FROM current_data";
        	pstmt = dbconn.prepareStatement(sql);
        	result = pstmt.executeQuery();
        	result.next();
        	count = result.getInt(1);
        	quantity = String.valueOf(count);
        	break;
        	}
        //当前正常站点数
        case 2:{
        	int count = 0;
        	sql="SELECT count(*) FROM current_data WHERE 站点情况=?";
        	pstmt = dbconn.prepareStatement(sql);
        	pstmt.setString(1,"正常");
        	result = pstmt.executeQuery();
        	result.next();
        	count = result.getInt(1);
        	quantity = String.valueOf(count);
        	break;
        	}
        //当前优质水质占比
        case 3:{
        	double count_high = 0;
        	//double count_poor = 0;
        	sql="SELECT count(*) FROM current_data WHERE 水质类别 in (?,?,?)";
        	pstmt = dbconn.prepareStatement(sql);
        	pstmt.setString(1,"Ⅰ");
        	pstmt.setString(2,"Ⅱ");
        	pstmt.setString(3,"Ⅲ");
        	result = pstmt.executeQuery();
        	result.next();
        	count_high = result.getDouble(1);
        	String total = survey(1);
        	double count = Double.valueOf(total);
        	double proportion = count_high / count;
        	DecimalFormat df = new DecimalFormat("0.00");
        	quantity = df.format(proportion*100);
        	break;
        	}
        //当前劣质水质占比
        case 4:{
        	double count_poor = 0;
        	sql="SELECT count(*) FROM current_data WHERE 水质类别 in (?,?,?)";
        	pstmt = dbconn.prepareStatement(sql);
        	pstmt.setString(1,"Ⅳ");
        	pstmt.setString(2,"Ⅴ");
        	pstmt.setString(3,"劣Ⅴ");
        	result = pstmt.executeQuery();
        	result.next();
        	count_poor = result.getDouble(1);
        	String total = survey(1);
        	double count = Double.valueOf(total);
        	double proportion = count_poor / count;
        	DecimalFormat df = new DecimalFormat("0.00");
        	quantity = df.format(proportion*100);
        	break;
        	}
      }
      endConn();
      return quantity;
    }
	//地图数据
    //public static ArrayList<String[]> index() throws SQLException{
	public static ArrayList<ProAndStaNum> mapping() throws SQLException{
        //ArrayList<String[]> list = new ArrayList<String[]>();
        dbconn = DatabaseConnection.databaseInitialized(dbconn);
        ArrayList<ProAndStaNum> psnArr = new ArrayList<ProAndStaNum>();
        String sql=null;
        sql="SELECT 省份,count(省份) FROM current_data GROUP BY 省份";
        pstmt = dbconn.prepareStatement(sql);
        result = pstmt.executeQuery();
        while(result.next()) {
        	ProAndStaNum psn = new ProAndStaNum();
        	String completePro = result.getString(1);
        	String proName = null;
        	if(completePro.contains("黑龙江") || completePro.contains("内蒙古")) {
        		proName = completePro.substring(0,3);
        	}
        	else {
        		proName = completePro.substring(0,2);
        	}
        	psn.setProvince(proName);
        	psn.setStanum(result.getInt(2));
        	//System.out.println(psn.getProvince());
        	//System.out.println(psn.getStanum());
        	psnArr.add(psn);
		}
      endConn();
      return psnArr;
    }
	//水质概况
    //public static ArrayList<String[]> index() throws SQLException{
	public static ArrayList<TypeAndNum> waterQualityGrade() throws SQLException{
        //ArrayList<String[]> list = new ArrayList<String[]>();
        dbconn = DatabaseConnection.databaseInitialized(dbconn);
        ArrayList<TypeAndNum> tnArr = new ArrayList<TypeAndNum>();
        String sql=null;
        sql="SELECT 水质类别,count(水质类别) FROM current_data GROUP BY 水质类别";
        pstmt = dbconn.prepareStatement(sql);
        result = pstmt.executeQuery();
        while(result.next()) {
        	TypeAndNum tn = new TypeAndNum();
        	String waterType = result.getString(1);
        	String waterRank = null;
        	if(waterType.contains("Ⅰ")||waterType.contains("Ⅱ")||waterType.contains("Ⅲ")||waterType.contains("Ⅳ")||waterType.contains("Ⅴ")||waterType.contains("劣Ⅴ")) {
        		waterRank = waterType + "级";
        	}
        	else {
        		waterRank = "未知";
        	}
        	tn.setType(waterRank);
    		tn.setNum(result.getInt(2));
        	//System.out.println(psn.getProvince());
        	//System.out.println(psn.getStanum());
    		tnArr.add(tn);
		}
      endConn();
      return tnArr;
    }
	//污染概况
    //public static ArrayList<String[]> index() throws SQLException{
	public static ArrayList<ProAndPolPro> waterQualityPollution() throws SQLException{
        //ArrayList<String[]> list = new ArrayList<String[]>();
        dbconn = DatabaseConnection.databaseInitialized(dbconn);
        ArrayList<ProAndPolPro> pppArr = new ArrayList<ProAndPolPro>();
        String sql=null;
        sql="SELECT 省份,count(1),SUM(CASE WHEN 水质类别 IN (?,?,?) THEN 1 ELSE 0 END) FROM current_data GROUP BY 省份";
        pstmt = dbconn.prepareStatement(sql);
        pstmt.setString(1,"Ⅳ");
		pstmt.setString(2,"Ⅴ");
		pstmt.setString(3,"劣Ⅴ");
        result = pstmt.executeQuery();
        while(result.next()) {
        	ProAndPolPro ppp = new ProAndPolPro();
        	String completePro = result.getString(1);
        	String proName = null;
        	if(completePro.contains("黑龙江") || completePro.contains("内蒙古")) {
        		proName = completePro.substring(0,3);
        	}
        	else {
        		proName = completePro.substring(0,2);
        	}
        	double totalWaterQuality = result.getDouble(2);
        	double poorWaterQuality = result.getDouble(3);
        	double polProTmp = poorWaterQuality / totalWaterQuality;
        	String polPro = null;
        	DecimalFormat df = new DecimalFormat("0.00");
        	polPro = df.format(polProTmp*100);
        	ppp.setProvince(proName);
        	ppp.setPolpro(Double.valueOf(polPro));
    		pppArr.add(ppp);
		}
      endConn();
      return pppArr;
    }
}
