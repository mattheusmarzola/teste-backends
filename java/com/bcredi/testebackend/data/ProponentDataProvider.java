package com.bcredi.testebackend.data;

import java.util.ArrayList;

import com.bcredi.testebackend.domain.entities.Proponent;
import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.entities.Warranty;

public class ProponentDataProvider {
	
	public Proponent getProponentByIdProposalAndIdProponent(String idProposal, String idProponent) {
		Proposal proposal = ProposalListSingleton.getProposalById(idProposal);
		if(proposal.getProponents() != null && !proposal.getProponents().isEmpty()) {
			return proposal.getProponents().stream().filter(p -> p.getId().equals(idProponent)).findAny().get();
		}
		
		return null;
	}
	
	public void addProponent(String idProposal, Proponent proponent) {
		Proposal proposal = ProposalListSingleton.getProposalById(idProposal);
		if(proposal != null) {
			if(proposal.getProponents() == null) { proposal.setProponents(new ArrayList<Proponent>());}
			
			proposal.getProponents().add(proponent);
		}
	}

	public void removeProponent(String idProposal, String idProponent) {
		Proposal proposal = ProposalListSingleton.getProposalById(idProposal);
		if(proposal.getProponents() != null && !proposal.getProponents().isEmpty()) {
			proposal.getProponents().removeIf(p -> p.getId().equals(idProponent));
		}
	}
}
