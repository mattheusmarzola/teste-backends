package com.bcredi.testebackend.data;

import java.util.ArrayList;

import com.bcredi.testebackend.domain.entities.Proposal;
import com.bcredi.testebackend.domain.entities.Warranty;

public class WarrantyDataProvider {
	
	public Warranty getWarrantyByIdProposalAndIdWarranty(String idProposal, String idWarranty) {
		Proposal proposal = ProposalListSingleton.getProposalById(idProposal);
		if(proposal.getWarranties() != null && !proposal.getWarranties().isEmpty()) {
			return proposal.getWarranties().stream().filter(w -> w.getId().equals(idWarranty)).findAny().get();
		}
		
		return null;
	}
	
	public void addWarranty(String idProposal, Warranty warranty) {
		Proposal proposal = ProposalListSingleton.getProposalById(idProposal);
		if(proposal != null) {
			if(proposal.getWarranties() == null) proposal.setWarranties(new ArrayList());

			proposal.getWarranties().add(warranty);
		}
	}

	public void removeWarranty(String idProposal, String idWarranty) {
		Proposal proposal = ProposalListSingleton.getProposalById(idProposal);
		if(proposal.getWarranties() != null && !proposal.getWarranties().isEmpty()) {
			proposal.getWarranties().removeIf(w -> w.getId().equals(idWarranty));
		}
	}
}
