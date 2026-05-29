package org.example.app.managers;

import org.example.app.models.Admin;
import org.example.app.models.Student;
import org.example.app.models.User;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {

    private static AuthenticationManager instance;
    private List<User> users = new ArrayList<>();
    private User currentUser;

    private AuthenticationManager() {
        // Hardcoded users for now — replace with FileManager.loadUsers() later
        users.add(new Admin("admin", "admin123"));
        users.add(new Student("student", "student123"));
        users.add(new Student("alice", "alice123"));
    }

    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    // Returns matched User or null if wrong credentials
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.checkPassword(password)) {  // uses YOUR checkPassword method
                currentUser = user;
                return user;
            }
        }
        return null;
    }

    public User getCurrentUser()  { return currentUser; }
    public void logout()          { currentUser = null; }
}