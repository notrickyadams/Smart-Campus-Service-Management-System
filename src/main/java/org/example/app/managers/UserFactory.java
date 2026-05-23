package org.example.app.managers;
import org.example.app.models.*;

public class UserFactory {

    public static User createUser(String role, String username, String password) {
        if (role.equalsIgnoreCase("student")) {
            return new Student(username, password);
        } else if (role.equalsIgnoreCase("admin")) {
            return new Admin(username, password);
        }
        return null;
    }
}
