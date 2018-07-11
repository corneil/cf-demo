package com.github.corneil.cloud_foundry.demo.repositories;

import com.github.corneil.cloud_foundry.demo.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.stream.Stream;

public interface EventRepository extends CrudRepository<Event, String> {
    Stream<Event> findByEventDateBetween(Date startDate, Date endDate);

    Stream<Event> findByEventSource(String eventSource);

    Stream<Event> findByEventSourceAndEventDateBetween(String eventSource, Date startDate, Date endDate);
}
