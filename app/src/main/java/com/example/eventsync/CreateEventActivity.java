package com.example.eventsync;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventsync.models.Event;
import com.example.eventsync.viewmodel.EventViewModel;

import java.util.UUID;

public class CreateEventActivity extends AppCompatActivity {

    private EditText titleInput, dateInput, timeInput, locationInput, descriptionInput;
    private Button saveEventButton;
    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventViewModel = new EventViewModel(this);

        titleInput = findViewById(R.id.titleInput);
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        locationInput = findViewById(R.id.locationInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        saveEventButton = findViewById(R.id.saveEventButton);

        saveEventButton.setOnClickListener(view -> saveEvent());
    }

    private void saveEvent() {
        String title = titleInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        String time = timeInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();

        if (title.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String eventID = UUID.randomUUID().toString();
        String creatorID = "organizer1";

        Event event = new Event(eventID, title, date, time, location, description, creatorID);

        long result = eventViewModel.addEvent(event);

        if (result != -1) {
            Toast.makeText(this, "Event created successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error creating event", Toast.LENGTH_SHORT).show();
        }
    }
}