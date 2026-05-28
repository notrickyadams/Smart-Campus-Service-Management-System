package org.example.app.managers;

import org.example.app.models.*;

public class UserFactory {

    // Creates user objects depending on role
    public static User createUser(String role, String username, String password) {

        // If role student
        if (role.equalsIgnoreCase("student")) {

            // Create Student object
            return new Student(username, password);
        }

        // If role is admin
        else if (role.equalsIgnoreCase("admin")) {

            // Create Admin object
            return new Admin(username, password);
        }

        // Invalid role
        return null;
    }
}