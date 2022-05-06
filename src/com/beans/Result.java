package com.beans;

public class Result {
	private String staname;
	private String time;
	private String parameter;
	private String monthOryear;
	private Double average;
	public Result() {
		super();
	}
	public Result(String staname, String time, String parameter, String monthOryear, Double average) {
		super();
		this.staname = staname;
		this.time = time;
		this.parameter = parameter;
		this.monthOryear = monthOryear;
		this.average = average;
	}
	
	public String getStaname() {
		return staname;
	}
	public String getParameter() {
		return parameter;
	}
	public String getMonthOryear() {
		return monthOryear;
	}
	public void setStaname(String staname) {
		this.staname = staname;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public void setMonthOryear(String monthOryear) {
		this.monthOryear = monthOryear;
	}
	public String getTime() {
		return time;
	}
	public Double getAverage() {
		return average;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
	
	

}
