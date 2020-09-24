package com.bcredi.testebackend.domain.services;

import java.util.Comparator;
import java.util.List;

import com.bcredi.testebackend.data.EventListSingleton;
import com.bcredi.testebackend.data.ProposalListSingleton;
import com.bcredi.testebackend.domain.entities.Event;

public class EventService {
	
	public boolean isValidToProcess(Event event) {
		
		boolean isEventAlreadyProcessed = this.isEventAlreadyProcessed(event);
		boolean isLatestEvent = this.isLatestEvent(event);
		
		return isLatestEvent && !isEventAlreadyProcessed;
	}
	
	public boolean isEventAlreadyProcessed(Event event) {
		if(EventListSingleton.getEventById(event.getId()) != null) {
			return true;
		}
		
		return false;
	}

	public boolean isLatestEvent(Event event) {
		List<Event> eventsFilteredBySchemaAndAction = EventListSingleton
														.getEventsByProposalSchemaAndAction(event);
		
		if(eventsFilteredBySchemaAndAction == null || eventsFilteredBySchemaAndAction.isEmpty()) {
			return true;
		}
		
		Event latestEventAlreadyProcessed = this.getLastestEventProcessed(eventsFilteredBySchemaAndAction);
		if(latestEventAlreadyProcessed == null) {
			return false;
		}
		
		if(event.getTimestamp().compareTo(latestEventAlreadyProcessed.getTimestamp()) > 0) {
			return true;
		}
		
		return false;
	}
	
	private Event getLastestEventProcessed(List<Event> events) {
		if(events.size() == 0) return null;
		
		Event event = events.stream()
						.max(Comparator.comparing(Event::getTimestamp))
						.get();
		return event;
	}
	
	public void create(Event event) {
		EventListSingleton.getInstance().add(event);
	}
}
