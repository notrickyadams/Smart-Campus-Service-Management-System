package org.example.app.models;
public class Student extends User {

    public Student(String username, String password) {
        super(username, password);
    }

    @Override
    public void displayDashboard() {
        System.out.println("Student Dashboard");
    }

}