package com.beans;

public class ProAndStaNum {
	private String province;
	private int stanum;
	
	public ProAndStaNum() {
		super();
	}
	public ProAndStaNum(String province, int stanum) {
		super();
		this.province = province;
		this.stanum = stanum;
	}
	public String getProvince() {
		return province;
	}
	public int getStanum() {
		return stanum;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setStanum(int stanum) {
		this.stanum = stanum;
	}
	
}
