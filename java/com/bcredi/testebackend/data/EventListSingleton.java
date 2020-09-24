package com.bcredi.testebackend.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bcredi.testebackend.domain.entities.Event;

public class EventListSingleton {

	private static List<Event> eventList;

	private EventListSingleton() {
	}

	public static synchronized List<Event> getInstance() {
		if (eventList == null)
			eventList = new ArrayList<Event>();

		return eventList;
	}
	
	public static Event getEventById(String id) {
		Optional<Event> eventOptional = EventListSingleton.getInstance()
						.stream()
						.parallel()
						.filter(e -> e.getId().equals(id))
						.findAny();
		
		if(eventOptional.isPresent()) {
			return eventOptional.get();
		}
		
		return null;
	}
	
	public static List<Event> getEventsByProposalSchemaAndAction(Event event) {
		List<Event> events = null;
		try {
			events = EventListSingleton.getInstance()
							.stream()
							.parallel()
							.filter(e -> e.getProposal().getId().equals(event.getProposal().getId()))
							.filter(e -> e.getSchema().equals(event.getSchema()) && e.getAction().equals(event.getAction()))
							.collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<Event>();
		}
		
		if(events != null) {
			return events;
		}
		
		return new ArrayList<Event>();
	}
	
	public static void cleanData() {
		eventList = new ArrayList<Event>();
	}
}
