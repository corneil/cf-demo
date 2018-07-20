package com.github.corneil.cloudfoundry.demo.persistence;

import com.github.corneil.cloudfoundry.demo.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, String> {
    List<Event> findAllByEventSource(String eventSource);

    List<Event> findAllByEventDateBetween(Date startDate, Date endDate);

    List<Event> findAllByEventSourceAndEventDateBetween(String eventSource, Date startDate, Date endDate);
}
