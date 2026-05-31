package org.example.app.managers;

import org.example.app.config.SupabaseClient;
import org.example.app.models.Admin;
import org.example.app.models.Student;
import org.example.app.models.User;
import org.example.app.models.UserFactory;

public class AuthenticationManager {

    private static AuthenticationManager instance;
    private User currentUser;

    private AuthenticationManager() {}

    public static AuthenticationManager getInstance() {
        if (instance == null) instance = new AuthenticationManager();
        return instance;
    }

    public User login(String username, String password) {
        try {
            String endpoint = "/users?username=eq." + username
                    + "&password=eq." + password
                    + "&select=username,password,role";

            String response = SupabaseClient.get(endpoint);
            System.out.println("LOGIN RESPONSE: " + response);

            if (response == null || response.trim().equals("[]")) return null;

            String role  = extractField(response, "role");
            String uname = extractField(response, "username");
            String pwd   = extractField(response, "password");

            if (uname == null || role == null) return null;

            currentUser = UserFactory.createUser(uname, pwd != null ? pwd : "", role);
            return currentUser;

        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return null;
        }
    }

    public boolean register(String username, String password) {
        try {
            System.out.println("=== REGISTERING: " + username + " ===");

            // Check if username exists
            String checkResponse = SupabaseClient.get(
                    "/users?username=eq." + username + "&select=username"
            );
            System.out.println("CHECK RESPONSE: " + checkResponse);

            if (checkResponse != null && !checkResponse.trim().equals("[]")) {
                System.out.println("Username taken.");
                return false;
            }

            // Insert new user
            String json = "{" +
                    "\"username\":\"" + username + "\"," +
                    "\"password\":\"" + password + "\"," +
                    "\"role\":\"Student\"" +
                    "}";

            System.out.println("INSERTING: " + json);
            int status = SupabaseClient.post("/users", json);
            System.out.println("INSERT STATUS: " + status);

            return status == 200 || status == 201;

        } catch (Exception e) {
            System.err.println("Register error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private String extractField(String json, String field) {
        try {
            String key = "\"" + field + "\":\"";
            int start = json.indexOf(key);
            if (start == -1) return null;
            start += key.length();
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }

    public User getCurrentUser() { return currentUser; }
    public void logout()         { currentUser = null; }
}