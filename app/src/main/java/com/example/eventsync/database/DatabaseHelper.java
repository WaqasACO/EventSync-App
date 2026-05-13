package com.example.eventsync.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.eventsync.models.Event;
import com.example.eventsync.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "eventsync.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_ATTENDEES = "event_attendees";

    // Common column names
    private static final String KEY_ID = "id";

    // USERS Table - column names
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_ROLE = "role";

    // EVENTS Table - column names
    private static final String KEY_EVENT_TITLE = "title";
    private static final String KEY_EVENT_DESCRIPTION = "description";
    private static final String KEY_EVENT_DATE = "date";
    private static final String KEY_EVENT_TIME = "time";
    private static final String KEY_EVENT_LOCATION = "location";
    private static final String KEY_EVENT_CREATOR_ID = "creatorId";

    // EVENT_ATTENDEES Table - column names
    private static final String KEY_ATTENDEE_EVENT_ID = "eventId";
    private static final String KEY_ATTENDEE_USER_ID = "userId";

    // Table Create Statements
    // Users table create statement
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_USER_NAME + " TEXT,"
            + KEY_USER_EMAIL + " TEXT UNIQUE,"
            + KEY_USER_PASSWORD + " TEXT,"
            + KEY_USER_ROLE + " TEXT" + ")";

    // Events table create statement
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_EVENT_TITLE + " TEXT,"
            + KEY_EVENT_DESCRIPTION + " TEXT,"
            + KEY_EVENT_DATE + " TEXT,"
            + KEY_EVENT_TIME + " TEXT,"
            + KEY_EVENT_LOCATION + " TEXT,"
            + KEY_EVENT_CREATOR_ID + " TEXT" + ")";

    // Attendees table create statement
    private static final String CREATE_TABLE_ATTENDEES = "CREATE TABLE " + TABLE_ATTENDEES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ATTENDEE_EVENT_ID + " TEXT,"
            + KEY_ATTENDEE_USER_ID + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_ATTENDEES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDEES);

        // create new tables
        onCreate(db);
    }

    // ------------------------ "USERS" table methods ----------------//

    /**
     * Authenticate user/login
     */
    public boolean authenticateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "
                + KEY_USER_EMAIL + " = ? AND " + KEY_USER_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{email, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    /**
     * Insert a user (for testing/setup purposes)
     */
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getUserID());
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_PASSWORD, user.getPassword());
        values.put(KEY_USER_ROLE, user.getRole());

        // insert row
        return db.insert(TABLE_USERS, null, values);
    }

    // ------------------------ "EVENTS" table methods ----------------//

    /**
     * Insert event
     */
    public long insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, event.getEventID());
        values.put(KEY_EVENT_TITLE, event.getTitle());
        values.put(KEY_EVENT_DESCRIPTION, event.getDescription());
        values.put(KEY_EVENT_DATE, event.getDate());
        values.put(KEY_EVENT_TIME, event.getTime());
        values.put(KEY_EVENT_LOCATION, event.getLocation());
        values.put(KEY_EVENT_CREATOR_ID, event.getCreatorID());

        // insert row
        return db.insert(TABLE_EVENTS, null, values);
    }

    /**
     * Get all events
     */
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setEventID(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID)));
                event.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_TITLE)));
                event.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_DESCRIPTION)));
                event.setDate(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_DATE)));
                event.setTime(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_TIME)));
                event.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_LOCATION)));
                event.setCreatorID(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_CREATOR_ID)));

                // adding to events list
                events.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    /**
     * Delete event
     */
    public int deleteEvent(String eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_EVENTS, KEY_ID + " = ?", new String[]{eventId});
    }

    // ------------------------ "ATTENDEES" table methods ----------------//

    /**
     * Add attendee to an event
     */
    public long addAttendee(String eventId, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ATTENDEE_EVENT_ID, eventId);
        values.put(KEY_ATTENDEE_USER_ID, userId);

        return db.insert(TABLE_ATTENDEES, null, values);
    }

    /**
     * Get all user IDs attending a specific event
     */
    public List<String> getAttendeesForEvent(String eventId) {
        List<String> userIds = new ArrayList<>();
        String selectQuery = "SELECT " + KEY_ATTENDEE_USER_ID + " FROM " + TABLE_ATTENDEES
                + " WHERE " + KEY_ATTENDEE_EVENT_ID + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{eventId});

        if (cursor.moveToFirst()) {
            do {
                userIds.add(cursor.getString(cursor.getColumnIndexOrThrow(KEY_ATTENDEE_USER_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userIds;
    }

    /**
     * Remove attendee from an event
     */
    public int removeAttendee(String eventId, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ATTENDEES, KEY_ATTENDEE_EVENT_ID + " = ? AND " + KEY_ATTENDEE_USER_ID + " = ?",
                new String[]{eventId, userId});
    }
}
