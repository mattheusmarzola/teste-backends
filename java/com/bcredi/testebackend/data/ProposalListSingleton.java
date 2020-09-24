package com.bcredi.testebackend.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.entities.Proposal;

public class ProposalListSingleton {

	private static List<Proposal> proposalList;

	private ProposalListSingleton() {
	}

	public static synchronized List<Proposal> getInstance() {
		if (proposalList == null)
			proposalList = new ArrayList<Proposal>();

		return proposalList;
	}
	
	public static Proposal getProposalById(String id) {
		Optional<Proposal> proposalOptional = ProposalListSingleton.getInstance()
						.stream()
						.parallel()
						.filter(e -> e.getId().equals(id))
						.findAny();
		
		if(proposalOptional.isPresent()) {
			return proposalOptional.get();
		}
		
		return null;
	}
	
	public static void cleanData() {
		proposalList = new ArrayList<Proposal>();
	}
}
