package com.browsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.ProAndSta;
import com.beans.Water;
import com.config.DatabaseConnection;
import com.config.PythonProcessing;

/**
 * Servlet implementation class SearchWaterServlet
 */
@WebServlet("/search-water")
public class SearchWaterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchWaterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    Connection dbconn = null;
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		dbconn = DatabaseConnection.databaseInitialized(dbconn);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try {
	         dbconn.close();
	      }catch(Exception e){
		     e.printStackTrace();
	      }
	}
	
	protected ArrayList<Water> readSQL() {
		ArrayList<Water> waterList = null;
		waterList = new ArrayList<Water>();
		try {
			String sql=null;
			PreparedStatement pstmt = null;
			sql="SELECT * FROM current_data";
			pstmt = dbconn.prepareStatement(sql);
			//pstmt = dbconn.prepareStatement(sql);
			//pstmt.setString(1,Spetime);
			//pstmt.setString(2,Proname);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				 Water water =new Water();
				 water.setProvince(result.getString("省份"));
				 water.setValley(result.getString("流域"));
				 water.setStaname(result.getString("断面名称"));
				 water.setSta_time(result.getString("监测时间"));
				 water.setWater_l(result.getString("水质类别"));
				 water.setWater_temp(result.getString("水温(℃)"));
				 
				 water.setSta_ph_v(result.getString("pH(无量纲)"));
				 water.setSta_do_v(result.getString("溶解氧(mg/L)"));
				 water.setConductivity(result.getString("电导率(μS/cm)"));
				 water.setTurbidity(result.getString("浊度(NTU)"));
				 water.setSta_pp_v(result.getString("高锰酸盐指数(mg/L)"));
				 
				 water.setSta_an_v(result.getString("氨氮(mg/L)"));
				 water.setSta_tp_v(result.getString("总磷(mg/L)"));
				 water.setSta_tn_v(result.getString("总氮(mg/L)"));
				 water.setChlorophyll(result.getString("叶绿素α(mg/L)"));
				 water.setAlgal_density(result.getString("藻密度(cells/L)"));
				 
				 water.setStatus_label(result.getString("站点情况"));
				 
				 waterList.add(water);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return waterList;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		ArrayList<Water> waterList = null;
		waterList = new ArrayList<Water>();
		request.setCharacterEncoding("GBK");
		Calendar cal=Calendar.getInstance();
		int h=cal.get(Calendar.HOUR_OF_DAY);
		SimpleDateFormat dateFormat= new SimpleDateFormat("MM-dd");
		String current_day = dateFormat.format(cal.getTime());
		Boolean flag = false;	
		ArrayList<String> plist = new ArrayList<String>();
		try {
			String sql=null;
			PreparedStatement pstmt = null;
			sql="SELECT 监测时间 FROM current_data";
			pstmt = dbconn.prepareStatement(sql);
			//pstmt = dbconn.prepareStatement(sql);
			//pstmt.setString(1,Spetime);
			//pstmt.setString(2,Proname);
			ResultSet result = pstmt.executeQuery();
			if(result.next()) {
				String saved_time = result.getString("监测时间");
				System.out.println(saved_time);
				if(saved_time.contains(current_day)) {
					System.out.println("包含当前天");
					for(int i=0;i<=4;i++) {
						int j = h - i;
						String c = j + ":00";
						if(saved_time.contains(c)) {
							System.out.println("包含当前时间");
							System.out.println(c);
							flag = true;
						}
					}
				}	 
			}
			plist = DatabaseConnection.getProvinceList();
			if(!plist.isEmpty()) {
				request.getSession().setAttribute("provinceList",plist);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(flag) {
			waterList = readSQL();
			if(!waterList.isEmpty()) {
					request.getSession().setAttribute("waterList",waterList);
			        response.sendRedirect("browsing.jsp");
				}else {
					response.sendRedirect("browsing.jsp");
				}
		}
		else {
			ArrayList<String> list = new ArrayList<String>();
			list = PythonProcessing.handle(0,null);
			if(list.isEmpty()) {
				System.out.println("链表为空");
				waterList = readSQL();
				if(!waterList.isEmpty()) {
						request.getSession().setAttribute("waterList",waterList);
				        response.sendRedirect("browsing.jsp");
					}else {
						response.sendRedirect("browsing.jsp");
					}
			}
			else {
				waterList = readSQL();
				if(!waterList.isEmpty()) {
						request.getSession().setAttribute("waterList",waterList);
				        response.sendRedirect("browsing.jsp");
					}else {
						response.sendRedirect("browsing.jsp");
					}		
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		ArrayList<Water> waterList = null;
		waterList = new ArrayList<Water>();
		String Proname =new String(request.getParameter("Proname").getBytes("iso-8859-1"),"utf-8");
		String Spetime =new String(request.getParameter("Spetime"));
		//System.out.println(Proname);
		//System.out.println(Spetime);
		Spetime = Spetime + " 12:00:00";
		boolean status = Spetime.contains("2021");
		boolean flag = Proname.contains("全国");
		try {
			String sql=null;
			PreparedStatement pstmt = null;
			if(status) {
				sql="SELECT * FROM all_data WHERE sta_time=?";
				pstmt = dbconn.prepareStatement(sql);
				pstmt.setString(1,Spetime);
			}
			else {
				if(flag) {
					sql="SELECT * FROM all_data WHERE sta_time=?";
					pstmt = dbconn.prepareStatement(sql);
					pstmt.setString(1,Spetime);
				}
				else {
					sql="SELECT * FROM all_data WHERE sta_time=? and province=?";
					pstmt = dbconn.prepareStatement(sql);
					pstmt.setString(1,Spetime);
					pstmt.setString(2,Proname);
				}
			}
			//pstmt = dbconn.prepareStatement(sql);
			//pstmt.setString(1,Spetime);
			//pstmt.setString(2,Proname);
			ResultSet result = pstmt.executeQuery();
			while(result.next()) {
				 Water water =new Water();
				 water.setProvince(result.getString("province"));
				 water.setValley(result.getString("valley"));
				 water.setStaname(result.getString("staname"));
				 water.setSta_time(result.getString("sta_time"));
				 water.setWater_l(result.getString("water_l"));
				 water.setWater_temp(result.getString("water_temp"));
				 
				 water.setSta_ph_v(result.getString("sta_ph_v"));
				 water.setSta_do_v(result.getString("sta_do_v"));
				 water.setConductivity(result.getString("conductivity"));
				 water.setTurbidity(result.getString("turbidity"));
				 water.setSta_pp_v(result.getString("sta_pp_v"));
				 
				 water.setSta_an_v(result.getString("sta_an_v"));
				 water.setSta_tp_v(result.getString("sta_tp_v"));
				 water.setSta_tn_v(result.getString("sta_tn_v"));
				 water.setChlorophyll(result.getString("chlorophyll"));
				 water.setAlgal_density(result.getString("algal_density"));
				 
				 water.setStatus_label(result.getString("status_label"));
				 
				 waterList.add(water);
			}
			if(!waterList.isEmpty()) {
				request.getSession().setAttribute("waterList",waterList);
		        response.sendRedirect("browsing.jsp");
			}else {
				response.sendRedirect("browsing.jsp");
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
