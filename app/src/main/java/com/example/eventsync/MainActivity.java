package com.example.eventsync;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import com.example.eventsync.database.DatabaseHelper;
import com.example.eventsync.models.Event;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 1. Initialize sample data so the dashboard isn't empty
        DatabaseHelper db = new DatabaseHelper(this);
        if (db.getAllEvents().isEmpty()) {
            db.insertEvent(new Event("e1", "Tech Workshop", "2026-06-01", "10:00 AM", "Room 302", "Learn Android development with AI.", "admin"));
            db.insertEvent(new Event("e2", "Spring Music Fest", "2026-06-15", "4:00 PM", "Main Quad", "Live music and food on the quad.", "admin"));
            db.insertEvent(new Event("e3", "Career Fair", "2026-07-10", "9:00 AM", "Student Center", "Meet employers from top tech companies.", "admin"));
        }

        // 2. Redirect to the new Participant Dashboard
        Intent intent = new Intent(this, ParticipantDashboardActivity.class);
        intent.putExtra("username", "Waqas"); // Placeholder username
        startActivity(intent);
        finish(); // Close MainActivity so the user doesn't go back to a blank screen
    }
}
