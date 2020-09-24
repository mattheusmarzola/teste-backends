package com.bcredi.testebackend.domain.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.bcredi.testebackend.data.WarrantyDataProvider;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.entities.Warranty;

public class WarrantyService {
	public final int _MIN_NUMBER_OF_WARRANTIES = 1;
	public final BigDecimal _MULTIPLICATION_OF_THE_GUARANTEE_BY_THE_LOAN_VALUE = new BigDecimal("2");
	public final List<String> _NOT_ACCEPTED_PROVINCES = Arrays.asList("PR","SC","RS");
	public final List<String> _ALL_PROVINCES = Arrays.asList("AC","AL","AP","AM","BA","CE","ES","GO","MA","MT","MS","MG","PA",
																"PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO","DF");
	
	public boolean isNumberOfWarrantyValid(List<Warranty> warranties) {
		if(warranties == null) return false;
		
		if(warranties.size() >= this._MIN_NUMBER_OF_WARRANTIES) {
			return true;
		}
		return false;		
	}
	
	public boolean isTotalValueOfWarrantiesValid(List<Warranty> warranties, BigDecimal loanValue ) {
		if(warranties == null) return false;
		if(loanValue == null) return false;
		
		try {
			BigDecimal totalValueOfWarranties = warranties.stream()
					.map(w -> w.getValue())
					.reduce(BigDecimal.valueOf(0), BigDecimal::add);
			
			BigDecimal necessaryWarranty = loanValue.multiply(this._MULTIPLICATION_OF_THE_GUARANTEE_BY_THE_LOAN_VALUE);
			
			return totalValueOfWarranties.compareTo(necessaryWarranty) >= 0;
			
		}catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public boolean isAcceptableProvince(Warranty warranties) {
		if(warranties.getProvince() == null || warranties.getProvince().trim().isEmpty()) {
			return false;
		}
		
		boolean isNotAcceptableProvince = this._NOT_ACCEPTED_PROVINCES.contains(warranties.getProvince());
		boolean isValidProvince = this._ALL_PROVINCES.contains(warranties.getProvince());

		
		return !isNotAcceptableProvince && isValidProvince;
	}
	
	public boolean isAllProvicesValid(List<Warranty> warranties) {
		long numberOfInvalidsProvinces = warranties.stream().map(w -> this.isAcceptableProvince(w))
					.filter(resultProvince -> !resultProvince).count();
		
		return numberOfInvalidsProvinces == 0;
	}

	public void add(Proposal proposal, Warranty warranty) {
		WarrantyDataProvider warrantyDataProvider = new WarrantyDataProvider(); 
		warrantyDataProvider.addWarranty(proposal.getId(), warranty);
	}

	public void update(Proposal proposal, Warranty warranty) {
		WarrantyDataProvider warrantyDataProvider = new WarrantyDataProvider();
		Warranty warrantyToUpdate = warrantyDataProvider.getWarrantyByIdProposalAndIdWarranty(proposal.getId(), warranty.getId());
		warrantyToUpdate.setProvince(warranty.getProvince());
		warrantyToUpdate.setValue(warranty.getValue());
	}

	public void remove(Proposal proposal, Warranty warranty) {
		WarrantyDataProvider warrantyDataProvider = new WarrantyDataProvider(); 
		warrantyDataProvider.removeWarranty(proposal.getId(), warranty.getId());
	}
	
	public Predicate<Proposal> isNumberOfWarrantyValidPredicate(){
		return p -> this.isNumberOfWarrantyValid(p.getWarranties());
	}
	
	public Predicate<Proposal> isTotalValueOfWarrantiesValidPredicate(){
		return p -> this.isTotalValueOfWarrantiesValid(p.getWarranties(), p.getLoanValue());
	}
	
	public Predicate<Proposal> isAllProvicesValid(){
		return p -> this.isAllProvicesValid(p.getWarranties());
	}
}
