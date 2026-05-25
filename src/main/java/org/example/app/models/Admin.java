package org.example.app.models;
public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void displayDashboard() {
        System.out.println("Admin Dashboard");
    }

    public void manageRequests() {
        System.out.println("Managing requests...");
    }
}