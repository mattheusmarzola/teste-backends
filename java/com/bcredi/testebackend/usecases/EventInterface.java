package com.bcredi.testebackend.usecases;

import com.bcredi.testebackend.domain.entities.Event;

public interface EventInterface {
	void parse(String data[]);
	void process();
	boolean isValid();
	void setEvent(Event event);
}
