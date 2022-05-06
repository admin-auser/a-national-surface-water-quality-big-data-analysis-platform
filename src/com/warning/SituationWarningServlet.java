package com.warning;

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
import com.statistics.StatisticalResults;

/**
 * Servlet implementation class TrendAnalysisServlet
 */
@WebServlet("/alarm-water")
public class SituationWarningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SituationWarningServlet() {
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
		String staname = "桔子洲";
		String parameter = "tp";
		String statime = "2021-01-01";
		String endtime = "2021-01-31";
		ArrayList<String> timelist = Auxiliary.getDays(statime, endtime);
		if(!pslist.isEmpty()) {
			request.getSession().setAttribute("psList",pslist);
			//readSQL(request, staname, parameter, monthOrweek,timelist);
			ArrayList<Result> ta = null;
			String border = null;
			String first = null;
			String second = null;
			String third = null;
			String fourth = null;
			String fifth = null;
			switch(parameter) {
			case "ph": border="6";first="9";second="9";third="9";fourth="9";fifth="9";break;
			case "do": border="15";first="7.5";second="6";third="5";fourth="3";fifth="2";break;
			case "pp": border="0";first="2";second="4";third="6";fourth="10";fifth="15";break;
			case "an": border="0";first="0.15";second="0.5";third="1.0";fourth="1.5";fifth="2.0";break;
			case "tp": border="0";first="0.02";second="0.1";third="0.2";fourth="0.3";fifth="0.4";break;
			}
			request.getSession().setAttribute("border",border);
			request.getSession().setAttribute("first",first);
			request.getSession().setAttribute("second",second);
			request.getSession().setAttribute("third",third);
			request.getSession().setAttribute("fourth",fourth);
			request.getSession().setAttribute("fifth",fifth);
			try {
				ta = StatisticalResults.parameterValue(staname, parameter, "d");
				ArrayList<Result> tapart = new ArrayList<Result>();
				for(Result r:ta) {
					String savedtime = r.getTime();
					 for(String time:timelist) {
						if(savedtime.equals(time)) {
							tapart.add(r);
						}
					}
				}
				int size = 6;
				int[] num = new int[size];
				ArrayList<Result> ta0 = new ArrayList<Result>();
				ArrayList<Result> ta1 = new ArrayList<Result>();
				ArrayList<Result> ta2 = new ArrayList<Result>();
				ArrayList<Result> ta3 = new ArrayList<Result>();
				ArrayList<Result> ta4 = new ArrayList<Result>();
				ArrayList<Result> ta5 = new ArrayList<Result>();
				if(!tapart.isEmpty()) {
					for(Result r:tapart) {
						switch(r.getParameter()) {
						case "ph": 
							if(r.getAverage()>=6 && r.getAverage()<=9) {
								num[1]++;
								ta1.add(r);
							}
							else {
								num[0]++;
								ta0.add(r);
							}
							break;
						case "do": 
							if(r.getAverage()>=7.5) {
								num[1]++;
								ta1.add(r);
							}
							else if(r.getAverage()>=6) {
								num[2]++;
								ta2.add(r);
							}
							else if(r.getAverage()>=5) {
								num[3]++;
								ta3.add(r);
							}
							else if(r.getAverage()>=3) {
								num[4]++;
								ta4.add(r);
							}
							else if(r.getAverage()>=2) {
								num[5]++;
								ta5.add(r);
							}
							else {
								num[0]++;
								ta0.add(r);
							}
							break;
						case "pp": 
							if(r.getAverage()<=2) {
								num[1]++;
								ta1.add(r);
							}
							else if(r.getAverage()<=4) {
								num[2]++;
								ta2.add(r);
							}
							else if(r.getAverage()<=6) {
								num[3]++;
								ta3.add(r);
							}
							else if(r.getAverage()<=10) {
								num[4]++;
								ta4.add(r);
							}
							else if(r.getAverage()<=15) {
								num[5]++;
								ta5.add(r);
							}
							else {
								num[0]++;
								ta0.add(r);
							}
							break;
						case "an":
							if(r.getAverage()<=0.15) {
								num[1]++;
								ta1.add(r);
							}
							else if(r.getAverage()<=0.5) {
								num[2]++;
								ta2.add(r);
							}
							else if(r.getAverage()<=1.0) {
								num[3]++;
								ta3.add(r);
							}
							else if(r.getAverage()<=1.5) {
								num[4]++;
								ta4.add(r);
							}
							else if(r.getAverage()<=2.0) {
								num[5]++;
								ta5.add(r);
							}
							else {
								num[0]++;
								ta0.add(r);
							}
							break;
						case "tp": 
							if(r.getAverage()<=0.02) {
								num[1]++;
								ta1.add(r);
							}
							else if(r.getAverage()<=0.1) {
								num[2]++;
								ta2.add(r);
							}
							else if(r.getAverage()<=0.2) {
								num[3]++;
								ta3.add(r);
							}
							else if(r.getAverage()<=0.3) {
								num[4]++;
								ta4.add(r);
							}
							else if(r.getAverage()<=0.4) {
								num[5]++;
								ta5.add(r);
							}
							else {
								num[0]++;
								ta0.add(r);
							}
							break;
						}
					}
					request.getSession().setAttribute("defaultMeanW",tapart);
					int highnum = num[1] + num[2] + num[3];
					int poornum = num[4] + num[5];
					String msg = "优质水类（Ⅰ类、Ⅱ类、Ⅲ类）所占天数最多，因此该时间段防治污染情况总体良好";
					String flag = "不";
					if(highnum >= poornum) {
						msg = "优质水类（Ⅰ类、Ⅱ类、Ⅲ类）所占天数最多，因此该时间段防治污染情况总体良好";
						flag = "不";
					}
					else {
						msg = "劣质水类（Ⅳ类、Ⅴ类）所占天数最多，因此该时间段防治污染情况总体较差";
						flag = "";
					}
					request.getSession().setAttribute("msg",msg);
					request.getSession().setAttribute("flag",flag);
				}
				else {
					if(!ta.isEmpty()) {
						request.getSession().setAttribute("defaultMeanW",ta);
					}		
				}
				request.getSession().setAttribute("abnormalnum",num[0]);
				request.getSession().setAttribute("firstnum",num[1]);
				request.getSession().setAttribute("secondnum",num[2]);
				request.getSession().setAttribute("thirdnum",num[3]);
				request.getSession().setAttribute("fourthnum",num[4]);
				request.getSession().setAttribute("fifthnum",num[5]);
				request.getSession().setAttribute("abnormaltime",ta0);
				request.getSession().setAttribute("firsttime",ta1);
				request.getSession().setAttribute("secondtime",ta2);
				request.getSession().setAttribute("thirdtime",ta3);
				request.getSession().setAttribute("fourthtime",ta4);
				request.getSession().setAttribute("fifthtime",ta5);
				String proname = "湖南省";
				request.getSession().setAttribute("pronameW",proname);
				request.getSession().setAttribute("stanameW",staname);
				request.getSession().setAttribute("parameterW","总磷(mg/L)");
				request.getSession().setAttribute("statimeW",statime);
				request.getSession().setAttribute("endtimeW",endtime);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("warning.jsp");
		}else {
			response.sendRedirect("warning.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String Proname =new String(request.getParameter("PronameW").getBytes("iso-8859-1"),"utf-8");
		String Staname =new String(request.getParameter("StanameW").getBytes("iso-8859-1"),"utf-8");
		String parameter =new String(request.getParameter("ParameternameW").getBytes("iso-8859-1"),"utf-8");
		String Statime =new String(request.getParameter("StatimeW").getBytes("iso-8859-1"),"utf-8");
		String Endtime =new String(request.getParameter("EndtimeW").getBytes("iso-8859-1"),"utf-8");
		String ParameterEng = null;
		String parameterChi = null;
		String border = null;
		String first = null;
		String second = null;
		String third = null;
		String fourth = null;
		String fifth = null;
		if(Proname.equals("0"))
			Proname = "湖南省";
		if(Staname.equals("0"))
			Staname = "桔子洲";
		if(Auxiliary.isContainChinese(parameter)) {
			switch(parameter) {
			case "pH(无量纲)": ParameterEng = "ph";border="6";first="9";second="9";third="9";fourth="9";fifth="9";break;
			case "溶解氧(mg/L)": ParameterEng = "do";border="15";first="7.5";second="6";third="5";fourth="3";fifth="2";break;
			case "高锰酸盐指数(mg/L)": ParameterEng = "pp";border="0";first="2";second="4";third="6";fourth="10";fifth="15";break;
			case "氨氮(mg/L)": ParameterEng = "an";border="0";first="0.15";second="0.5";third="1.0";fourth="1.5";fifth="2.0";break;
			case "总磷(mg/L)": ParameterEng = "tp";border="0";first="0.02";second="0.1";third="0.2";fourth="0.3";fifth="0.4";break;
			}
			parameterChi = parameter;
		}
		else {
			switch(parameter) {
			case "ph": parameterChi = "pH(无量纲)";border="6";first="9";second="9";third="9";fourth="9";fifth="9";break;
			case "do": parameterChi = "溶解氧(mg/L)";border="15";first="7.5";second="6";third="5";fourth="3";fifth="2";break;
			case "pp": parameterChi = "高锰酸盐指数(mg/L)";border="0";first="2";second="4";third="6";fourth="10";fifth="15";break;
			case "an": parameterChi = "氨氮(mg/L)";border="0";first="0.15";second="0.5";third="1.0";fourth="1.5";fifth="2.0";break;
			case "tp": parameterChi = "总磷(mg/L)";border="0";first="0.02";second="0.1";third="0.2";fourth="0.3";fifth="0.4";break;
			}
			ParameterEng = parameter;
		}
		ArrayList<String> timelist = Auxiliary.getDays(Statime, Endtime);
		ArrayList<Result> ta = null;
		try {
			ta = StatisticalResults.parameterValue(Staname, ParameterEng, "d");
			ArrayList<Result> tapart = new ArrayList<Result>();
			for(Result r:ta) {
				String savedtime = r.getTime();
				 for(String time:timelist) {
					if(savedtime.equals(time)) {
						tapart.add(r);
					}
				}
			}
			int size = 6;
			int[] num = new int[size];
			ArrayList<Result> ta0 = new ArrayList<Result>();
			ArrayList<Result> ta1 = new ArrayList<Result>();
			ArrayList<Result> ta2 = new ArrayList<Result>();
			ArrayList<Result> ta3 = new ArrayList<Result>();
			ArrayList<Result> ta4 = new ArrayList<Result>();
			ArrayList<Result> ta5 = new ArrayList<Result>();
			if(!tapart.isEmpty()) {
				for(Result r:tapart) {
					switch(r.getParameter()) {
					case "ph": 
						if(r.getAverage()>=6 && r.getAverage()<=9) {
							num[1]++;
							ta1.add(r);
						}
						else {
							num[0]++;
							ta0.add(r);
						}
						break;
					case "do": 
						if(r.getAverage()>=7.5) {
							num[1]++;
							ta1.add(r);
						}
						else if(r.getAverage()>=6) {
							num[2]++;
							ta2.add(r);
						}
						else if(r.getAverage()>=5) {
							num[3]++;
							ta3.add(r);
						}
						else if(r.getAverage()>=3) {
							num[4]++;
							ta4.add(r);
						}
						else if(r.getAverage()>=2) {
							num[5]++;
							ta5.add(r);
						}
						else {
							num[0]++;
							ta0.add(r);
						}
						break;
					case "pp": 
						if(r.getAverage()<=2) {
							num[1]++;
							ta1.add(r);
						}
						else if(r.getAverage()<=4) {
							num[2]++;
							ta2.add(r);
						}
						else if(r.getAverage()<=6) {
							num[3]++;
							ta3.add(r);
						}
						else if(r.getAverage()<=10) {
							num[4]++;
							ta4.add(r);
						}
						else if(r.getAverage()<=15) {
							num[5]++;
							ta5.add(r);
						}
						else {
							num[0]++;
							ta0.add(r);
						}
						break;
					case "an":
						if(r.getAverage()<=0.15) {
							num[1]++;
							ta1.add(r);
						}
						else if(r.getAverage()<=0.5) {
							num[2]++;
							ta2.add(r);
						}
						else if(r.getAverage()<=1.0) {
							num[3]++;
							ta3.add(r);
						}
						else if(r.getAverage()<=1.5) {
							num[4]++;
							ta4.add(r);
						}
						else if(r.getAverage()<=2.0) {
							num[5]++;
							ta5.add(r);
						}
						else {
							num[0]++;
							ta0.add(r);
						}
						break;
					case "tp": 
						if(r.getAverage()<=0.02) {
							num[1]++;
							ta1.add(r);
						}
						else if(r.getAverage()<=0.1) {
							num[2]++;
							ta2.add(r);
						}
						else if(r.getAverage()<=0.2) {
							num[3]++;
							ta3.add(r);
						}
						else if(r.getAverage()<=0.3) {
							num[4]++;
							ta4.add(r);
						}
						else if(r.getAverage()<=0.4) {
							num[5]++;
							ta5.add(r);
						}
						else {
							num[0]++;
							ta0.add(r);
						}
						break;
					}
				}
				request.getSession().setAttribute("defaultMeanW",tapart);
				int highnum = num[1] + num[2] + num[3];
				int poornum = num[4] + num[5];
				String msg = "优质水类（Ⅰ类、Ⅱ类、Ⅲ类）所占天数最多，因此该时间段防治污染情况总体良好";
				String flag = "不";
				if(highnum >= poornum) {
					msg = "优质水类（Ⅰ类、Ⅱ类、Ⅲ类）所占天数最多，因此该时间段防治污染情况总体良好";
					flag = "不";
				}
				else {
					msg = "劣质水类（Ⅳ类、Ⅴ类）所占天数最多，因此该时间段防治污染情况总体较差";
					flag = "";
				}
				request.getSession().setAttribute("msg",msg);
				request.getSession().setAttribute("flag",flag);
			}
			else {
				if(!ta.isEmpty()) {
					request.getSession().setAttribute("defaultMeanW",ta);
				}		
			}
			request.getSession().setAttribute("abnormalnum",num[0]);
			request.getSession().setAttribute("firstnum",num[1]);
			request.getSession().setAttribute("secondnum",num[2]);
			request.getSession().setAttribute("thirdnum",num[3]);
			request.getSession().setAttribute("fourthnum",num[4]);
			request.getSession().setAttribute("fifthnum",num[5]);
			request.getSession().setAttribute("abnormaltime",ta0);
			request.getSession().setAttribute("firsttime",ta1);
			request.getSession().setAttribute("secondtime",ta2);
			request.getSession().setAttribute("thirdtime",ta3);
			request.getSession().setAttribute("fourthtime",ta4);
			request.getSession().setAttribute("fifthtime",ta5);
			request.getSession().setAttribute("pronameW",Proname);
			request.getSession().setAttribute("stanameW",Staname);
			request.getSession().setAttribute("statimeW",Statime);
			request.getSession().setAttribute("endtimeW",Endtime);
			request.getSession().setAttribute("parameterW",parameterChi);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("border",border);
		request.getSession().setAttribute("first",first);
		request.getSession().setAttribute("second",second);
		request.getSession().setAttribute("third",third);
		request.getSession().setAttribute("fourth",fourth);
		request.getSession().setAttribute("fifth",fifth);
		response.sendRedirect("warning.jsp");
	}
}
