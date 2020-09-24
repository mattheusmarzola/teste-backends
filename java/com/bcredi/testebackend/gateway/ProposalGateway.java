package com.bcredi.testebackend.gateway;

import java.util.List;

import com.bcredi.testebackend.data.EventListSingleton;
import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.services.ProposalService;

public class ProposalGateway {
	
	public List<Proposal> getAllValidProposal(){
		ProposalService proposalService = new ProposalService();
		
		List<Proposal> allValidProposal = proposalService.getAllValidProposal();
		List<Event> events = EventListSingleton.getInstance();
		for (Proposal p: allValidProposal) {
//			p.getWarranties().forEach(w -> System.out.println(w.getId()));
		}
		return allValidProposal;
	}
	
}
