package com.example.eventsync;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventsync.database.DatabaseHelper;
import com.example.eventsync.models.Event;

import java.util.ArrayList;
import java.util.List;

public class ParticipantDashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerEvents;
    private EventAdapter eventAdapter;
    private List<Event> allEvents;
    private List<Event> filteredEvents;
    private DatabaseHelper databaseHelper;
    private TextView textEmpty;
    private EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_dashboard);

        String username = getIntent().getStringExtra("username");

        recyclerEvents = findViewById(R.id.recyclerEvents);
        textEmpty = findViewById(R.id.textEmpty);
        editSearch = findViewById(R.id.editSearch);
        TextView textWelcome = findViewById(R.id.textWelcome);

        if (username != null) {
            textWelcome.setText("Hi, " + username);
        }

        databaseHelper = new DatabaseHelper(this);
        allEvents = databaseHelper.getAllEvents();
        filteredEvents = new ArrayList<>(allEvents);

        eventAdapter = new EventAdapter(this, filteredEvents);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(this));
        recyclerEvents.setAdapter(eventAdapter);

        if (allEvents.isEmpty()) {
            textEmpty.setVisibility(View.VISIBLE);
            recyclerEvents.setVisibility(View.GONE);
        }

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterEvents(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterEvents(String query) {
        filteredEvents = new ArrayList<>();

        if (query.isEmpty()) {
            filteredEvents.addAll(allEvents);
        } else {
            String lowerQuery = query.toLowerCase();
            for (Event event : allEvents) {
                if (event.getTitle().toLowerCase().contains(lowerQuery)
                        || event.getLocation().toLowerCase().contains(lowerQuery)
                        || event.getDate().toLowerCase().contains(lowerQuery)) {
                    filteredEvents.add(event);
                }
            }
        }

        eventAdapter.filterList(filteredEvents);

        if (filteredEvents.isEmpty()) {
            textEmpty.setVisibility(View.VISIBLE);
            recyclerEvents.setVisibility(View.GONE);
        } else {
            textEmpty.setVisibility(View.GONE);
            recyclerEvents.setVisibility(View.VISIBLE);
        }
    }
}