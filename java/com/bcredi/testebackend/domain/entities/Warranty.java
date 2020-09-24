package com.bcredi.testebackend.domain.entities;

import java.math.BigDecimal;

public class Warranty {
	private String id;
	private BigDecimal value;
	private String province;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
}
