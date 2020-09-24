package com.bcredi.testebackend.domain.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Proposal {
	private String id;
	private BigDecimal loanValue;
	private int numberOfMonthlyInstallments;
	private List<Proponent> proponents; 
	private List<Warranty> warranties;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getLoanValue() {
		return loanValue;
	}
	public void setLoanValue(BigDecimal loanValue) {
		this.loanValue = loanValue;
	}
	public int getNumberOfMonthlyInstallments() {
		return numberOfMonthlyInstallments;
	}
	public void setNumberOfMonthlyInstallments(int numberOfMonthlyInstallments) {
		this.numberOfMonthlyInstallments = numberOfMonthlyInstallments;
	}
	public List<Proponent> getProponents() {
		return proponents;
	}	
	public void setProponents(List<Proponent> proponents) {
		this.proponents = proponents;
	}
	public List<Warranty> getWarranties() {
		return warranties;
	}
	public void setWarranties(List<Warranty> warranties) {
		this.warranties = warranties;
	} 
	
	public Proponent getMainProponent() {
		if(this.getProponents() == null) return null;
		Optional<Proponent> mainProponent = this.getProponents().stream()
											.filter(p -> p.isMain()).findAny();
		
		if(mainProponent.isPresent()) {
			return mainProponent.get();
		}
		
		return null;
	}
}
