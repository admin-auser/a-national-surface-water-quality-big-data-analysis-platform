package com.survey;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.ProAndPolPro;
import com.beans.ProAndStaNum;
import com.beans.Result;
import com.beans.TypeAndNum;
import com.config.DatabaseConnection;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CollectionOverview
 */
@WebServlet({"/collected-water", "*.do"})
public class CollectionOverviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollectionOverviewServlet() {
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
		try {
			String total_data = CollectionResults.survey(0);
			request.getSession().setAttribute("total_data",total_data);
			String current_data = CollectionResults.survey(1);
			request.getSession().setAttribute("current_data",current_data);
			String normal_site = CollectionResults.survey(2);
			request.getSession().setAttribute("normal_site",normal_site);
			String high_water = CollectionResults.survey(3);
			request.getSession().setAttribute("high_water",high_water);
			String poor_water = CollectionResults.survey(4);
			request.getSession().setAttribute("poor_water",poor_water);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("survey.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		// ???????????????URI????????????
        String url = request.getRequestURI();
        // ????????????????????????
        String methodName = url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("."));
        Method method = null;
        try {
            // ??????????????????????????????????????????????????????
            method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // ????????????
            method.invoke(this, request, response);
        } catch (Exception e) {
            throw new RuntimeException("?????????????????????");
        }
	}
	
	@SuppressWarnings("unused")
	private void mapSite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<ProAndStaNum> psnArr = CollectionResults.mapping();
			//???????????????????????????JSP????????????????????????????????????
			response.setContentType("text/html; charset=utf-8");
			JSONArray jsonPSN = JSONArray.fromObject(psnArr);
			//System.out.println(json.toString());
			//?????????JSP
			PrintWriter writer = response.getWriter();
			writer.println(jsonPSN.toString());
			writer.flush();
			//???????????????
			writer.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@SuppressWarnings("unused")
	private void waterCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<TypeAndNum> tnArr = CollectionResults.waterQualityGrade();
			response.setContentType("text/html; charset=utf-8");
			JSONArray jsonTN = JSONArray.fromObject(tnArr);
			PrintWriter writer = response.getWriter();
			writer.println(jsonTN.toString());
			//System.out.println(jsonTN.toString());
			writer.flush();
			//???????????????
			writer.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	@SuppressWarnings("unused")
	private void pollutionProportion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<ProAndPolPro> pppArr = CollectionResults.waterQualityPollution();
			/*System.out.println("Before Sort:");
			for(ProAndPolPro ppp:pppArr) {
				System.out.println("??????"+ppp.getProvince()+"??????"+ppp.getPolpro()+"\n");
			}*/
			//pppArr.forEach(x -> System.out.println(x));
			Collections.sort(pppArr,Comparator.comparingDouble(ProAndPolPro::getPolpro));
			/*System.out.println("\nAfterSort:");
			for(ProAndPolPro ppp:pppArr) {
				System.out.println("??????"+ppp.getProvince()+"??????"+ppp.getPolpro()+"\n");
			}*/
			//pppArr.forEach(x -> System.out.println(x));
			Collections.reverse(pppArr);
			/*System.out.println("\nAfterReverse:");
			for(ProAndPolPro ppp:pppArr) {
				System.out.println("??????"+ppp.getProvince()+"??????"+ppp.getPolpro()+"\n");
			}*/
			ArrayList<ProAndPolPro> partArr = new ArrayList<ProAndPolPro>();
			int topN = 7;
			int i = 0;
			for(ProAndPolPro ppp:pppArr) {
				if(i < topN) {
					partArr.add(ppp);
					i++;
				}
			}
			response.setContentType("text/html; charset=utf-8");
			JSONArray jsonPPP = JSONArray.fromObject(partArr);
			PrintWriter writer = response.getWriter();
			writer.println(jsonPPP.toString());
			//System.out.println(jsonTN.toString());
			writer.flush();
			//???????????????
			writer.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
