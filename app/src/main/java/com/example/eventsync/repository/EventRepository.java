package com.example.eventsync.repository;

import android.content.Context;

import com.example.eventsync.database.DatabaseHelper;
import com.example.eventsync.models.Event;

import java.util.List;

public class EventRepository {

    private DatabaseHelper databaseHelper;

    public EventRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long addEvent(Event event) {
        return databaseHelper.insertEvent(event);
    }

    public List<Event> getAllEvents() {
        return databaseHelper.getAllEvents();
    }

    public int deleteEvent(String eventID) {
        return databaseHelper.deleteEvent(eventID);
    }
}