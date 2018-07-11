package com.github.corneil.cloud_foundry.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.corneil.cloud_foundry.demo.model.Event;
import com.github.corneil.cloud_foundry.demo.model.EventService;
import com.github.corneil.cloud_foundry.demo.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private AmqpTemplate amqpTemplate;
    private ObjectMapper mapper;

    public EventServiceImpl(EventRepository eventRepository, AmqpTemplate amqpTemplate, ObjectMapper mapper) {
        this.eventRepository = eventRepository;
        this.amqpTemplate = amqpTemplate;
        this.mapper = mapper;
    }

    @Override
    public Event createEvent(String eventSource) throws JsonProcessingException {
        Assert.notNull(eventSource, "eventSource Required");
        Event event = new Event(UUID.randomUUID().toString(), eventSource, new Date());
        log.info("createEvent:{}", event);
        amqpTemplate.convertAndSend("cf-demo-queue", mapper.writeValueAsString(event));
        log.info("createEvent:sent");
        return event;
    }

    @Override
    public Stream<Event> listAll() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false);
    }

    @Override
    public Stream<Event> listAll(final Date startDate, final Date endDate) {
        return eventRepository.findByEventDateBetween(startDate, endDate);
    }

    @Override
    public Stream<Event> listByEventSource(final String eventSource) {
        Assert.notNull(eventSource, "eventSource Required");
        return eventRepository.findByEventSource(eventSource);
    }

    @Override
    public Stream<Event> listByEventSource(String eventSource, Date startDate, Date endDate) {
        return eventRepository.findByEventSourceAndEventDateBetween(eventSource, startDate, endDate);
    }

    @RabbitListener(queues = "cf-demo-queue")
    @Transactional
    public void handleEvent(String json) throws IOException {
        log.info("handleEvent:{}", json);
        Event event = mapper.readValue(json, Event.class);
        eventRepository.save(event);
        log.info("handleEvent:saved:{}", event);
    }
}
