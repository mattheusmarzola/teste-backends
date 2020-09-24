package com.bcredi.testebackend.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.bcredi.testebackend.data.ProponentDataProvider;
import com.bcredi.testebackend.data.ProposalListSingleton;
import com.bcredi.testebackend.domain.entities.Proponent;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.services.ProponentService;
import com.bcredi.testebackend.domain.services.ProposalService;

public class ProponentServiceTest {
	
	@Test
	public void shouldFalseIfNumberOfProponentsIsBelowMin() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		proposal.getProponents().add(new Proponent());
		
		String messageFail = "The number of proponents must be above " + proponentController._MIN_NUMBER_OF_PROPONENTS;
		assertFalse(messageFail, proponentController.isNumberOfProponentsValid(proposal));
	}

	@Test
	public void shouldTrueIfNumberOfProponentsIsAboveMin() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		proposal.getProponents().add(new Proponent());
		proposal.getProponents().add(new Proponent());

		String messageFail = "The number of proponents must be above " + proponentController._MIN_NUMBER_OF_PROPONENTS;
		assertTrue(messageFail, proponentController.isNumberOfProponentsValid(proposal));
	}

	@Test
	public void shouldFalseIfNumberOfMainProponentsIsAboveCorrect() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		proposal.getProponents().add(new Proponent());
		
		String messageFail = "The number of main proponents must be " + proponentController._NUMBER_OF_MAIN_PROPONENT;
		assertFalse(messageFail, proponentController.isNumberOfMainProponentsValid(proposal));
	}
	
	@Test
	public void shouldFalseIfNumberOfMainProponentsIsBelowCorrect() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		Proponent p2 = new Proponent();
		p1.setMain(true);
		p2.setMain(true);
		
		proposal.getProponents().addAll(Arrays.asList(p1, p2));
		
		String messageFail = "The number of main proponents must be " + proponentController._NUMBER_OF_MAIN_PROPONENT;
		assertFalse(messageFail, proponentController.isNumberOfMainProponentsValid(proposal));
	}

	@Test
	public void shouldTrueIfNumberOfMainProponentsIsCorrect() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		p1.setMain(true);
		proposal.getProponents().add(p1);

		String messageFail = "The number of main proponents must be " + proponentController._NUMBER_OF_MAIN_PROPONENT;
		assertTrue(messageFail, proponentController.isNumberOfMainProponentsValid(proposal));
	}

	@Test
	public void shouldFalseIfHasProponentWithInvalidAge() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		Proponent p2 = new Proponent();
		p1.setAge(1);
		p2.setAge(20);
		
		proposal.getProponents().addAll(Arrays.asList(p1, p2));
		
		String messageFail = "The age of proponents must be above " + proponentController._MIN_PROPONENT_AGE;
		assertFalse(messageFail, proponentController.areAllProponentsOverAge(proposal));
	}

	@Test
	public void shouldTrueIfHasAllProponentAgeIsValid() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		Proponent p2 = new Proponent();
		p1.setAge(18);
		p2.setAge(20);
		
		proposal.getProponents().addAll(Arrays.asList(p1, p2));

		String messageFail = "The age of proponents must be above " + proponentController._MIN_PROPONENT_AGE;
		assertTrue(messageFail, proponentController.areAllProponentsOverAge(proposal));
	}
	
	@Test
	public void shouldTrueIfMainProponentWithEighteenYearsOldMounthlyIncomeIsValid() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		p1.setAge(18);
		p1.setMonthlyIncome(new BigDecimal("10000"));
		p1.setMain(true);
		
		proposal.getProponents().addAll(Arrays.asList(p1));

		BigDecimal installmentsValue = new BigDecimal("1000");
		
		String messageFail = "The monthly income of the main proponent is invalid";
		assertTrue(messageFail, proponentController.isMainProponentMonthlyIncomeValid(proposal.getMainProponent(), installmentsValue));
	}
	
	@Test
	public void shouldFalseIfMainProponentWithEighteenYearsOldMounthlyIncomeIsValid() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		p1.setAge(18);
		p1.setMonthlyIncome(new BigDecimal("10000"));
		p1.setMain(true);
		
		proposal.getProponents().addAll(Arrays.asList(p1));

		BigDecimal installmentsValue = new BigDecimal("10000");
		
		String messageFail = "The monthly income of the main proponent is invalid";
		assertFalse(messageFail, proponentController.isMainProponentMonthlyIncomeValid(proposal.getMainProponent(), installmentsValue));
	}
	
	@Test
	public void shouldTrueIfMainProponentWithTwentyFiveYearsOldMounthlyIncomeIsValid() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		p1.setAge(25);
		p1.setMonthlyIncome(new BigDecimal("10000"));
		p1.setMain(true);
		
		proposal.getProponents().addAll(Arrays.asList(p1));

		BigDecimal installmentsValue = new BigDecimal("3000");
		
		String messageFail = "The monthly income of the main proponent is invalid";
		assertTrue(messageFail, proponentController.isMainProponentMonthlyIncomeValid(proposal.getMainProponent(), installmentsValue));
	}
	
	@Test
	public void shouldFalseIfMainProponentWithTwentyFiveYearsOldMounthlyIncomeIsValid() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		p1.setAge(25);
		p1.setMonthlyIncome(new BigDecimal("8000"));
		p1.setMain(true);
		
		proposal.getProponents().addAll(Arrays.asList(p1));

		BigDecimal installmentsValue = new BigDecimal("3000");
		
		String messageFail = "The monthly income of the main proponent is invalid";
		assertFalse(messageFail, proponentController.isMainProponentMonthlyIncomeValid(proposal.getMainProponent(), installmentsValue));
	}
	
	@Test
	public void shouldTrueIfMainProponentWithFiftyOneYearsOldMounthlyIncomeIsValid() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		p1.setAge(51);
		p1.setMonthlyIncome(new BigDecimal("10000"));
		p1.setMain(true);
		
		proposal.getProponents().addAll(Arrays.asList(p1));

		BigDecimal installmentsValue = new BigDecimal("3000");
		
		String messageFail = "The monthly income of the main proponent is invalid";
		assertTrue(messageFail, proponentController.isMainProponentMonthlyIncomeValid(proposal.getMainProponent(), installmentsValue));
	}
	
	@Test
	public void shouldFalseIfMainProponentWithFiftyOneYearsOldMounthlyIncomeIsValid() {
		Proposal proposal = new Proposal();
		ProponentService proponentController = new ProponentService();
		proposal.setProponents(new ArrayList<Proponent>());
		Proponent p1 = new Proponent();
		p1.setAge(51);
		p1.setMonthlyIncome(new BigDecimal("6000"));
		p1.setMain(true);
		
		proposal.getProponents().addAll(Arrays.asList(p1));

		BigDecimal installmentsValue = new BigDecimal("3000.95");
		
		String messageFail = "The monthly income of the main proponent is invalid";
		assertFalse(messageFail, proponentController.isMainProponentMonthlyIncomeValid(proposal.getMainProponent(), installmentsValue));
	}
	
	@Test
	public void shouldTrueIfAddProponentToProposal() {
		ProposalListSingleton.cleanData();
		ProponentService proponentController = new ProponentService();
		ProposalService proposalController = new ProposalService();
		
		Proposal proposal = this.createProposal();
		proposal.setId("proposal1");
		
		Proponent proponent1 = this.createProponent();
		proponent1.setId("proponent1");
		
		proposal.setProponents(new ArrayList<Proponent>());
		
		proposalController.create(proposal);
		proponentController.add(proposal, proponent1);
		
		Proposal proposalReturn = ProposalListSingleton.getProposalById(proposal.getId());
		
		boolean assertResult = proposalReturn.getProponents().stream().filter(proponent -> proponent.getId().equals(proponent1.getId())).count() == 1;
		assertTrue("Erro to add proponent to proposal", assertResult);		
	}
	
	@Test
	public void shouldTrueIfUpdateProponent() {
		ProposalListSingleton.cleanData();
		ProponentService proponentController = new ProponentService();
		ProposalService proposalController = new ProposalService();
		
		Proposal proposal = this.createProposal();
		proposal.setId("proposal1");
		
		Proponent proponent1 = this.createProponent();
		proponent1.setId("proponent1");
		
		proposalController.create(proposal);
		proponentController.add(proposal, proponent1);
		
		Proponent proponentUpdated = this.createProponent();
		proponentUpdated.setMonthlyIncome(new BigDecimal("5000"));
		proponentUpdated.setName("BBB BBB");
		proponentUpdated.setAge(20);
		proponentUpdated.setId("proponent1");
		proponentController.update(proposal, proponentUpdated);
		
		
		Proponent proponentToCheck =  new ProponentDataProvider().getProponentByIdProposalAndIdProponent(proposal.getId(), proponentUpdated.getId());
		
		boolean assertResult = proponentToCheck.getAge() == proponentUpdated.getAge() &&
						proponentToCheck.getMonthlyIncome().compareTo(proponentUpdated.getMonthlyIncome()) == 0 &&
						proponentToCheck.getName().equals(proponentToCheck.getName()) ;
		
		assertTrue("Erro to update proponent from proposal", assertResult);		
	}
	
	@Test
	public void shouldTrueIfRemoveProponentFromProposal() {
		ProposalListSingleton.cleanData();
		ProponentService proponentController = new ProponentService();
		ProposalService proposalController = new ProposalService();
		
		Proposal proposal = this.createProposal();
		proposal.setId("proposal1");
		
		Proponent proponent1 = this.createProponent();
		proponent1.setId("proponent1");
		
		Proponent proponent2 = this.createProponent();
		proponent2.setId("proponent2");
		
		proposal.setProponents(new ArrayList<Proponent>());
		
		proposalController.create(proposal);
		proponentController.add(proposal, proponent1);
		proponentController.add(proposal, proponent2);
		
		proponentController.remove(proposal, proponent1);
		
		Proposal proposalReturn = ProposalListSingleton.getProposalById(proposal.getId());
		
		boolean assertResult = proposalReturn.getProponents().size() == 1 
								&& !proposalReturn.getProponents().get(0).getId().equals(proponent1.getId());
		
		assertTrue("Erro to remove proponent to proposal", assertResult);		
	}
	
	private Proposal createProposal() {
		Proposal proposal = new Proposal();
		proposal.setId("1");
		proposal.setLoanValue(new BigDecimal("10000.00"));
		proposal.setNumberOfMonthlyInstallments(10);
		proposal.setProponents(new ArrayList<Proponent>());

		return proposal;
	}
	
	private Proponent createProponent() {
		Proponent p1 = new Proponent();
		p1.setAge(51);
		p1.setMonthlyIncome(new BigDecimal("6000"));
		p1.setName("AAA AAA");
		p1.setMain(true);
		return p1;
	}
}
