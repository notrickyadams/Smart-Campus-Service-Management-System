

package org.example.app.models;
public abstract class User {

    protected final String role;
    protected String username;
    protected String password;

    // FIXED:
    // Added password parameter
    // because every user must have a password
    public User(String username, String password, String role) {

        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    // Abstract method
    // Each user type will implement its own dashboard
    public abstract void displayDashboard();
}