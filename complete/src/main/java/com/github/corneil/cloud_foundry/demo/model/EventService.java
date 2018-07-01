package com.github.corneil.cloud_foundry.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;
import java.util.stream.Stream;

public interface EventService {
	void createEvent(String eventSource) throws JsonProcessingException;

	Stream<Event> listAll();

	Stream<Event> listAll(Date startDate, Date endDate);

	Stream<Event> listByEventSource(String eventSource);

	Stream<Event> listByEventSource(String eventSource, Date startDate, Date endDate);
}
