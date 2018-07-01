package com.github.corneil.cloud_foundry.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.corneil.cloud_foundry.demo.model.Event;
import com.github.corneil.cloud_foundry.demo.persistence.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
public class EventListener {
	ObjectMapper mapper;
	EventRepository eventRepository;

	@Autowired
	public EventListener(ObjectMapper mapper, EventRepository eventRepository) {
		this.mapper = mapper;
		this.eventRepository = eventRepository;
	}

	public void receiveMessage(final String message) throws IOException {
		log.info("receiveMessage:message={}", message);
		Event event = mapper.readValue(message, Event.class);
		log.info("receiveMessage:event:{}", event);
		eventRepository.save(event);
		log.info("receiveMessage:saved:event:{}", event);
	}
}
