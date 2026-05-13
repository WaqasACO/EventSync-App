package com.example.eventsync.database;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.example.eventsync.models.Event;
import com.example.eventsync.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class DatabaseHelperTest {

    private DatabaseHelper databaseHelper;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        databaseHelper = new DatabaseHelper(context);
    }

    @After
    public void tearDown() {
        databaseHelper.close();
    }

    @Test
    public void testAuthenticateUser() {
        // Arrange
        User user = new User("u1", "Test User", "test@example.com", "password123", "organizer");
        databaseHelper.insertUser(user);

        // Act
        boolean success = databaseHelper.authenticateUser("test@example.com", "password123");
        boolean failure = databaseHelper.authenticateUser("test@example.com", "wrongpass");

        // Assert
        assertTrue(success);
        assertFalse(failure);
    }

    @Test
    public void testInsertAndGetAllEvents() {
        // Arrange
        Event event1 = new Event("e1", "Tech Talk", "2026-10-10", "14:00", "Room 101", "A talk about AI", "u1");
        Event event2 = new Event("e2", "Job Fair", "2026-11-15", "10:00", "Main Hall", "Campus job fair", "u2");

        // Act
        long row1 = databaseHelper.insertEvent(event1);
        long row2 = databaseHelper.insertEvent(event2);
        List<Event> events = databaseHelper.getAllEvents();

        // Assert
        assertTrue(row1 != -1);
        assertTrue(row2 != -1);
        assertEquals(2, events.size());
        assertEquals("Tech Talk", events.get(0).getTitle());
        assertEquals("Job Fair", events.get(1).getTitle());
    }

    @Test
    public void testDeleteEvent() {
        // Arrange
        Event event = new Event("e3", "Movie Night", "2026-12-01", "20:00", "Student Center", "Watch a movie", "u3");
        databaseHelper.insertEvent(event);

        // Act
        int rowsDeleted = databaseHelper.deleteEvent("e3");
        List<Event> events = databaseHelper.getAllEvents();

        // Assert
        assertEquals(1, rowsDeleted);
        assertEquals(0, events.size());
    }
}
