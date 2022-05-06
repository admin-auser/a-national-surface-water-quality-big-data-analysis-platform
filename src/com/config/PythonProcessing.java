package com.config;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class PythonProcessing {
	public static ArrayList<String> handle(int flag, String[] para) {
		ArrayList<String> list = new ArrayList<String>();
		try {
				String exe;
				String path = Thread.currentThread().getContextClassLoader().getResource("/com").getPath();
				char test = path.charAt(1);
				String allpath;
				if(test == 'D' || test == 'C') {
					allpath = path.substring(1,3)+"/"+path.substring(3).replaceAll("%20", " ");
					exe = "python";
				}
				else {
					allpath = path.replaceAll("%20", " ");
					exe = "python3";
				}
				String command = "";
				String[] cmdArr = {""};
				if(flag == 0) {
					//exe = "C:\\Users\\12710\\AppData\\Local\\Programs\\Python\\Python36-32\\python.exe";
					exe = "D:\\ProgramData\\Anaconda3\\python.exe";
					command = allpath+"python/ObtainCurrentWater.py";
					//command = "D:/GraduationProject/water.py";
					cmdArr = new String[] {exe, command};
				}
				else if(flag == 1) {
					//exe = "C:\\Users\\12710\\AppData\\Local\\Programs\\Python\\Python36-32\\python.exe";
					exe = "D:\\ProgramData\\Anaconda3\\python.exe";
					command = allpath+"python/DataTransmission.py";
					//command = "D:/GraduationProject/python/DataTransmission.py";
					cmdArr = new String[] {exe, command};
				}
				else {
					
					String activateCommand = "D:\\ProgramData\\Anaconda3\\envs\\py3_6\\Scripts\\activate.bat";
					String envName = "py3_6";
					String link = "&";
					exe = "D:\\ProgramData\\Anaconda3\\envs\\py3_6\\python.exe";
					command = allpath+"python/waterProphet.py";
					//command = "D:/GraduationProject/python/waterProphet.py";
					String csvpath = path.substring(1,3).replaceAll("/", "\\\\")+path.substring(3).replaceAll("%20", " ").replaceAll("/", "\\\\")+"data\\all_data.csv";
					//System.out.println(csvpath);
					/*String staname = "桔子洲";
					String parameter = "sta_do_v";
					String num = "30";*/
					//cmdArr = new String[] {exe, command, csvpath, staname, parameter};
					//cmdArr = new String[] {activateCommand, envName, link, exe, command, csvpath, staname, parameter, num};
					cmdArr = new String[] {activateCommand, envName, link, exe, command, csvpath, para[0], para[1], para[2], para[3]};
				}
				Process process = Runtime.getRuntime().exec(cmdArr);
				// 防止缓冲区满, 导致卡住
				new Thread() {
					@Override
					public void run() {
						super.run();
						String str = null;
						try {
							InputStream is = process.getInputStream();
							DataInputStream dis = new DataInputStream(is);
							//BufferedReader bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
							while ((str = dis.readLine()) != null) {
							//while ((str = bufferReader.readLine()) != null) {
								if(test == 'D' || test == 'C') {
									str = new String(str.getBytes("ISO-8859-1"), "GBK");
								}
								else {
									str = new String(str.getBytes("ISO-8859-1"), "utf-8");
								}
								//result += str;
								list.add(str);
							}
							dis.close();
							//bufferReader.close();
						}
						catch (Exception e) {

						}

					}
				}.start();
				int re = process.waitFor();
				System.out.println(re);
			} catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		//return result;
		return list;
	}
}
