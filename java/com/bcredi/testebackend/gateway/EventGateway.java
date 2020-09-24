package com.bcredi.testebackend.gateway;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.usecases.ProponentAddEvent;
import com.bcredi.testebackend.usecases.ProponentUpdateEvent;
import com.bcredi.testebackend.usecases.ProponentRemoveEvent;
import com.bcredi.testebackend.usecases.ProposalCreateEvent;
import com.bcredi.testebackend.usecases.ProposalDeleteEvent;
import com.bcredi.testebackend.usecases.ProposalUpdateEvent;
import com.bcredi.testebackend.usecases.WarrantyAddEvent;
import com.bcredi.testebackend.usecases.WarrantyUpdateEvent;
import com.bcredi.testebackend.usecases.WarrantyRemoveEvent;

public class EventGateway {
	private int _EVENT_ID_POSITION = 0;
	private int _EVENT_SCHEMA_POSITION = 1;
	private int _EVENT_ACTION_POSITION = 2;
	private int _EVENT_TIMESTAMP_POSITION = 3;
	
	public Event createEvent(String message) throws Exception {
		String[] data = message.split(",");
		String eventSchema = data[this._EVENT_SCHEMA_POSITION];
		String eventAction = data[this._EVENT_ACTION_POSITION];
		Event event = null;
		
		switch (eventSchema) {
			case "proposal":
				event = createProposalEvent(eventAction, data);
				break;
			case "proponent":
				event = createProponentEvent(eventAction, data);
				break;
			case "warranty":
				event = createWarrantyEvent(eventAction, data);
				break;
			default:
				throw new Exception("Invalid Event: " + eventSchema + "." + eventAction);
		}
		
		event.getEventAction().parse(data);
		event.getEventAction().setEvent(event);
		return event;

	}

	private Event createProposalEvent(String eventType, String[] data) throws Exception {
		Event event = parseEventData(data);
		
		switch (eventType) {
			case "created":
				event.setEventAction(new ProposalCreateEvent());
				break;
			case "deleted":
				event.setEventAction(new ProposalDeleteEvent());
				break;
			case "updated":
				event.setEventAction(new ProposalUpdateEvent());
				break;
			default:
				throw new Exception("Invalid Event: " + eventType);
		}
		
		return event;
	}

	

	private Event createProponentEvent(String eventType, String[] data) throws Exception {
		Event event = parseEventData(data);
		
		switch (eventType) {
			case "added":
				event.setEventAction(new ProponentAddEvent());
				break;
			case "removed":
				event.setEventAction(new ProponentRemoveEvent());
				break;
			case "updated":
				event.setEventAction(new ProponentUpdateEvent());
				break;
			default:
				throw new Exception("Invalid Event: " + eventType);
		}
		
		return event;
	}

	private Event createWarrantyEvent(String eventType, String[] data)  throws Exception {
		Event event = parseEventData(data);
		
		switch (eventType) {
			case "added":
				event.setEventAction(new WarrantyAddEvent());
				break;
			case "removed":
				event.setEventAction(new WarrantyRemoveEvent());
				break;
			case "updated":
				event.setEventAction(new WarrantyUpdateEvent());
				break;
			default:
				throw new Exception("Invalid Event: " + eventType);
		}
		
		return event;
	}

	private Event parseEventData(String[] data) {
		String eventId = data[this._EVENT_ID_POSITION];
		String eventSchema = data[this._EVENT_SCHEMA_POSITION];
		String eventAction = data[this._EVENT_ACTION_POSITION];
		LocalDateTime eventTimestamp = stringToLocalDateTime(data[this._EVENT_TIMESTAMP_POSITION]);
		
		
		Event event = new Event(eventId, eventSchema, eventAction, eventTimestamp);
		return event;
	}	private LocalDateTime stringToLocalDateTime(String timestamp) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
		return LocalDateTime.parse(timestamp, formatter);
	}

		
}
