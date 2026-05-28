package org.example.app.controllers;

import org.example.app.managers.SystemManager;
import org.example.app.models.Request;

public class AdminDashboardController {

    private SystemManager systemManager;

    // CONSTRUCTOR
    public AdminDashboardController() {
        systemManager = SystemManager.getInstance();
    }

    // SHOW ALL REQUESTS
    public void showAllRequests() {

        systemManager.displayAllRequests();
    }

    // APPROVE REQUEST
    public void approveRequest(Request request) {

        systemManager.approveRequest(request);
    }

    // REJECT REQUEST
    public void rejectRequest(Request request) {

        systemManager.rejectRequest(request);
    }

    // SHOW STATISTICS
    public void showStatistics() {

        System.out.println("===== SYSTEM STATISTICS =====");

        System.out.println("Total Requests: "
                + systemManager.getTotalRequests());

        System.out.println("Pending Requests: "
                + systemManager.getPendingRequestsCount());

        System.out.println("Approved Requests: "
                + systemManager.getApprovedRequestsCount());

        System.out.println("Rejected Requests: "
                + systemManager.getRejectedRequestsCount());
    }
}