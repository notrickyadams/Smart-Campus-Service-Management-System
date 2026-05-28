package org.example.app.managers;

import org.example.app.models.Request;

import java.util.ArrayList;

public class SystemManager {

    // singlton implmentation
    private static SystemManager instance;

    // STORE ALL REQUESTS
    private ArrayList<Request> requests;

    // PRIVATE CONSTRUCTOR
    private SystemManager() {
        requests = new ArrayList<>();
    }

    // GET SINGLE INSTANCE
    public static SystemManager getInstance() {

        if (instance == null) {
            instance = new SystemManager();
        }

        return instance;
    }

    // ADD REQUEST
    public void addRequest(Request request) {
        requests.add(request);
    }

    // APPROVE REQUEST WITH MULTITHREADING
    public void approveRequest(Request request) {

        Thread thread = new Thread(() -> {

            try {

                System.out.println("Processing approval...");

                // loadinggg
                Thread.sleep(3000);

                request.setStatus("Approved");

                System.out.println("Request approved.");

            } catch (InterruptedException e) {

                System.out.println("Error processing request.");
            }
        });

        thread.start();
    }

    // REJECT REQUEST
    // REJECT REQUEST WITH MULTITHREADING
    public void rejectRequest(Request request) {

        Thread thread = new Thread(() -> {

            try {

                System.out.println("Processing rejection...");

                // loadinggg
                Thread.sleep(3000);

                request.setStatus("Rejected");

                System.out.println("Request rejected.");

            } catch (InterruptedException e) {

                System.out.println("Error processing request.");
            }
        });

        thread.start();
    }

    // RETURN ALL REQUESTS
    public ArrayList<Request> getAllRequests() {
        return requests;
    }

    // DISPLAY REQUESTS
    public void displayAllRequests() {

        for (Request request : requests) {

            System.out.println(
                    request.getStudent().getUsername()
                            + " requested "
                            + request.getService().getName()
                            + " | Status: "
                            + request.getStatus()
            );
        }
    }

    // TOTAL REQUESTS
    public int getTotalRequests() {
        return requests.size();
    }

    // COUNT PENDING REQUESTS
    public int getPendingRequestsCount() {

        int count = 0;

        for (Request request : requests) {

            if (request.getStatus().equalsIgnoreCase("Pending")) {
                count++;
            }
        }

        return count;
    }

    // COUNT APPROVED REQUESTS
    public int getApprovedRequestsCount() {

        int count = 0;

        for (Request request : requests) {

            if (request.getStatus().equalsIgnoreCase("Approved")) {
                count++;
            }
        }

        return count;
    }

    // COUNT REJECTED REQUESTS
    public int getRejectedRequestsCount() {

        int count = 0;

        for (Request request : requests) {

            if (request.getStatus().equalsIgnoreCase("Rejected")) {
                count++;
            }
        }

        return count;
    }
}