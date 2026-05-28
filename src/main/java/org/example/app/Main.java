package org.example.app;

import org.example.app.managers.SystemManager;
import org.example.app.models.Request;
import org.example.app.models.Service;
import org.example.app.models.Student;

public class Main {

    public static void main(String[] args) {

        // CREATE SYSTEM MANAGER
        SystemManager systemManager = SystemManager.getInstance();

        // CREATE STUDENT
        Student student = new Student("Lojan", "123");

        // CREATE SERVICE
        Service service =
                new Service("Bus Service", "Transportation");

        // CREATE REQUEST
        Request request =
                new Request(student, service);

        // ADD REQUEST TO SYSTEM
        systemManager.addRequest(request);

        // DISPLAY BEFORE APPROVAL
        System.out.println("BEFORE APPROVAL:");
        systemManager.displayAllRequests();

        // APPROVE REQUEST
        systemManager.approveRequest(request);

        // WAIT FOR THREAD TO FINISH
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // DISPLAY AFTER APPROVAL
        System.out.println("\nAFTER APPROVAL:");
        systemManager.displayAllRequests();

        // DISPLAY STATISTICS
        System.out.println("\nSTATISTICS:");

        System.out.println(
                "Total Requests: "
                        + systemManager.getTotalRequests());

        System.out.println(
                "Approved Requests: "
                        + systemManager.getApprovedRequestsCount());

        System.out.println(
                "Pending Requests: "
                        + systemManager.getPendingRequestsCount());

        System.out.println(
                "Rejected Requests: "
                        + systemManager.getRejectedRequestsCount());
    }
}