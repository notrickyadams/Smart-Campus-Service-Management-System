package org.example.app.models;

public abstract class User {

    protected final String role;
    protected String username;
    protected String password;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getRole()     { return role; }
    public String getUsername() { return username; }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public abstract void displayDashboard();

    public String getPassword() {
        return "";
    }
}