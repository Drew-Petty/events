package com.dp.events.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dp.events.models.Event;
import com.dp.events.models.User;
import com.dp.events.repos.EventRepo;

@Service
public class EventService {
	private final EventRepo eventRepo;
	public EventService(EventRepo eventRepo) {
		this.eventRepo = eventRepo;
	}
	public List<Event> allEvents(){
		return (List<Event>) eventRepo.findAll();
	}
	public Event saveEvent(Event event) {
		return eventRepo.save(event);
	}
	public Event findEventById(Long id) {
		Optional<Event> optionalEvent = eventRepo.findById(id);
		if(optionalEvent.isPresent()) {
			return optionalEvent.get();
		}else {
			return null;
		}
	}
	public void deleteEvent(Long id) {
		eventRepo.deleteById(id);
	}
	public List<Event> inStateEvents(String state){
		return eventRepo.findByState(state);
	}
	public List<Event> outOfStateEvents(String state){
		return eventRepo.findByStateNot(state);
	}
	public void addComment(Event event,String comment) {
		ArrayList<String> commentList = event.getMessages();
		commentList.add(comment);
		event.setMessages(commentList);
		eventRepo.save(event);
	}
	public void addHost(Event event,User user) {
		event.setHost(user);
		eventRepo.save(event);
	}
	public void joinEvent(Event event,User user) {
		List<User> attendees = event.getUsers();
		attendees.add(user);
		event.setUsers(attendees);
		eventRepo.save(event);
	}
	public void leaveEvent(Event event, User user) {
		List<User> attendees = event.getUsers();		
		attendees.remove(user);
		System.out.println(attendees);
		event.setUsers(attendees);
		eventRepo.save(event);
	}
}
