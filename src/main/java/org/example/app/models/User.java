package org.example.app.models;

public abstract class User {

    protected final String role;
    protected String username;
    protected String password;
    public User(String username, String role) {
        this.username = username;
        this.role = role;
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public abstract void displayDashboard();
    public String getUsername() { return username; }

}