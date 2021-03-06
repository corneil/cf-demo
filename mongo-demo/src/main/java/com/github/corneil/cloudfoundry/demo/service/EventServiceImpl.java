package com.github.corneil.cloudfoundry.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.corneil.cloudfoundry.demo.model.Event;
import com.github.corneil.cloudfoundry.demo.model.EventService;
import com.github.corneil.cloudfoundry.demo.persistence.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
    public Event createEvent(String eventSource) throws JsonProcessingException {
        Assert.notNull(eventSource, "eventSource Required");
        Event event = new Event(UUID.randomUUID().toString(), eventSource, new Date());
        log.info("createEvent:event:{}", event);
        if (amqpTemplate != null) {
            final String message = mapper.writeValueAsString(event);
            log.info("createEvent:message:{}", message);
            amqpTemplate.convertAndSend("cf-demo-queue", message);
            return event;
        } else {
            return eventRepository.save(event);
        }
    }


    @Transactional
    @RabbitListener(queues = "cf-demo-queue")
    public void receiveMessage(@Payload String message) throws IOException {
        log.info("receiveMessage:message={}", message);
        Event event = mapper.readValue(message, Event.class);
        log.info("receiveMessage:event:{}", event);
        eventRepository.save(event);
        log.info("receiveMessage:saved:event:{}", event);
    }

    @Override
    public List<Event> listAll() {
        return StreamSupport.stream(eventRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Event> listAll(final Date startDate, final Date endDate) {

        return StreamSupport.stream(eventRepository.findAllByEventDateBetween(startDate, endDate).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Event> listByEventSource(final String eventSource) {
        Assert.notNull(eventSource, "eventSource Required");
        return StreamSupport.stream(eventRepository.findAllByEventSource(eventSource).spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public List<Event> listByEventSource(String eventSource, Date startDate, Date endDate) {
        return StreamSupport.stream(eventRepository.findAllByEventSourceAndEventDateBetween(eventSource, startDate, endDate).spliterator(), false).collect(Collectors.toList());
    }
}
