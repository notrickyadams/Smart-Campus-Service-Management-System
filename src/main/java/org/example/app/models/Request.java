package org.example.app.models;

public class Request {

    private int     id;
    private Student student;
    private Service service;
    private String  status;

    public Request(Student student, Service service) {
        this.student = student;
        this.service = service;
        this.status  = "Pending";
    }

    public int     getId()                  { return id; }
    public void    setId(int id)            { this.id = id; }
    public Student getStudent()             { return student; }
    public Service getService()             { return service; }
    public String  getStatus()              { return status; }
    public void    setStatus(String status) { this.status = status; }
}