package com.github.corneil.cloudfoundry.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;
import java.util.List;

public interface EventService {

    Event createEvent(String eventSource) throws JsonProcessingException;

    List<Event> listAll();

    List<Event> listAll(Date startDate, Date endDate);

    List<Event> listByEventSource(String eventSource);

    List<Event> listByEventSource(String eventSource, Date startDate, Date endDate);
}
