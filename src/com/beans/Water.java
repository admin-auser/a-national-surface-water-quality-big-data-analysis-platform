package com.beans;

public class Water {
	
	private String province;
	private String valley;
	private String staname;
	private String sta_time;
	private String water_l;
	private String water_temp;
	
	private String sta_ph_v;
	private String sta_do_v;
	private String conductivity;
	private String turbidity;
	private String sta_pp_v;
	private String sta_an_v;
	private String sta_tp_v;
	private String sta_tn_v;
	private String chlorophyll;
	private String algal_density;
	private String status_label;
	
	public Water() {}
	public Water(String province, String valley, String staname, String sta_time, String water_l, String water_temp,
			String sta_ph_v, String sta_do_v, String conductivity, String turbidity, String sta_pp_v, String sta_an_v,
			String sta_tp_v, String sta_tn_v, String chlorophyll, String algal_density, String status_label) {
		super();
		this.province = province;
		this.valley = valley;
		this.staname = staname;
		this.sta_time = sta_time;
		this.water_l = water_l;
		this.water_temp = water_temp;
		this.sta_ph_v = sta_ph_v;
		this.sta_do_v = sta_do_v;
		this.conductivity = conductivity;
		this.turbidity = turbidity;
		this.sta_pp_v = sta_pp_v;
		this.sta_an_v = sta_an_v;
		this.sta_tp_v = sta_tp_v;
		this.sta_tn_v = sta_tn_v;
		this.chlorophyll = chlorophyll;
		this.algal_density = algal_density;
		this.status_label = status_label;
	}
	
	public String getProvince() {
		return province;
	}
	public String getValley() {
		return valley;
	}
	public String getStaname() {
		return staname;
	}
	public String getSta_time() {
		return sta_time;
	}
	public String getWater_l() {
		return water_l;
	}
	public String getWater_temp() {
		return water_temp;
	}
	public String getSta_ph_v() {
		return sta_ph_v;
	}
	public String getSta_do_v() {
		return sta_do_v;
	}
	public String getConductivity() {
		return conductivity;
	}
	public String getTurbidity() {
		return turbidity;
	}
	public String getSta_pp_v() {
		return sta_pp_v;
	}
	public String getSta_an_v() {
		return sta_an_v;
	}
	public String getSta_tp_v() {
		return sta_tp_v;
	}
	public String getSta_tn_v() {
		return sta_tn_v;
	}
	public String getChlorophyll() {
		return chlorophyll;
	}
	public String getAlgal_density() {
		return algal_density;
	}
	public String getStatus_label() {
		return status_label;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setValley(String valley) {
		this.valley = valley;
	}
	public void setStaname(String staname) {
		this.staname = staname;
	}
	public void setSta_time(String sta_time) {
		this.sta_time = sta_time;
	}
	public void setWater_l(String water_l) {
		this.water_l = water_l;
	}
	public void setWater_temp(String water_temp) {
		this.water_temp = water_temp;
	}
	public void setSta_ph_v(String sta_ph_v) {
		this.sta_ph_v = sta_ph_v;
	}
	public void setSta_do_v(String sta_do_v) {
		this.sta_do_v = sta_do_v;
	}
	public void setConductivity(String conductivity) {
		this.conductivity = conductivity;
	}
	public void setTurbidity(String turbidity) {
		this.turbidity = turbidity;
	}
	public void setSta_pp_v(String sta_pp_v) {
		this.sta_pp_v = sta_pp_v;
	}
	public void setSta_an_v(String sta_an_v) {
		this.sta_an_v = sta_an_v;
	}
	public void setSta_tp_v(String sta_tp_v) {
		this.sta_tp_v = sta_tp_v;
	}
	public void setSta_tn_v(String sta_tn_v) {
		this.sta_tn_v = sta_tn_v;
	}
	public void setChlorophyll(String chlorophyll) {
		this.chlorophyll = chlorophyll;
	}
	public void setAlgal_density(String algal_density) {
		this.algal_density = algal_density;
	}
	public void setStatus_label(String status_label) {
		this.status_label = status_label;
	}

	
}