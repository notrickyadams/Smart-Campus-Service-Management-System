package org.example.app.managers;

import org.example.app.config.SupabaseClient;
import org.example.app.models.Request;
import org.example.app.models.Service;
import org.example.app.models.Student;

import java.util.ArrayList;

public class SystemManager {

    private static SystemManager instance;

    private SystemManager() {}

    public static SystemManager getInstance() {
        if (instance == null) instance = new SystemManager();
        return instance;
    }

    public void addRequest(Request request) {
        try {
            String json = "{" +
                    "\"student_username\":\"" + request.getStudent().getUsername() + "\"," +
                    "\"service_name\":\"" + request.getService().getName() + "\"," +
                    "\"status\":\"Pending\"," +
                    "\"notes\":\"\"" +
                    "}";

            System.out.println("=== ADDING REQUEST ===");
            System.out.println("JSON: " + json);

            int status = SupabaseClient.post("/requests", json);

            if (status == 200 || status == 201) {
                System.out.println("Request saved to Supabase!");
            } else {
                System.err.println("Failed to save request. Status: " + status);
            }
        } catch (Exception e) {
            System.err.println("addRequest error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Request> getAllRequests() {
        String response = SupabaseClient.get(
                "/requests?select=id,student_username,service_name,status,notes" +
                        "&order=created_at.desc"
        );
        System.out.println("getAllRequests response: " + response);
        return parseRequests(response);
    }

    public ArrayList<Request> getRequestsByStudent(String username) {
        String response = SupabaseClient.get(
                "/requests?student_username=eq." + username +
                        "&select=id,student_username,service_name,status,notes" +
                        "&order=created_at.desc"
        );
        System.out.println("getRequestsByStudent response: " + response);
        return parseRequests(response);
    }

    public void approveRequest(Request request) {
        new Thread(() -> {
            try {
                System.out.println("Processing approval...");
                Thread.sleep(3000);
                String json = "{\"status\":\"Approved\"}";
                int status = SupabaseClient.patch("/requests?id=eq." + request.getId(), json);
                if (status == 200 || status == 204) {
                    request.setStatus("Approved");
                    System.out.println("Approved in Supabase!");
                } else {
                    System.err.println("Approve failed: " + status);
                }
            } catch (InterruptedException e) {
                System.err.println("Approval interrupted.");
            }
        }).start();
    }

    public void rejectRequest(Request request) {
        new Thread(() -> {
            try {
                System.out.println("Processing rejection...");
                Thread.sleep(3000);
                String json = "{\"status\":\"Rejected\"}";
                int status = SupabaseClient.patch("/requests?id=eq." + request.getId(), json);
                if (status == 200 || status == 204) {
                    request.setStatus("Rejected");
                    System.out.println("Rejected in Supabase!");
                } else {
                    System.err.println("Reject failed: " + status);
                }
            } catch (InterruptedException e) {
                System.err.println("Rejection interrupted.");
            }
        }).start();
    }

    private ArrayList<Request> parseRequests(String json) {
        ArrayList<Request> list = new ArrayList<>();
        if (json == null || json.trim().equals("[]") || json.isEmpty()) return list;

        try {
            String[] objects = json.split("\\},\\s*\\{");
            for (String obj : objects) {
                String id      = extractField(obj, "id");
                String uname   = extractField(obj, "student_username");
                String service = extractField(obj, "service_name");
                String status  = extractField(obj, "status");

                if (uname == null || service == null) continue;

                Student s  = new Student(uname, "");
                Service sv = new Service(service, "");
                Request r  = new Request(s, sv);
                r.setStatus(status != null ? status : "Pending");

                if (id != null) {
                    try { r.setId(Integer.parseInt(id.trim())); }
                    catch (NumberFormatException ignored) {}
                }

                list.add(r);
            }
        } catch (Exception e) {
            System.err.println("Parse error: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    private String extractField(String json, String field) {
        try {
            String key = "\"" + field + "\":\"";
            int start = json.indexOf(key);
            if (start != -1) {
                start += key.length();
                int end = json.indexOf("\"", start);
                return json.substring(start, end);
            }

            key = "\"" + field + "\":";
            start = json.indexOf(key);
            if (start != -1) {
                start += key.length();
                int end = json.indexOf(",", start);
                if (end == -1) end = json.indexOf("}", start);
                return json.substring(start, end).trim();
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public int getTotalRequests()         { return getAllRequests().size(); }
    public int getPendingRequestsCount()  {
        return (int) getAllRequests().stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Pending")).count();
    }
    public int getApprovedRequestsCount() {
        return (int) getAllRequests().stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Approved")).count();
    }
    public int getRejectedRequestsCount() {
        return (int) getAllRequests().stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("Rejected")).count();
    }
}