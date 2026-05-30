package org.example.app.managers;

import org.example.app.models.Request;
import org.example.app.models.Service;
import org.example.app.models.Student;
import org.example.app.models.User;
import org.example.app.models.UserFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static FileManager instance;

    // File paths — creates in project root
    private static final String USERS_FILE    = "users.txt";
    private static final String REQUESTS_FILE = "requests.txt";

    private FileManager() {
        // Create files if they don't exist
        createFileIfNotExists(USERS_FILE);
        createFileIfNotExists(REQUESTS_FILE);
    }

    public static FileManager getInstance() {
        if (instance == null) instance = new FileManager();
        return instance;
    }

    private void createFileIfNotExists(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Created file: " + filename);
            }
        } catch (IOException e) {
            System.err.println("Could not create file: " + filename);
        }
    }

    // ── SAVE USERS ──────────────────────────────────────────

    public void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                // FORMAT: username,password,role
                writer.write(user.getUsername() + "," +
                        user.checkPassword(user.getUsername()) + "," +
                        user.getRole());
                writer.newLine();
            }
            System.out.println("Users saved to " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public void saveUsersRaw(List<String[]> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (String[] user : users) {
                // user[0]=username, user[1]=password, user[2]=role
                writer.write(user[0] + "," + user[1] + "," + user[2]);
                writer.newLine();
            }
            System.out.println("Users saved.");
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // ── LOAD USERS ──────────────────────────────────────────

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    // FORMAT: username,password,role
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role     = parts[2].trim();
                    users.add(UserFactory.createUser(username, password, role));
                }
            }
            System.out.println("Loaded " + users.size() + " users from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No users file found, starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // ── SAVE REQUESTS ────────────────────────────────────────

    public void saveRequests(List<Request> requests) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REQUESTS_FILE))) {
            for (Request request : requests) {
                // FORMAT: studentUsername,serviceName,status
                writer.write(request.getStudent().getUsername() + "," +
                        request.getService().getName() + "," +
                        request.getStatus());
                writer.newLine();
            }
            System.out.println("Requests saved to " + REQUESTS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving requests: " + e.getMessage());
        }
    }

    // ── LOAD REQUESTS ────────────────────────────────────────

    public List<Request> loadRequests() {
        List<Request> requests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String studentUsername = parts[0].trim();
                    String serviceName     = parts[1].trim();
                    String status          = parts[2].trim();

                    Student student = new Student(studentUsername, "");
                    Service service = new Service(serviceName, "");
                    Request request = new Request(student, service);
                    request.setStatus(status);
                    requests.add(request);
                }
            }
            System.out.println("Loaded " + requests.size() + " requests from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No requests file found, starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading requests: " + e.getMessage());
        }
        return requests;
    }
}