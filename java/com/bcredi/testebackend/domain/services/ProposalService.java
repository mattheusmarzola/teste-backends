package com.bcredi.testebackend.domain.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bcredi.testebackend.data.EventListSingleton;
import com.bcredi.testebackend.data.ProposalListSingleton;
import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.entities.Proposal;


public class ProposalService {

	public final BigDecimal _MAX_LOAN_VALUE = BigDecimal.valueOf(3_000_000);
	public final BigDecimal _MIN_LOAN_VALUE = BigDecimal.valueOf(30_000);
	
	public final int _MONTHS_IN_A_YEAR = 12;
	public final int _MIN_NUMBER_OF_MONTHLY_INSTALLMENTS = 2 * this._MONTHS_IN_A_YEAR;
	public final int _MAX_NUMBER_OF_MONTHLY_INSTALLMENTS = 15 * this._MONTHS_IN_A_YEAR;
	
	public boolean isLoanValueValid(Proposal proposal) {
		if(proposal.getLoanValue() == null) return false;
		
		boolean isMinValid = proposal.getLoanValue().compareTo(this._MIN_LOAN_VALUE) >= 0;
		boolean isMaxValid = proposal.getLoanValue().compareTo(this._MAX_LOAN_VALUE) <= 0;
		
		if(isMinValid && isMaxValid) {
			return true;
		}
		return false;		
	}
	
	public boolean isNumberOfMonthlyInstallmentsValid(Proposal proposal) {
		boolean isMinValid = proposal.getNumberOfMonthlyInstallments() >= this._MIN_NUMBER_OF_MONTHLY_INSTALLMENTS;
		boolean isMaxValid = proposal.getNumberOfMonthlyInstallments() <= this._MAX_NUMBER_OF_MONTHLY_INSTALLMENTS;
		
		if(isMinValid && isMaxValid) {
			return true;
		}
		return false;		
	}
	
	public BigDecimal calculateInstallmentValue(Proposal proposal) {
		BigDecimal installmentsValue = null;
		BigDecimal numberOfMonthlyInstallments = new BigDecimal(proposal.getNumberOfMonthlyInstallments());
		
		installmentsValue = proposal.getLoanValue().divide(numberOfMonthlyInstallments, RoundingMode.HALF_DOWN);

		BigDecimal remnant = proposal.getLoanValue().subtract(installmentsValue.multiply(numberOfMonthlyInstallments));
		installmentsValue = installmentsValue.add(remnant);
		
		return installmentsValue;
	}
	
	public void create(Proposal proposal) {
		ProposalListSingleton.getInstance().add(proposal);
	}

	public void delete(Proposal proposal) {
		ProposalListSingleton.getInstance().removeIf(p -> p.getId().equals(p.getId()));
	}

	public void update(Proposal proposal) {
		Proposal proposalToUpdate = ProposalListSingleton.getProposalById(proposal.getId());
		proposalToUpdate.setLoanValue(proposal.getLoanValue());
		proposalToUpdate.setNumberOfMonthlyInstallments(proposal.getNumberOfMonthlyInstallments());
	}

	public List<Proposal> getAllValidProposal() {
		ProponentService proponentService = new ProponentService();
		WarrantyService warrantyService = new WarrantyService();
		
		List<Proposal>  allProposal = ProposalListSingleton.getInstance();
//		System.out.println("Todas>"+proposalIds.size());

		List<Proposal> allProposalValidationResult = allProposal.stream().filter(this.isLoanValueValidPredicate())
																.filter(this.isNumberOfMonthlyInstallmentsValidPredicate())
																.filter(proponentService.isNumberOfProponentsValidPredicate())
																.filter(proponentService.areAllProponentsOverAgePredicate())
																.filter(proponentService.isMainProponentMonthlyIncomeValidPredicate())
																.filter(proponentService.isNumberOfMainProponentsValidPredicate())
																.filter(warrantyService.isNumberOfWarrantyValidPredicate())
																.filter(warrantyService.isTotalValueOfWarrantiesValidPredicate())
																.filter(warrantyService.isAllProvicesValid()).collect(Collectors.toList());
		
		return allProposalValidationResult;
	}
	
	private Predicate<Proposal> isLoanValueValidPredicate(){
		return p -> this.isLoanValueValid(p);
	}
	private Predicate<Proposal> isNumberOfMonthlyInstallmentsValidPredicate(){
		return p -> this.isNumberOfMonthlyInstallmentsValid(p);
	}

	
}
