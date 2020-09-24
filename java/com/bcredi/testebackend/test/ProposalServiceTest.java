package com.bcredi.testebackend.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.services.ProposalService;

public class ProposalServiceTest {
	
	@Test
	public void shouldFalseIfLoanValueIsNull() {
		Proposal proposal = new Proposal();
		ProposalService proposalController = new ProposalService();
		
		String messageFail = "The loan value must be between " + proposalController._MIN_LOAN_VALUE + " and "
				+ proposalController._MAX_LOAN_VALUE;
		assertFalse(messageFail, proposalController.isLoanValueValid(proposal));
	}

	@Test
	public void shouldFalseIfLoanValueIsBelowMin() {
		Proposal proposal = new Proposal();
		ProposalService proposalController = new ProposalService();
		proposal.setLoanValue(BigDecimal.valueOf(1100));

		String messageFail = "The loan value must be between " + proposalController._MIN_LOAN_VALUE + " and "
				+ proposalController._MAX_LOAN_VALUE;
		assertFalse(messageFail, proposalController.isLoanValueValid(proposal));
	}

	@Test
	public void shouldFalseIfLoanValueIsAboveMax() {
		Proposal proposal = new Proposal();
		ProposalService proposalController = new ProposalService();
		proposal.setLoanValue(BigDecimal.valueOf(3_000_100));

		String messageFail = "The loan value must be between " + proposalController._MIN_LOAN_VALUE + " and "
				+ proposalController._MAX_LOAN_VALUE;
		assertFalse(messageFail, proposalController.isLoanValueValid(proposal));
	}

	@Test
	public void shouldTrueIfLoanValueIsBetweenMinAndMax() {
		Proposal proposal = new Proposal();
		ProposalService proposalController = new ProposalService();
		proposal.setLoanValue(BigDecimal.valueOf(3_000_000));

		String messageFail = "The loan value must be between " + proposalController._MIN_LOAN_VALUE + " and "
				+ proposalController._MAX_LOAN_VALUE;
		assertTrue(messageFail, proposalController.isLoanValueValid(proposal));
	}

	@Test
	public void shouldFalseIfNumberOfMonthlyInstallmentsIsBelowMin() {
		Proposal proposal = new Proposal();
		ProposalService proposalController = new ProposalService();
		proposal.setNumberOfMonthlyInstallments(2);

		String messageFail = "The number of monthly installments must be between " + proposalController._MIN_NUMBER_OF_MONTHLY_INSTALLMENTS
				+ " and " + proposalController._MAX_NUMBER_OF_MONTHLY_INSTALLMENTS;
		assertFalse(messageFail, proposalController.isNumberOfMonthlyInstallmentsValid(proposal));
	}

	@Test
	public void shouldFalseIfNumberOfMonthlyInstallmentsIsAboveMax() {
		Proposal proposal = new Proposal();
		ProposalService proposalController = new ProposalService();
		proposal.setNumberOfMonthlyInstallments(300);

		String messageFail = "The number of monthly installments must be between " + proposalController._MIN_NUMBER_OF_MONTHLY_INSTALLMENTS
				+ " and " + proposalController._MAX_NUMBER_OF_MONTHLY_INSTALLMENTS;
		assertFalse(messageFail, proposalController.isNumberOfMonthlyInstallmentsValid(proposal));
	}

	@Test
	public void shouldTrueIfNumberOfMonthlyInstallmentsIsBetweenMinAndMax() {
		Proposal proposal = new Proposal();
		ProposalService proposalController = new ProposalService();
		proposal.setNumberOfMonthlyInstallments(120);

		String messageFail = "The number of monthly installments must be between " + proposalController._MIN_NUMBER_OF_MONTHLY_INSTALLMENTS
				+ " and " + proposalController._MAX_NUMBER_OF_MONTHLY_INSTALLMENTS;
		assertTrue(messageFail, proposalController.isNumberOfMonthlyInstallmentsValid(proposal));
	}
	
	@Test
	public void shouldTrueIfCalculateCorrectlyInstallmentValue() {
		Proposal proposal = new Proposal();
		ProposalService proposalController = new ProposalService();
		proposal.setNumberOfMonthlyInstallments(120);
		proposal.setLoanValue(new BigDecimal("119027.00"));
		
		String messageFail = "The number of monthly installments must be between " + proposalController._MIN_NUMBER_OF_MONTHLY_INSTALLMENTS
				+ " and " + proposalController._MAX_NUMBER_OF_MONTHLY_INSTALLMENTS;
		
		BigDecimal isntallmentsValue = proposalController.calculateInstallmentValue(proposal);
		BigDecimal correctlyValue = new BigDecimal("992.09");
		
		assertTrue(messageFail, isntallmentsValue.compareTo(correctlyValue) == 0);
	}

}
