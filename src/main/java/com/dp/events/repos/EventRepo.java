package com.dp.events.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dp.events.models.Event;

@Repository
public interface EventRepo extends CrudRepository<Event, Long> {
	List<Event> findByState(String state);
	List<Event> findByStateNot(String state);
}
