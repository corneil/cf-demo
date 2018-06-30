package com.github.corneil.cloud_foundry.demo.service;

import com.github.corneil.cloud_foundry.demo.model.Event;
import com.github.corneil.cloud_foundry.demo.model.EventService;
import com.github.corneil.cloud_foundry.demo.persistence.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EventServiceImpl implements EventService {
	private EventRepository eventRepository;

	@Autowired
	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	@Transactional
	public void createEvent(String eventSource) {
		Assert.notNull(eventSource, "eventSource Required");
		eventRepository.save(new Event(eventSource, new Date()));
	}

	@Override
	public Stream<Event> listAll() {
		return StreamSupport.stream(eventRepository.findAll().spliterator(), false);
	}

	@Override
	public Stream<Event> listAll(final Date startDate, final Date endDate) {
		return eventRepository.findAllByEventDateBetween(startDate, endDate);
	}

	@Override
	public Stream<Event> listByEventSource(final String eventSource) {
		Assert.notNull(eventSource, "eventSource Required");
		return eventRepository.findAllByEventSource(eventSource);
	}

	@Override
	public Stream<Event> listByEventSource(String eventSource, Date startDate, Date endDate) {
		return eventRepository.findAllByEventSourceAndEventDateBetween(eventSource, startDate, endDate);
	}
}
