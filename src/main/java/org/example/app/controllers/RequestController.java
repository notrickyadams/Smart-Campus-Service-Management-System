package org.example.app.controllers;

import org.example.app.models.Service;
import org.example.app.models.Request;
import org.example.app.models.Student;
import org.example.app.exceptions.InvalidRequestException;

import java.util.ArrayList;

public class RequestController {
    private ArrayList<Request> requests = new ArrayList<>();
    public void submitRequest(Student student, Service service) throws InvalidRequestException {

        if (service == null) {
            throw new InvalidRequestException("Service cannot be null");
        }

        Request request = new Request(student, service);
        requests.add(request);

        System.out.println("Request submitted successfully.");
    }
    public ArrayList<Request> getAllRequests() {
        return requests;
    }
}

