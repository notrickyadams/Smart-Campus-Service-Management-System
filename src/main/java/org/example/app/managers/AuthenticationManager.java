package org.example.app.managers;
import org.example.app.models.User;
import org.example.app.exceptions.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {

    private ArrayList<User> users = new ArrayList<>();

    public AuthenticationManager() {

        users.add(UserFactory.createUser("student", "student1", "123"));
        users.add(UserFactory.createUser("admin", "admin1", "admin"));
    }

    public User login(String username, String password) throws AuthenticationException {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return user;
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }
}
