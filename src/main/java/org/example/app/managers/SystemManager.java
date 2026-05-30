package org.example.app.managers;

import org.example.app.models.Request;
import org.example.app.models.Student;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SystemManager {

    private static SystemManager instance;
    private ArrayList<Request> requests = new ArrayList<>();

    private SystemManager() {
        // Load requests from file on startup
        requests = new ArrayList<>(FileManager.getInstance().loadRequests());
    }

    public static SystemManager getInstance() {
        if (instance == null) instance = new SystemManager();
        return instance;
    }

    public void addRequest(Request request) {
        requests.add(request);
        FileManager.getInstance().saveRequests(requests); // save immediately
    }

    public void approveRequest(Request request) {
        new Thread(() -> {
            try {
                System.out.println("Processing approval...");
                Thread.sleep(3000);
                request.setStatus("Approved");
                FileManager.getInstance().saveRequests(requests); // save after update
                System.out.println("Request approved.");
            } catch (InterruptedException e) {
                System.out.println("Error processing.");
            }
        }).start();
    }

    public void rejectRequest(Request request) {
        new Thread(() -> {
            try {
                System.out.println("Processing rejection...");
                Thread.sleep(3000);
                request.setStatus("Rejected");
                FileManager.getInstance().saveRequests(requests); // save after update
                System.out.println("Request rejected.");
            } catch (InterruptedException e) {
                System.out.println("Error processing.");
            }
        }).start();
    }

    public ArrayList<Request> getAllRequests() { return requests; }

    public ArrayList<Request> getRequestsByStudent(String username) {
        return requests.stream()
                .filter(r -> r.getStudent().getUsername().equals(username))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public int getTotalRequests()         { return requests.size(); }

    public int getPendingRequestsCount() {
        return (int) requests.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Pending")).count();
    }

    public int getApprovedRequestsCount() {
        return (int) requests.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Approved")).count();
    }

    public int getRejectedRequestsCount() {
        return (int) requests.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Rejected")).count();
    }
}