package com.example.eventsync.viewmodel;

import android.content.Context;

import com.example.eventsync.models.Event;
import com.example.eventsync.repository.EventRepository;

import java.util.List;

public class EventViewModel {

    private EventRepository repository;

    public EventViewModel(Context context) {
        repository = new EventRepository(context);
    }

    public long addEvent(Event event) {
        return repository.addEvent(event);
    }

    public List<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    public int deleteEvent(String eventID) {
        return repository.deleteEvent(eventID);
    }
}