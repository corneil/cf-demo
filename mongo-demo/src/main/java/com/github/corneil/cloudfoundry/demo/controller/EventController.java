package com.github.corneil.cloudfoundry.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.corneil.cloudfoundry.demo.model.Event;
import com.github.corneil.cloudfoundry.demo.model.EventService;
import com.github.corneil.cloudfoundry.demo.util.TimeRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
    @Transactional
    public ResponseEntity<Event> createEvent(@PathVariable("eventSource") String eventSource) throws JsonProcessingException {
        log.info("createEvent:{}", eventSource);
        return ResponseEntity.ok(eventService.createEvent(eventSource));
    }

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public ResponseEntity<List<Event>> getEvents(@RequestParam(name = "period", required = false) String period) {
        log.info("getEvents:{}", period);
        if (StringUtils.isEmpty(period)) {
            return ResponseEntity.ok(eventService.listAll());
        } else {
            Pair<Date, Date> range = TimeRange.createStartAndEndDates(new Date(), period);
            return ResponseEntity.ok(eventService.listAll(range.getFirst(), range.getSecond()));
        }
    }

    @RequestMapping(path = "/{eventSource}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public ResponseEntity<List<Event>> getEventsBySource(@PathVariable("eventSource") String eventSource,
                                                         @RequestParam(name = "period", required = false) String period) {
        log.info("getEventsBySource:{}", eventSource);
        if (StringUtils.isEmpty(period)) {
            return ResponseEntity.ok(eventService.listByEventSource(eventSource));
        } else {
            Pair<Date, Date> range = TimeRange.createStartAndEndDates(new Date(), period);
            return ResponseEntity.ok(eventService.listByEventSource(eventSource, range.getFirst(), range.getSecond()));
        }
    }
}
