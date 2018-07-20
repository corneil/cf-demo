package com.github.corneil.cloudfoundry.demo.persistence;

import com.github.corneil.cloudfoundry.demo.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.stream.Stream;

public interface EventRepository extends CrudRepository<Event, String> {
    Stream<Event> findAllByEventSource(String eventSource);

    Stream<Event> findAllByEventDateBetween(Date startDate, Date endDate);

    Stream<Event> findAllByEventSourceAndEventDateBetween(String eventSource, Date startDate, Date endDate);
}
