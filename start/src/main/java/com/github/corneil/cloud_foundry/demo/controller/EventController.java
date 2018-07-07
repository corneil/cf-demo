package com.github.corneil.cloud_foundry.demo.controller;

import com.github.corneil.cloud_foundry.demo.model.Event;
import com.github.corneil.cloud_foundry.demo.model.EventService;
import com.github.corneil.cloud_foundry.demo.util.TimeRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
    public ResponseEntity<String> createEvent(@PathVariable("eventSource") String eventSource) {
        log.info("createEvent:{}", eventSource);
        return ResponseEntity.ok(eventService.createEvent(eventSource));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEvents(@RequestParam(name = "period", required = false) String period) {
        log.info("getEvents:{}", period);
        if (StringUtils.isEmpty(period)) {
            return ResponseEntity.ok(eventService.listAll().collect(Collectors.toList()));
        } else {
            Pair<Date, Date> range = TimeRange.createStartAndEndDates(new Date(), period);
            return ResponseEntity.ok(eventService.listAll(range.getFirst(), range.getSecond()).collect(Collectors.toList()));
        }
    }

    @RequestMapping(path = "/{eventSource}", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEventsBySource(@PathVariable("eventSource") String eventSource,
                                                         @RequestParam(name = "period", required = false) String period) {
        log.info("getEventsBySource:{}", eventSource);
        if (StringUtils.isEmpty(period)) {
            return ResponseEntity.ok(eventService.listByEventSource(eventSource).collect(Collectors.toList()));
        } else {
            Pair<Date, Date> range = TimeRange.createStartAndEndDates(new Date(), period);
            return ResponseEntity.ok(eventService.listByEventSource(eventSource, range.getFirst(), range.getSecond()).collect(Collectors.toList()));
        }
    }
}
