package com.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Auxiliary {
	public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	
	/**
     * 获取两个日期之间的所有日期
     * 
     * @param startTime
     *            开始日期
     * @param endTime
     *            结束日期
     * @return
     */
    public static ArrayList<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
    	ArrayList<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }
	
	//获取所选的两个日期中的所有月份
	public static ArrayList<String> getMonths(String y1, String y2) {
			ArrayList<String> list = null;
			try {
	            Date startDate = new SimpleDateFormat("yyyy-MM").parse(y1);
	            Date endDate = new SimpleDateFormat("yyyy-MM").parse(y2);
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(startDate);
	            // 获取开始年份和开始月份
	            int startYear = calendar.get(Calendar.YEAR);
	            int startMonth = calendar.get(Calendar.MONTH);
	            // 获取结束年份和结束月份
	            calendar.setTime(endDate);
	            int endYear = calendar.get(Calendar.YEAR);
	            int endMonth = calendar.get(Calendar.MONTH);
	            //
	            list = new ArrayList<String>();
	            for (int i = startYear; i <= endYear; i++) {
	                String date = "";
	                if (startYear == endYear) {
	                    for (int j = startMonth; j <= endMonth; j++) {
	                        if (j < 9) {
	                            date = i + "-0" + (j + 1);
	                        } else {
	                            date = i + "-" + (j + 1);
	                        }
	                        list.add(date);
	                    }

	                } else {
	                    if (i == startYear) {
	                        for (int j = startMonth; j < 12; j++) {
	                            if (j < 9) {
	                                date = i + "-0" + (j + 1);
	                            } else {
	                                date = i + "-" + (j + 1);
	                            }
	                            list.add(date);
	                        }
	                    } else if (i == endYear) {
	                        for (int j = 0; j <= endMonth; j++) {
	                            if (j < 9) {
	                                date = i + "-0" + (j + 1);
	                            } else {
	                                date = i + "-" + (j + 1);
	                            }
	                            list.add(date);
	                        }
	                    } else {
	                        for (int j = 0; j < 12; j++) {
	                            if (j < 9) {
	                                date = i + "-0" + (j + 1);
	                            } else {
	                                date = i + "-" + (j + 1);
	                            }
	                            list.add(date);
	                        }
	                    }

	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return list;
		}
	
	
	//获取所选的两个日期中的所有年份
		public static ArrayList<String> getYears(String y1, String y2) {
				ArrayList<String> list = null;
				try {
		            Date startDate = new SimpleDateFormat("yyyy").parse(y1);
		            Date endDate = new SimpleDateFormat("yyyy").parse(y2);
		            Calendar calendar = Calendar.getInstance();
		            calendar.setTime(startDate);
		            // 获取开始年份和开始月份
		            int startYear = calendar.get(Calendar.YEAR);
		            // 获取结束年份和结束月份
		            calendar.setTime(endDate);
		            int endYear = calendar.get(Calendar.YEAR);
		            //
		            list = new ArrayList<String>();
		            for (int i = startYear; i <= endYear; i++) {
		                String date = "";
		                date = String.valueOf(i);
		                System.out.println(date);
		                list.add(date);
		            }

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				return list;
			}
	
	
	/**
	　　 *字符串的日期格式的计算
	　　 */
	    public static int daysBetween(String smdate,String bdate) throws ParseException{
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(sdf.parse(smdate));  
	        long time1 = cal.getTimeInMillis();               
	        cal.setTime(sdf.parse(bdate));  
	        long time2 = cal.getTimeInMillis();       
	        long between_days=(time2-time1)/(1000*3600*24);
	          
	       return Integer.parseInt(String.valueOf(between_days));   
	    }
	    
	  //判断字符串是不是以数字开头
		public static boolean isStartWithNumber(String str) {
		   Pattern pattern = Pattern.compile("[0-9]*");
		   if(!str.isEmpty()) {
			   Matcher isNum = pattern.matcher(str.charAt(0)+"");
			   if (!isNum.matches()) {
			       return false;
			   }
			   return true;
		   }
		   else {
			   return false;
		   }
		}

}
