package org.example.app.models;

public class Student extends User {

    // Student constructor
    public Student(String username, String password) {

        // FIXED:
        // Sends username + password + role
        // to parent User class
        super(username, password, "Student");
    }

    @Override
    public void displayDashboard() {

        System.out.println("Student Dashboard");
    }
}