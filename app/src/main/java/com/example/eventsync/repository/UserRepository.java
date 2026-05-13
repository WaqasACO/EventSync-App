package com.example.eventsync.repository;

import com.example.eventsync.models.User;

public class UserRepository {

    public boolean login(String email, String password) {
        return false;
    }

    public String getUserRole(User user) {
        return user.getRole();
    }
}