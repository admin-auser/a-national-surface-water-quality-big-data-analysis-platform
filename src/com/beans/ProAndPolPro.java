package com.beans;

public class ProAndPolPro{
	public String province;
	public double polpro;
	
	public ProAndPolPro() {
		super();
	}
	public ProAndPolPro(String province, double polpro) {
		super();
		this.province = province;
		this.polpro = polpro;
	}
	public String getProvince() {
		return province;
	}
	public double getPolpro() {
		return polpro;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setPolpro(double polpro) {
		this.polpro = polpro;
	}
	
}
