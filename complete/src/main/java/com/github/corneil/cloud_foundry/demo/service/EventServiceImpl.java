package com.github.corneil.cloud_foundry.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.corneil.cloud_foundry.demo.config.MessageQueueNames;
import com.github.corneil.cloud_foundry.demo.model.Event;
import com.github.corneil.cloud_foundry.demo.model.EventService;
import com.github.corneil.cloud_foundry.demo.persistence.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper mapper;

    public EventServiceImpl(EventRepository eventRepository, AmqpTemplate amqpTemplate, ObjectMapper mapper) {
        this.eventRepository = eventRepository;
        this.amqpTemplate = amqpTemplate;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void createEvent(String eventSource) throws JsonProcessingException {
        Assert.notNull(eventSource, "eventSource Required");
        Event event = new Event(eventSource, new Date());
        log.info("createEvent:event:{}", event);
        if (amqpTemplate != null) {
            final String message = mapper.writeValueAsString(event);
            log.info("createEvent:message:{}", message);
            amqpTemplate.convertAndSend(MessageQueueNames.TOPIC_EXCHANGE_NAME, MessageQueueNames.ROUTING_KEY, message);
        } else {
            eventRepository.save(event);
        }
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
