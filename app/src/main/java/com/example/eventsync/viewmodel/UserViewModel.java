package com.example.eventsync.viewmodel;

import android.content.Context;

import com.example.eventsync.models.User;
import com.example.eventsync.repository.UserRepository;

public class UserViewModel {

    private UserRepository repository;

    public UserViewModel(Context context) {
        repository = new UserRepository(context);
    }

    public boolean login(String email, String password) {
        return repository.login(email, password);
    }

    public String getUserRole(User user) {
        return repository.getUserRole(user);
    }
}