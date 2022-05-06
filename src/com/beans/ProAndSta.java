package com.beans;

public class ProAndSta {
	private String province;
	private String staname;
	
	public ProAndSta() {
		super();
	}
	
	public ProAndSta(String province, String staname) {
		super();
		this.province = province;
		this.staname = staname;
	}
	public String getProvince() {
		return province;
	}
	public String getStaname() {
		return staname;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setStaname(String staname) {
		this.staname = staname;
	}
	
}
