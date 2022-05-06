package com.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

public class TimerTaskService extends TimerTask {
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void run() {
		try {
			// 要执行的任务逻辑写在这里
			ArrayList<String> list = new ArrayList<String>();
			list = PythonProcessing.handle(0,null);
			if(list.isEmpty())
				System.out.println("链表为空");
			else {
				ArrayList<String> list2 = new ArrayList<String>();
				list2 = PythonProcessing.handle(1,null);
				if(list2.isEmpty())
					System.out.println("链表为空");
			}
			System.out.println("执行当前时间"+formatter.format(Calendar.getInstance().getTime()));
			System.out.println("执行成功");
		} catch (Exception e) {
			System.out.println("-------------解析信息发生异常--------------");
			System.out.println("执行失败");
		}
	}
}
