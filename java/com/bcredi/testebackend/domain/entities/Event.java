package com.bcredi.testebackend.domain.entities;

import java.time.LocalDateTime;

import com.bcredi.testebackend.usecases.EventInterface;

public class Event {
	private String id;
	private String schema;
	private String action;
	private LocalDateTime timestamp;
	private EventInterface eventAction;
	private Proposal proposal;
	
	public Event(String id, String schema, String action, LocalDateTime timestamp) {
		this.id = id;
		this.schema = schema;
		this.action = action;
		this.timestamp = timestamp;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public EventInterface getEventAction() {
		return eventAction;
	}
	public void setEventAction(EventInterface event) {
		this.eventAction = event;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
}
