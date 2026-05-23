package org.example.app;

import org.example.app.exceptions.ProcessingException;
import org.example.app.managers.SystemManager;
import org.example.app.models.*;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   RUNNING BACKEND ENGINE VERIFICATION SUITE   ");
        System.out.println("=================================================");

        // 1. Verify Singleton Instance
        System.out.print("[TEST 1] Testing SystemManager Singleton... ");
        SystemManager instance1 = SystemManager.getInstance();
        SystemManager instance2 = SystemManager.getInstance();

        if (instance1 == instance2) {
            System.out.println("PASSED (Single instance enforced)");
        } else {
            System.out.println("FAILED!");
        }

        // 2. Setup Mock Entities
        Student mockStudent = new Student("judy_tech_lead");
        Service mockService = new Service("Campus Wi-Fi", "IT");
        ServiceRequest req = new ServiceRequest("REQ-101", mockStudent, mockService, "Testing connectivity issues.");

        // 3. Verify Request Insertion
        System.out.print("[TEST 2] Testing Request Collection Insertion... ");
        instance1.addRequest(req);
        if (instance1.getTotalRequests() == 1) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }

        // 4. Verify Duplicate Handling
        System.out.print("[TEST 3] Testing Duplicate Exception Prevention... ");
        try {
            ServiceRequest duplicateReq = new ServiceRequest("REQ-101", mockStudent, mockService, "Different body, same ID.");
            instance1.addRequest(duplicateReq);
            System.out.println("FAILED (System allowed duplicate ID)");
        } catch (ProcessingException e) {
            System.out.println("PASSED (Caught expected error: " + e.getMessage() + ")");
        }

        // 5. Verify State Modifications
        System.out.print("[TEST 4] Testing State Transitions... ");
        instance1.updateRequestStatus("REQ-101", RequestStatus.APPROVED);
        if (req.getStatus() == RequestStatus.APPROVED) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }

        System.out.println("\n=================================================");
        System.out.println("      ALL CORE ENGINE VERIFICATIONS COMPLETE      ");
        System.out.println("=================================================");
    }
}