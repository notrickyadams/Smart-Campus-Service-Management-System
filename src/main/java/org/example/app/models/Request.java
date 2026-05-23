package org.example.app.models;
public class Request {

    private Student student;
    private Service service;
    private String status;

    public Request(Student student, Service service) {
        this.student = student;
        this.service = service;
        this.status = "Pending";
    }

    public void submitRequest() {
        System.out.println("Request submitted");
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void displayStatus() {
        System.out.println("Current Status: " + status);
    }

    public Service getService() {
        return service;
    }

    public Student getStudent() {
        return student;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


}