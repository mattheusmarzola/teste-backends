package com.bcredi.testebackend.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import com.bcredi.testebackend.data.EventListSingleton;
import com.bcredi.testebackend.domain.entities.Event;
import com.bcredi.testebackend.domain.services.EventService;

public class EventServiceTest {

	public Event createEvent() {
		String id = "event1";
		String schema = "proposal";
		String action = "created";
		LocalDateTime timestamp = LocalDateTime.now();
		
		return new Event(id, schema, action, timestamp);
	}
	
	@Test
	public void shouldTrueIfEventIsValidToProcess() {
		EventListSingleton.cleanData();
		EventService eventService = new EventService();
		Event event = this.createEvent();
		
		assertTrue("Event should valid to process.", eventService.isValidToProcess(event));		
	}
	
	@Test
	public void shouldFalseIfEventIsValidToProcess() {
		EventListSingleton.cleanData();
		EventService eventService = new EventService();
		Event event1 = this.createEvent();
		Event event2 = this.createEvent();
		
		eventService.create(event1);
		
		assertFalse("Event should invalid to process.", eventService.isValidToProcess(event2));		
	}
	
	@Test
	public void shouldTrueIfEventAlreadyProcessed() {
		EventListSingleton.cleanData();
		EventService eventService = new EventService();
		Event event1 = this.createEvent();
		
		eventService.create(event1);
		
		assertTrue("Event should invalid to process.", eventService.isEventAlreadyProcessed(event1));		
	}
	
	@Test
	public void shouldTrueIfIsLatestEvent() {
		EventListSingleton.cleanData();
		EventService eventService = new EventService();
		Event event1 = this.createEvent();
		event1.setTimestamp(LocalDateTime.of(2020, 9, 10, 10, 10, 10));
		
		Event event2IsLatest = this.createEvent();
		event2IsLatest.setTimestamp(LocalDateTime.of(2020, 9, 10, 12, 20, 10));
		
		Event event3 = this.createEvent();
		event3.setTimestamp(LocalDateTime.of(2020, 9, 10, 10, 30, 10));
		
		eventService.create(event1);
		eventService.create(event2IsLatest);
		eventService.create(event3);
		
		assertTrue("Event should be lastest event.", eventService.isLatestEvent(event2IsLatest));		
	}
	
	@Test
	public void shouldFalseIfIsLatestEvent() {
		EventListSingleton.cleanData();
		EventService eventService = new EventService();
		Event event1 = this.createEvent();
		event1.setTimestamp(LocalDateTime.of(2020, 9, 10, 10, 10, 10));
		
		Event event2IsLatest = this.createEvent();
		event2IsLatest.setTimestamp(LocalDateTime.of(2020, 9, 10, 12, 20, 10));
		
		Event event3 = this.createEvent();
		event3.setTimestamp(LocalDateTime.of(2020, 9, 10, 10, 30, 10));
		
		eventService.create(event1);
		eventService.create(event2IsLatest);
		eventService.create(event3);
		
		assertTrue("Event should not be lastest event.", eventService.isLatestEvent(event3));		
	}
	
	@Test
	public void shouldTrueIfIsLatestEventProcessed() {
		EventListSingleton.cleanData();
		EventService eventService = new EventService();
		Event event1 = this.createEvent();
		event1.setTimestamp(LocalDateTime.of(2020, 9, 10, 10, 10, 10));
		
		Event event2IsLatest = this.createEvent();
		event2IsLatest.setTimestamp(LocalDateTime.of(2020, 9, 10, 12, 20, 10));
		
		Event event3 = this.createEvent();
		event3.setTimestamp(LocalDateTime.of(2020, 9, 10, 10, 30, 10));
		
		eventService.create(event1);
		eventService.create(event2IsLatest);
		eventService.create(event3);
		
		assertTrue("Event should be lastest event processed.", eventService.isLatestEvent(event2IsLatest));		
	}
}
