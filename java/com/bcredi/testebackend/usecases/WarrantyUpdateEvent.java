package com.bcredi.testebackend.usecases;

import java.math.BigDecimal;
import java.util.Arrays;

import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.entities.Warranty;
import com.bcredi.testebackend.domain.services.EventService;
import com.bcredi.testebackend.domain.services.ProposalService;
import com.bcredi.testebackend.domain.services.WarrantyService;

public class WarrantyUpdateEvent implements EventInterface{
	private Event event;
	private Warranty warranty;
	private Proposal proposal;
	private final int _PROPOSAL_ID_POSITION = 4;
	private final int _WARRANTY_ID_POSITION = 5;
	private final int _WARRANTY_VALUE_POSITION = 6;
	private final int _WARRANTY_PROVINCE_POSITION = 7;
	
	
	@Override
	public void process() {
		if(this.isValid()) {
			EventService eventService = new EventService();
			eventService.create(this.event);

			WarrantyService warrantyService = new WarrantyService();
			warrantyService.update(this.getProposal(), this.getWarranty());
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
		this.setWarranty(new Warranty());
		BigDecimal warrantyValue = new BigDecimal(data[this._WARRANTY_VALUE_POSITION]);
		
		this.getProposal().setId(data[this._PROPOSAL_ID_POSITION]);
		this.getWarranty().setProvince(data[this._WARRANTY_PROVINCE_POSITION]);
		this.getWarranty().setId(data[this._WARRANTY_ID_POSITION]);
		this.getWarranty().setValue(warrantyValue);
	}

	
	
	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public Warranty getWarranty() {
		return warranty;
	}

	public void setWarranty(Warranty warranty) {
		this.warranty = warranty;
	}
	@Override
	public void setEvent(Event event) {
		this.event = event;
	}
}
