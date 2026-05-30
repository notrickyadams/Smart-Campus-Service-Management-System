package org.example.app.managers;

import org.example.app.models.Admin;
import org.example.app.models.Student;
import org.example.app.models.User;
import org.example.app.models.UserFactory;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {

    private static AuthenticationManager instance;
    private List<User> users = new ArrayList<>();
    private User currentUser;

    private AuthenticationManager() {
        // Load users from file first
        List<User> loaded = FileManager.getInstance().loadUsers();

        if (loaded.isEmpty()) {
            // First run — create defaults and save them
            users.add(new Admin("admin", "admin123"));
            users.add(new Student("student", "student123"));
            users.add(new Student("alice", "alice123"));
            saveToFile();
        } else {
            users = loaded;
        }
    }

    public static AuthenticationManager getInstance() {
        if (instance == null) instance = new AuthenticationManager();
        return instance;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.checkPassword(password)) {
                currentUser = user;
                return user;
            }
        }
        return null;
    }

    public boolean register(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return false;
        }
        User newUser = UserFactory.createUser(username, password, "student");
        users.add(newUser);
        saveToFile(); // persist immediately
        return true;
    }

    private void saveToFile() {
        // Save as raw strings since we need plaintext passwords
        List<String[]> raw = new ArrayList<>();
        for (User user : users) {
            // We store password in a field we can access via subclass
            // so we use a workaround — store during creation
            raw.add(new String[]{
                    user.getUsername(),
                    getRawPassword(user),
                    user.getRole()
            });
        }
        FileManager.getInstance().saveUsersRaw(raw);
    }

    // Helper to get password — since checkPassword compares, we store it
    private String getRawPassword(User user) {
        // checkPassword compares this.password == input
        // so we try the username as a trick — not secure but fine for this project
        // Better: add getPassword() to User
        return user.getPassword();
    }

    public User getCurrentUser() { return currentUser; }
    public void logout()         { currentUser = null; }
}