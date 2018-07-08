package com.github.corneil.cloud_foundry.demo.service;

import com.github.corneil.cloud_foundry.demo.model.Event;
import com.github.corneil.cloud_foundry.demo.model.EventService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class EventServiceImpl implements EventService {
    List<Event> eventList = new LinkedList<>();

    @Override
    public Event createEvent(String eventSource) {
        Assert.notNull(eventSource, "eventSource Required");
        Event event = new Event(UUID.randomUUID().toString(), eventSource, new Date());
        eventList.add(event);
        return event;
    }

    @Override
    public Stream<Event> listAll() {
        return eventList.stream();
    }

    @Override
    public Stream<Event> listAll(final Date startDate, final Date endDate) {
        return eventList.stream().filter(event ->
            ((startDate == null) || startDate.before(event.getEventDate())) &&
                ((endDate == null) || endDate.after(event.getEventDate()))
        );
    }

    @Override
    public Stream<Event> listByEventSource(final String eventSource) {
        Assert.notNull(eventSource, "eventSource Required");
        return eventList.stream().filter(event -> event.getEventSource().equals(eventSource));
    }

    @Override
    public Stream<Event> listByEventSource(String eventSource, Date startDate, Date endDate) {
        return eventList.stream().filter(event ->
            (event.getEventSource().equals(eventSource)) &&
                ((startDate == null) || startDate.before(event.getEventDate())) &&
                ((endDate == null) || endDate.after(event.getEventDate()))
        );
    }
}
