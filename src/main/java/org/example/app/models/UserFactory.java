package org.example.app.models;

public class UserFactory {

    public static User createUser(String username, String password, String role) {
        switch (role.toLowerCase()) {
            case "admin":
                return new Admin(username, password);
            case "student":
                return new Student(username, password);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}