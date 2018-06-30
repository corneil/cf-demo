package com.github.corneil.cloud_foundry.demo.model;

import java.util.Date;
import java.util.stream.Stream;

public interface EventService {
	void createEvent(String eventSource);

	Stream<Event> listAll();

	Stream<Event> listAll(Date startDate, Date endDate);

	Stream<Event> listByEventSource(String eventSource);

	Stream<Event> listByEventSource(String eventSource, Date startDate, Date endDate);
}
