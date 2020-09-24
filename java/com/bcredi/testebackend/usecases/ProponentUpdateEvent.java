package com.bcredi.testebackend.usecases;

import java.math.BigDecimal;

import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.entities.Proponent;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.services.EventService;
import com.bcredi.testebackend.domain.services.ProponentService;

public class ProponentUpdateEvent implements EventInterface{
	private Event event;
	private Proponent proponent;
	private Proposal proposal;
	private final int _PROPOSAL_ID_POSITION = 4;
	private final int _PROPONENT_ID_POSITION = 5;
	private final int _PROPONENT_NAME_POSITION = 6;
	private final int _PROPONENT_AGE_POSITION = 7;
	private final int _PROPONENT_MONTHLY_INCOME_POSITION = 8;
	private final int _PROPONENT_IS_MAIN_POSITION = 9;
	
	@Override
	public void process() {
		if(this.isValid()) {
			EventService eventService = new EventService();
			eventService.create(this.event);
			
			ProponentService proponentService = new ProponentService();
			proponentService.update(this.getProposal(), this.getProponent());
			this.event.setProposal(proposal);
		}
	}

	@Override
	public boolean isValid() {
		EventService eventService = new EventService();
		boolean isValidToProcess = eventService.isValidToProcess(this.event);
		
		return isValidToProcess;
	}

	@Override
	public void parse(String data[]) {
		this.setProposal(new Proposal());
		this.setProponent(new Proponent());
		
		BigDecimal monthlyIncomeValue = new BigDecimal(data[this._PROPONENT_MONTHLY_INCOME_POSITION]);

		
		this.getProposal().setId(data[this._PROPOSAL_ID_POSITION]);
		this.getProponent().setId(data[this._PROPONENT_ID_POSITION]);
		this.getProponent().setName(data[this._PROPONENT_NAME_POSITION]);
		this.getProponent().setAge(Integer.parseInt(data[this._PROPONENT_AGE_POSITION]));
		this.getProponent().setMain(Boolean.parseBoolean(data[this._PROPONENT_IS_MAIN_POSITION]));
		this.getProponent().setMonthlyIncome(monthlyIncomeValue);
	}

	
	
	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public Proponent getProponent() {
		return proponent;
	}

	public void setProponent(Proponent proponent) {
		this.proponent = proponent;
	}
	@Override
	public void setEvent(Event event) {
		this.event = event;
	}

}
