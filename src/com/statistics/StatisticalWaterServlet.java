package com.statistics;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.beans.Water;
import com.config.Auxiliary;
import com.config.DatabaseConnection;

/**
 * Servlet implementation class StatisticalWaterServlet
 */
@WebServlet("/statistical-water")
public class StatisticalWaterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatisticalWaterServlet() {
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

	
	//protected ArrayList<Result> readSQL(HttpServletRequest request, String staname,String parameter,String monthOrweek) {
	protected void readSQL(HttpServletRequest request, String staname,String parameter,String monthOryear,ArrayList<String> timelist) {
		ArrayList<Result> ta = null;
		String ParameterEng = null;
		String parameterChi = null;
		String monthOryearEng = null;
		String monthOryearChi = null;
		if(Auxiliary.isContainChinese(parameter)) {
			switch(parameter) {
			case "??????(???)": ParameterEng = "temp";break;
			case "pH(?????????)": ParameterEng = "ph";break;
			case "?????????(mg/L)": ParameterEng = "do";break;
			case "??????????????????(mg/L)": ParameterEng = "pp";break;
			case "??????(mg/L)": ParameterEng = "an";break;
			case "??????(mg/L)": ParameterEng = "tp";break;
			}
			parameterChi = parameter;
		}
		else {
			switch(parameter) {
			case "temp": parameterChi = "??????(???)";break;
			case "ph": parameterChi = "pH(?????????)";break;
			case "do": parameterChi = "?????????(mg/L)";break;
			case "pp": parameterChi = "??????????????????(mg/L)";break;
			case "an": parameterChi = "??????(mg/L)";break;
			case "tp": parameterChi = "??????(mg/L)";break;
			}
			ParameterEng = parameter;
		}
		if(Auxiliary.isContainChinese(monthOryear)) {
			if(monthOryear.equals("?????????"))
				monthOryearEng = "y";
			else if(monthOryear.equals("?????????"))
				monthOryearEng = "d";
			else
				monthOryearEng = "m";
			monthOryearChi = monthOryear;
		}
		else {
			if(monthOryear.equals("y"))
				monthOryearChi = "?????????";
			else if(monthOryear.equals("d"))
				monthOryearEng = "?????????";
			else
				monthOryearChi = "?????????";
			monthOryearEng = monthOryear;
		}
		System.out.println(monthOryearEng);
		try {
			if(monthOryearEng != "d")
				ta = StatisticalResults.averageValue(staname,ParameterEng,monthOryearEng);
			else {
				ta = StatisticalResults.parameterValue(staname, ParameterEng, monthOryearEng);
			}
			ArrayList<Result> tapart = new ArrayList<Result>();
			for(Result r:ta) {
				String savedtime = r.getTime();
				 for(String time:timelist) {
					if(savedtime.equals(time)) {
						tapart.add(r);
					}
				}
			}
			Double maxaver = (double) 0;
			Double minaver = (double) 100;
			if(!tapart.isEmpty()) {
				//String maxtime = null;
				//String mintime = null;
				  for (Result i : tapart) {
					  Double aver = i.getAverage();
					  //String avertime = i.getTime();
					  if(aver >= maxaver) {
						  maxaver = aver;
						  //maxtime = avertime;
					  }
					  if(aver <= minaver) {
						  minaver = aver;
						  //mintime = avertime;
					  }
		            }
				  Double differenceTmp = maxaver - minaver;
				 String difference = String.format("%.2f", differenceTmp).toString();
				 request.getSession().setAttribute("maxaver",maxaver);
				 request.getSession().setAttribute("minaver",minaver);
				 //request.getSession().setAttribute("maxtime",maxtime);
				 //request.getSession().setAttribute("mintime",mintime);
				 request.getSession().setAttribute("difference",difference);
				request.getSession().setAttribute("defaultMean",tapart);
				//System.out.println(ta.get(0).getTime());
				//System.out.println(ta.get(0).getTime());
			}
			else {
				if(!ta.isEmpty()) {
					  for (Result i : ta) {
						  Double aver = i.getAverage();
						  if(aver >= maxaver) {
							  maxaver = aver;
						  }
						  if(aver <= minaver) {
							  minaver = aver;
						  }
			            }
					  Double differenceTmp = maxaver - minaver;
					 String difference = String.format("%.2f", differenceTmp).toString();
					 request.getSession().setAttribute("maxaver",maxaver);
					 request.getSession().setAttribute("minaver",minaver);
					 request.getSession().setAttribute("difference",difference);
					request.getSession().setAttribute("defaultMean",ta);
					//System.out.println(ta.get(0).getTime());
				}		
			}
			request.getSession().setAttribute("staname",staname);
			request.getSession().setAttribute("parameter",parameterChi);
			request.getSession().setAttribute("monthOryear",monthOryearChi);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return ta;
    }
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> plist = DatabaseConnection.getProvinceList();
		ArrayList<ProAndSta> pslist = DatabaseConnection.getProAndStaList();
		if(!plist.isEmpty()) {
			request.getSession().setAttribute("provinceList",plist);
		}
		String staname = "?????????";
		String parameter = "temp";
		String monthOrweek = "m";
		String statime = "2021-01";
		String endtime = "2021-12";
		ArrayList<String> timelist = Auxiliary.getMonths(statime, endtime);
		if(!pslist.isEmpty()) {
			request.getSession().setAttribute("psList",pslist);
			readSQL(request, staname, parameter, monthOrweek,timelist);
			//request.getSession().setAttribute("defaultMean",ta);
			response.sendRedirect("statistics.jsp");
		}else {
			response.sendRedirect("statistics.jsp");
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String Staname =new String(request.getParameter("Staname").getBytes("iso-8859-1"),"utf-8");
		String Parametername =new String(request.getParameter("Parametername").getBytes("iso-8859-1"),"utf-8");
		String Meanvalue =new String(request.getParameter("Meanvalue").getBytes("iso-8859-1"),"utf-8");
		String Statime =new String(request.getParameter("Statime").getBytes("iso-8859-1"),"utf-8");
		String Endtime =new String(request.getParameter("Endtime").getBytes("iso-8859-1"),"utf-8");
		Date s,e;
		if(Staname.equals("0"))
			Staname = "?????????";
		try {
			s = new SimpleDateFormat("yyyy-MM-dd").parse(Statime);
			e = new SimpleDateFormat("yyyy-MM-dd").parse(Endtime);
			if(e.before(s)) {
	        	System.out.println("?????????????????????????????????");
	        }
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String y1,y2;
		ArrayList<String> timelist = new ArrayList<String>();
		if(Meanvalue.equals("?????????")) {
			y1 = Statime.substring(0,4);
			y2 = Endtime.substring(0,4);
			timelist = Auxiliary.getYears(y1, y2);
		}
		else if(Meanvalue.equals("?????????")) {
			y1 = Statime;
			y2 = Endtime;
			timelist = Auxiliary.getDays(y1, y2);
			/*for(String time:timelist) {
				System.out.println(time);
			}*/
		}
		else {
			y1 = Statime.substring(0,7);
			y2 = Endtime.substring(0,7);
			timelist = Auxiliary.getMonths(y1, y2);
		}
		readSQL(request, Staname, Parametername, Meanvalue,timelist);
		response.sendRedirect("statistics.jsp");
		
	}

}
