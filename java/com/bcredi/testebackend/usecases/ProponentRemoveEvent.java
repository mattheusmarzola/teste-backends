package com.bcredi.testebackend.usecases;

import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.entities.Proponent;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.services.EventService;
import com.bcredi.testebackend.domain.services.ProponentService;

public class ProponentRemoveEvent implements EventInterface{
	private Event event;
	private Proponent proponent;
	private Proposal proposal;
	private final int _PROPOSAL_ID_POSITION = 4;
	private final int _PROPONENT_ID_POSITION = 5;
	
	@Override
	public void process() {
		if(this.isValid()) {
			EventService eventService = new EventService();
			eventService.create(this.event);
			
			ProponentService proponentService = new ProponentService();
			proponentService.remove(this.getProposal(), this.getProponent());
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
		
		this.getProposal().setId(data[this._PROPOSAL_ID_POSITION]);
		this.getProponent().setId(data[this._PROPONENT_ID_POSITION]);
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
