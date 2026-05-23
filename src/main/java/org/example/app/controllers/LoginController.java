package org.example.app.controllers;

import org.example.app.managers.AuthenticationManager;
import org.example.app.models.User;
import org.example.app.exceptions.AuthenticationException;

public class LoginController {

    private AuthenticationManager authManager = new AuthenticationManager();

    public void login(String username, String password) {

        try {
            User user = authManager.login(username, password);

            System.out.println("Login successful!");
            user.displayDashboard();

        } catch (AuthenticationException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }
}

