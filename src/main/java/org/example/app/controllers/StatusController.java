package org.example.app.controllers;
import org.example.app.models.Request;

public class StatusController {
    public void updateStatus(Request request, String newStatus) {

        if (request == null) {
            System.out.println("Request not found.");
            return;
        }

        request.setStatus(newStatus);
        System.out.println("Status updated to: " + newStatus);
    }
    public void viewStatus(Request request) {

        if (request == null) {
            System.out.println("Request not found.");
            return;
        }

        System.out.println("Current status: " + request.getStatus());
    }
}
