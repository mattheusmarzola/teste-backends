package com.bcredi.testebackend.usecases;

import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.entities.Warranty;
import com.bcredi.testebackend.domain.services.EventService;
import com.bcredi.testebackend.domain.services.WarrantyService;

public class WarrantyRemoveEvent implements EventInterface{
	private Event event;
	private Warranty warranty;
	private Proposal proposal;
	private final int _PROPOSAL_ID_POSITION = 4;
	private final int _WARRANTY_ID_POSITION = 5;
	
	
	@Override
	public void process() {
		if(this.isValid()) {
			EventService eventService = new EventService();
			eventService.create(this.event);
			
			WarrantyService warrantyService = new WarrantyService();
			warrantyService.remove(this.getProposal(), this.getWarranty());
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
		
		
		this.getProposal().setId(data[this._PROPOSAL_ID_POSITION]);
		this.getWarranty().setId(data[this._WARRANTY_ID_POSITION]);
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
