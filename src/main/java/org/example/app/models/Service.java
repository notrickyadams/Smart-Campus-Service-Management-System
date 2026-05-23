package org.example.app.models;

public class Service {
    private final String serviceName;
    private final String category;

    public Service(String serviceName, String category) {
        this.serviceName = serviceName;
        this.category = category;
    }

    public String getServiceName() { return serviceName; }
    public String getCategory() { return category; }
}