package com.bcredi.testebackend.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.entities.Warranty;
import com.bcredi.testebackend.domain.services.WarrantyService;

public class WarrantyServiceTest {
	
	@Test
	public void shouldFalseIfHasLessThanMinWarratyNumber() {
		WarrantyService warrantyController = new WarrantyService();
		ArrayList<Warranty> warranties = new ArrayList<Warranty>();		
		
		String messageFail = "The number of warranties must be " + warrantyController._MIN_NUMBER_OF_WARRANTIES+" or more";
		assertFalse(messageFail, warrantyController.isNumberOfWarrantyValid(warranties));
	}

	@Test
	public void shouldTrueIfHasEnoughWarratyNumber() {
		WarrantyService warrantyController = new WarrantyService();
		ArrayList<Warranty> warranties = new ArrayList<Warranty>();		
		warranties.add(new Warranty());

		String messageFail = "The number of warranties must be " + warrantyController._MIN_NUMBER_OF_WARRANTIES+" or more";
		assertTrue(messageFail, warrantyController.isNumberOfWarrantyValid(warranties));
	}
	
	@Test
	public void shouldFalseIfTotalValueOfWarrantiesIsInsufficient() {
		WarrantyService warrantyController = new WarrantyService();
		Warranty warranty = new Warranty();
		warranty.setValue(new BigDecimal("10"));
		
		Warranty warranty2 = new Warranty();
		warranty2.setValue(new BigDecimal("15"));
		
		List<Warranty> warranties = Arrays.asList(warranty, warranty2);
		
		BigDecimal loanValue = new BigDecimal("100");
		
		String messageFail = "The total value of warranties must be "
				+ "(loan value *" + warrantyController._MULTIPLICATION_OF_THE_GUARANTEE_BY_THE_LOAN_VALUE+") or more";
		assertFalse(messageFail, warrantyController.isTotalValueOfWarrantiesValid(warranties, loanValue));
	}

	@Test
	public void shouldTrueIfTotalValueOfWarrantiesIsSufficient() {
		WarrantyService warrantyController = new WarrantyService();
		Warranty warranty = new Warranty();
		warranty.setValue(new BigDecimal("192.10"));
		
		Warranty warranty2 = new Warranty();
		warranty2.setValue(new BigDecimal("7.90"));
		
		List<Warranty> warranties = Arrays.asList(warranty, warranty2);
		
		BigDecimal loanValue = new BigDecimal("100");

		String messageFail = "The total value of warranties must be "
				+ "(total loan value * " + warrantyController._MULTIPLICATION_OF_THE_GUARANTEE_BY_THE_LOAN_VALUE+") or more";
		assertTrue(messageFail, warrantyController.isTotalValueOfWarrantiesValid(warranties, loanValue));
	}
	
	@Test
	public void shouldFalseIfHasInvalidProvince() {
		WarrantyService warrantyController = new WarrantyService();
		Warranty warranty = new Warranty();
		
		String messageFail = "The province cannot be "+warrantyController._NOT_ACCEPTED_PROVINCES.toString();
		assertFalse(messageFail, warrantyController.isAcceptableProvince(warranty));
	}
	
	@Test
	public void shouldFalseIfHasNotAcceptableProvince() {
		WarrantyService warrantyController = new WarrantyService();
		Warranty warranty = new Warranty();
		warranty.setProvince("PR");

		String messageFail = "The province cannot be "+warrantyController._NOT_ACCEPTED_PROVINCES.toString();
		assertFalse(messageFail, warrantyController.isAcceptableProvince(warranty));
	}

	@Test
	public void shouldTrueIfHasValidProvince() {
		WarrantyService warrantyController = new WarrantyService();
		Warranty warranty = new Warranty();
		warranty.setProvince("MG");
		
		String messageFail = "The province cannot be "+warrantyController._NOT_ACCEPTED_PROVINCES.toString();
		assertTrue(messageFail, warrantyController.isAcceptableProvince(warranty));
	}
	
	@Test
	public void shouldTrueIfAllProvincesValid() {
		WarrantyService warrantyController = new WarrantyService();
		Warranty warranty = new Warranty();
		warranty.setProvince("MG");
		
		Warranty warranty2 = new Warranty();
		warranty2.setProvince("SP");
		
		List<Warranty> warranties = Arrays.asList(warranty, warranty2);
		
		String messageFail = "The province cannot be "+warrantyController._NOT_ACCEPTED_PROVINCES.toString();
		assertTrue(messageFail, warrantyController.isAllProvicesValid(warranties));
	}
	
	@Test
	public void shouldFalseIfHasProvincesInvalid() {
		WarrantyService warrantyController = new WarrantyService();
		Warranty warranty = new Warranty();
		warranty.setProvince("MG");
		
		Warranty warranty2 = new Warranty();
		warranty2.setProvince("PR");
		
		List<Warranty> warranties = Arrays.asList(warranty, warranty2);
		
		String messageFail = "The province cannot be "+warrantyController._NOT_ACCEPTED_PROVINCES.toString();
		assertFalse(messageFail, warrantyController.isAllProvicesValid(warranties));
	}
	
}
