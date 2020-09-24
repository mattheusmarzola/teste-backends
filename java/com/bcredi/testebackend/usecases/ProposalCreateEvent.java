package com.bcredi.testebackend.usecases;

import java.math.BigDecimal;

import com.bcredi.testebackend.data.EventListSingleton;
import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.services.EventService;
import com.bcredi.testebackend.domain.services.ProposalService;

public class ProposalCreateEvent implements EventInterface{
	private Event event;
	private Proposal proposal;
	private final int _PROPOSAL_ID_POSITION = 4;
	private final int _LOAN_VALUE_POSITION = 5;
	private final int _NUMBER_OF_MONTHLY_INSTALLMENTS_POSITION = 6;
	
	
	@Override
	public void process() {
		if(this.isValid()) {
			EventService eventService = new EventService();
			eventService.create(this.event);
			
			ProposalService proposalService = new ProposalService();
			proposalService.create(proposal);
			this.event.setProposal(proposal);
		}
	}

	@Override
	public boolean isValid() {
		EventService eventService = new EventService();
		
		return eventService.isValidToProcess(this.event);
	}

	@Override
	public void parse(String data[]) {
		this.setProposal(new Proposal());
		
		BigDecimal loanValue = new BigDecimal(data[this._LOAN_VALUE_POSITION]);
		
		this.getProposal().setId(data[this._PROPOSAL_ID_POSITION]);
		this.getProposal().setNumberOfMonthlyInstallments(Integer.parseInt(data[this._NUMBER_OF_MONTHLY_INSTALLMENTS_POSITION]));
		this.getProposal().setLoanValue(loanValue);
	}

	
	
	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
	@Override
	public void setEvent(Event event) {
		this.event = event;
	}
}
