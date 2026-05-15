package com.example.eventsync;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventsync.database.DatabaseHelper;

import java.util.List;

public class EventDetailActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private boolean isJoined = false;
    private String eventID;
    private String userID = "participant_1"; // placeholder until login is connected
    private StringBuilder comments = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // get event data passed from adapter
        eventID = getIntent().getStringExtra("eventID");
        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String location = getIntent().getStringExtra("location");
        String description = getIntent().getStringExtra("description");

        // set up views
        TextView textTitle = findViewById(R.id.textDetailTitle);
        TextView textDate = findViewById(R.id.textDetailDate);
        TextView textTime = findViewById(R.id.textDetailTime);
        TextView textLocation = findViewById(R.id.textDetailLocation);
        TextView textDescription = findViewById(R.id.textDetailDescription);
        Button buttonJoin = findViewById(R.id.buttonJoin);
        EditText editComment = findViewById(R.id.editComment);
        Button buttonComment = findViewById(R.id.buttonComment);
        TextView textComments = findViewById(R.id.textComments);

        // populate event details
        textTitle.setText(title);
        textDate.setText("Date: " + date);
        textTime.setText("Time: " + time);
        textLocation.setText("Location: " + location);
        textDescription.setText(description);

        // set up database
        databaseHelper = new DatabaseHelper(this);

        // check if user already joined this event
        List<String> attendees = databaseHelper.getAttendeesForEvent(eventID);
        if (attendees.contains(userID)) {
            isJoined = true;
            buttonJoin.setText("Leave Event");
            buttonJoin.setBackgroundTintList(
                    getColorStateList(android.R.color.holo_red_light));
        }

        // join / leave event logic
        buttonJoin.setOnClickListener(v -> {
            if (!isJoined) {
                databaseHelper.addAttendee(eventID, userID);
                isJoined = true;
                buttonJoin.setText("Leave Event");
                buttonJoin.setBackgroundTintList(
                        getColorStateList(android.R.color.holo_red_light));
                Toast.makeText(this, "You joined this event!", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.removeAttendee(eventID, userID);
                isJoined = false;
                buttonJoin.setText("Join Event");
                buttonJoin.setBackgroundTintList(
                        getColorStateList(android.R.color.holo_blue_light));
                Toast.makeText(this, "You left this event.", Toast.LENGTH_SHORT).show();
            }
        });

        // comment logic
        buttonComment.setOnClickListener(v -> {
            String comment = editComment.getText().toString().trim();
            if (!comment.isEmpty()) {
                comments.append("• ").append(comment).append("\n");
                textComments.setText(comments.toString());
                editComment.setText("");
                Toast.makeText(this, "Comment posted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please write a comment first.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}