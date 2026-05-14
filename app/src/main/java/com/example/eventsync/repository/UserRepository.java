package com.example.eventsync.repository;

import android.content.Context;

import com.example.eventsync.database.DatabaseHelper;
import com.example.eventsync.models.User;

public class UserRepository {

    private DatabaseHelper databaseHelper;

    public UserRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public boolean login(String email, String password) {
        return databaseHelper.authenticateUser(email, password);
    }

    public String getUserRole(User user) {
        return user.getRole();
    }
}