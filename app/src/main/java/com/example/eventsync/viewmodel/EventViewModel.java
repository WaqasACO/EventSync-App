package com.example.eventsync.viewmodel;

import com.example.eventsync.models.Event;
import com.example.eventsync.repository.EventRepository;

import java.util.ArrayList;

public class EventViewModel {

    private EventRepository repository;

    public EventViewModel() {
        repository = new EventRepository();
    }

    public void addEvent(Event event) {
        repository.addEvent(event);
    }

    public ArrayList<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    public void deleteEvent(String eventID) {
        repository.deleteEvent(eventID);
    }
}