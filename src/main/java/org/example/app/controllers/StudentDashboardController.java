package org.example.app.controllers;

import org.example.app.models.Service;
import org.example.app.models.Request;
import org.example.app.models.Student;

import java.util.ArrayList;


public class StudentDashboardController {

    private ArrayList<Service> services = new ArrayList<>();
    private ArrayList<Request> studentRequests = new ArrayList<>();
    private Student student;
    public StudentDashboardController(Student student) {
        this.student = student;

        services.add(new Service("Bus Service", "Book transportation"));
        services.add(new Service("Course Registration", "Register courses"));
        services.add(new Service("IT Support", "Fix technical issues"));
    }
    public void viewServices() {
        System.out.println("Available Services:");
        for (Service service : services) {
            System.out.println("- " + service.getName());
        }
    }
    public void submitRequest(Service service) {

        if (service == null) {
            System.out.println("Invalid service.");
            return;
        }

        Request request = new Request(student, service);
        studentRequests.add(request);

        System.out.println("Request submitted for: " + service.getName());
    }
    public void viewMyRequests() {

        System.out.println("My Requests:");

        for (Request request : studentRequests) {
            System.out.println(request.getStatus());
        }
    }
    public Service searchService(String name) {

        for (Service service : services) {
            if (service.getName().equalsIgnoreCase(name)) {
                return service;
            }
        }

        System.out.println("Service not found.");
        return null;
    }
}

