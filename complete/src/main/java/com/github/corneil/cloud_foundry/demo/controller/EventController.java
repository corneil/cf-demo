package com.github.corneil.cloud_foundry.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.corneil.cloud_foundry.demo.model.Event;
import com.github.corneil.cloud_foundry.demo.model.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/event")
@Slf4j
public class EventController {

	private EventService eventService;

	@Autowired
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@RequestMapping(path = "/{eventSource}", method = RequestMethod.POST)
	public ResponseEntity createEvent(@PathVariable("eventSource") String eventSource) {
		log.info("createEvent:{}", eventSource);
		try {
			eventService.createEvent(eventSource);
		} catch (JsonProcessingException e) {
			log.error("createEvent:exception:" + e, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
		}
		return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getEvents() {
		log.info("getEvents");
		return ResponseEntity.ok(eventService.listAll().collect(Collectors.toList()));
	}

	@RequestMapping(path = "/{eventSource}", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> getEventsBySource(@PathVariable("eventSource") String eventSource) {
		log.info("getEventsBySource:{}", eventSource);
		return ResponseEntity.ok(eventService.listByEventSource(eventSource).collect(Collectors.toList()));
	}
}
