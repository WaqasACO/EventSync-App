package com.example.eventsync;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventsync.models.Event;
import com.example.eventsync.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class OrganizerDashboardActivity extends AppCompatActivity {

    private Button createEventButton;
    private ListView eventsListView;

    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_dashboard);

        createEventButton = findViewById(R.id.createEventButton);
        eventsListView = findViewById(R.id.eventsListView);

        eventViewModel = new EventViewModel(this);

        createEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(OrganizerDashboardActivity.this, CreateEventActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    private void loadEvents() {

        List<Event> events = eventViewModel.getAllEvents();

        List<String> eventTitles = new ArrayList<>();

        for (Event event : events) {
            eventTitles.add(event.getTitle() + " - " + event.getDate());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                eventTitles
        );

        eventsListView.setAdapter(adapter);

        eventsListView.setOnItemClickListener((parent, view, position, id) -> {
            Event selectedEvent = events.get(position);

            new android.app.AlertDialog.Builder(this)
                    .setTitle(selectedEvent.getTitle())
                    .setMessage(
                            "Date: " + selectedEvent.getDate() +
                                    "\nTime: " + selectedEvent.getTime() +
                                    "\nLocation: " + selectedEvent.getLocation() +
                                    "\n\nDescription:\n" + selectedEvent.getDescription()
                    )
                    .setPositiveButton("OK", null)
                    .show();
        });

        eventsListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Event selectedEvent = events.get(position);

            new android.app.AlertDialog.Builder(this)
                    .setTitle("Delete Event")
                    .setMessage("Do you want to delete this event?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        eventViewModel.deleteEvent(selectedEvent.getEventID());
                        loadEvents();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
    }
}