package com.example.eventsync.viewmodel;

import com.example.eventsync.models.User;
import com.example.eventsync.repository.UserRepository;

public class UserViewModel {

    private UserRepository repository;

    public UserViewModel() {
        repository = new UserRepository();
    }

    public boolean login(String email, String password) {
        return repository.login(email, password);
    }

    public String getUserRole(User user) {
        return repository.getUserRole(user);
    }
}