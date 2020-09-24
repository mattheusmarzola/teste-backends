package com.bcredi.testebackend.domain.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Predicate;

import com.bcredi.testebackend.data.ProponentDataProvider;
import com.bcredi.testebackend.data.WarrantyDataProvider;
import com.bcredi.testebackend.domain.entities.Proponent;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.entities.Warranty;

public class ProponentService {

	public final int _MIN_NUMBER_OF_PROPONENTS = 2;
	public final int _NUMBER_OF_MAIN_PROPONENT = 1;
	public final int _MIN_PROPONENT_AGE = 18;
	

	public boolean isNumberOfProponentsValid(Proposal proposal) {
		if(proposal.getProponents() == null) return false;
		
		if(proposal.getProponents().size() >= this._MIN_NUMBER_OF_PROPONENTS) {
			return true;
		}
		return false;		
	}
		
	public boolean isNumberOfMainProponentsValid(Proposal proposal) {
		if(proposal.getProponents() == null) return false;
		
		Long mainProponentsCount = proposal.getProponents().stream().filter(p -> p.isMain()).count();
		
		if(this._NUMBER_OF_MAIN_PROPONENT == mainProponentsCount) {
			return true;
		}
		return false;		
	}
	
	public boolean areAllProponentsOverAge(Proposal proposal) {
		if(proposal.getProponents() == null || proposal.getProponents().isEmpty()) return false;
		
		Long numberOfProponentsWithValidAge = proposal.getProponents().stream()
				.filter(p -> p.getAge() >= this._MIN_PROPONENT_AGE).count();
		
		if(numberOfProponentsWithValidAge == proposal.getProponents().size()) {
			return true;
		}
		return false;		
	}
	
	public boolean isMainProponentMonthlyIncomeValid(Proponent mainProponent, BigDecimal installmentValue) {
		if(mainProponent == null) return false;
		
		BigDecimal multiplyer = null;
		if(mainProponent.getAge() >= 18 && mainProponent.getAge() < 24) {
			multiplyer = new BigDecimal("4");
		}
		
		if(mainProponent.getAge() >= 24 && mainProponent.getAge() < 50) {
			multiplyer = new BigDecimal("3");
		}
		
		if(mainProponent.getAge() >= 50) {
			multiplyer = new BigDecimal("2");
		}
		
		BigDecimal minValeuOfMouthlyIncome = installmentValue.multiply(multiplyer);

		return minValeuOfMouthlyIncome.compareTo(mainProponent.getMonthlyIncome())  <= 0;
	}

	public void add(Proposal proposal, Proponent proponent) {
		ProponentDataProvider proponentDataProvider = new ProponentDataProvider(); 
		proponentDataProvider.addProponent(proposal.getId(), proponent);
	}
	
	public void update(Proposal proposal, Proponent proponent) {
		ProponentDataProvider proponentDataProvider = new ProponentDataProvider();
		Proponent proponentToUpdate = proponentDataProvider.getProponentByIdProposalAndIdProponent(proposal.getId(), proponent.getId());
		proponentToUpdate.setAge(proponent.getAge());
		proponentToUpdate.setMain(proponent.isMain());
		proponentToUpdate.setMonthlyIncome(proponent.getMonthlyIncome());
		proponentToUpdate.setName(proponent.getName());

	}

	public void remove(Proposal proposal, Proponent proponent) {
		ProponentDataProvider proponentDataProvider = new ProponentDataProvider();
		proponentDataProvider.removeProponent(proposal.getId(), proponent.getId());
	}
	
	public Predicate<Proposal> isNumberOfProponentsValidPredicate(){
		return p -> this.isNumberOfProponentsValid(p);
	}
	
	public Predicate<Proposal> areAllProponentsOverAgePredicate(){
		return p -> this.areAllProponentsOverAge(p);
	}
	
	public Predicate<Proposal> isMainProponentMonthlyIncomeValidPredicate(){
		return p -> this.isMainProponentMonthlyIncomeValid(p.getMainProponent(), new ProposalService().calculateInstallmentValue(p));
	}
	
	public Predicate<Proposal> isNumberOfMainProponentsValidPredicate(){
		return p -> this.isNumberOfMainProponentsValid(p);
	}
	
}
