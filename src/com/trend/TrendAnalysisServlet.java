package com.trend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.ProAndSta;
import com.beans.Result;
import com.config.Auxiliary;
import com.config.DatabaseConnection;
import com.config.PythonProcessing;

/**
 * Servlet implementation class TrendAnalysisServlet
 */
@WebServlet("/predicted-water")
public class TrendAnalysisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrendAnalysisServlet() {
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
	
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		ArrayList<String> plist = DatabaseConnection.getProvinceList();
		ArrayList<ProAndSta> pslist = DatabaseConnection.getProAndStaList();
		if(!plist.isEmpty()) {
			request.getSession().setAttribute("provinceList",plist);
		}
		if(!pslist.isEmpty()) {
			request.getSession().setAttribute("psList",pslist);
		}
		ArrayList<String> list = new ArrayList<String>();
		String staname = "桔子洲";
		String parameter = "water_temp";
		String num = "20";
		String[] para = {""};
		para = new String[] {staname, parameter, num, num};
		list = PythonProcessing.handle(2,para);
		ArrayList<Result> talist = new ArrayList<Result>();
		if(list.isEmpty()) {
			System.out.println("链表为空");
		}
		else {
			for(String str:list) {
				//System.out.println("测试输出："+str);
				if(Auxiliary.isStartWithNumber(str)) {
					Result ta = new Result();
					int i = str.indexOf(" ", 0);
					//System.out.println(i);
					String time = str.substring(i+1,14);
					Double average = Double.parseDouble(str.substring(25));
					ta.setStaname(staname);
			    	ta.setTime(time);
			    	ta.setParameter(parameter);
			    	ta.setMonthOryear(null);
			    	ta.setAverage(average);
			    	talist.add(ta);
					//System.out.println(data);
				}
			}
			String parameterChi = "水温(℃)";
			request.getSession().setAttribute("stanameT",staname);
			request.getSession().setAttribute("parameterT",parameterChi);
			request.getSession().setAttribute("predictionMean",talist);
		}
		response.sendRedirect("trend.jsp");	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String Staname =new String(request.getParameter("StanameT").getBytes("iso-8859-1"),"utf-8");
		String parameter =new String(request.getParameter("ParameternameT").getBytes("iso-8859-1"),"utf-8");
		String Statime =new String(request.getParameter("StatimeT").getBytes("iso-8859-1"),"utf-8");
		String Endtime =new String(request.getParameter("EndtimeT").getBytes("iso-8859-1"),"utf-8");
		String ForecastStartTime = "2022-05-01";
		String ParameterEng = null;
		String parameterChi = null;
		ArrayList<String> list = new ArrayList<String>();
		System.out.println(Staname);
		if(Staname.equals("0"))
			Staname = "桔子洲";
		if(Auxiliary.isContainChinese(parameter)) {
			switch(parameter) {
			case "水温(℃)": ParameterEng = "water_temp";break;
			case "pH(无量纲)": ParameterEng = "sta_ph_v";break;
			case "溶解氧(mg/L)": ParameterEng = "sta_do_v";break;
			case "高锰酸盐指数(mg/L)": ParameterEng = "sta_pp_v";break;
			case "氨氮(mg/L)": ParameterEng = "sta_an_v";break;
			case "总磷(mg/L)": ParameterEng = "sta_tp_v";break;
			}
			parameterChi = parameter;
		}
		else {
			switch(parameter) {
			case "water_temp": parameterChi = "水温(℃)";break;
			case "sta_ph_v": parameterChi = "pH(无量纲)";break;
			case "sta_do_v": parameterChi = "溶解氧(mg/L)";break;
			case "sta_pp_v": parameterChi = "高锰酸盐指数(mg/L)";break;
			case "sta_an_v": parameterChi = "氨氮(mg/L)";break;
			case "sta_tp_v": parameterChi = "总磷(mg/L)";break;
			}
			ParameterEng = parameter;
		}
		try {
			int num = Auxiliary.daysBetween(ForecastStartTime,Endtime)+1;
			System.out.println(num);
			int showNum = Auxiliary.daysBetween(Statime,Endtime)+1;
			System.out.println(showNum);
			String[] para = {""};
			para = new String[] {Staname, ParameterEng, Integer.toString(num), Integer.toString(showNum)};
			list = PythonProcessing.handle(2,para);
			ArrayList<Result> talist = new ArrayList<Result>();
			if(list.isEmpty()) {
				System.out.println("链表为空");
			}
			else {
				for(String str:list) {
					//System.out.println("测试输出："+str);
					if(Auxiliary.isStartWithNumber(str)) {
						Result ta = new Result();
						int i = str.indexOf(" ", 0);
						//System.out.println(i);
						String time = str.substring(i+1,14);
						Double average = Double.parseDouble(str.substring(25));
						ta.setStaname(Staname);
				    	ta.setTime(time);
				    	ta.setParameter(ParameterEng);
				    	ta.setMonthOryear(null);
				    	ta.setAverage(average);
				    	talist.add(ta);
						//System.out.println(data);
					}
				}
				request.getSession().setAttribute("stanameT",Staname);
				request.getSession().setAttribute("parameterT",parameterChi);
				request.getSession().setAttribute("predictionMean",talist);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("trend.jsp");
	}
}
