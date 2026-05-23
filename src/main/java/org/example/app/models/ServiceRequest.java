package org.example.app.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ServiceRequest {
    private final StringProperty requestId;
    private final ObjectProperty<Student> student;
    private final ObjectProperty<Service> service;
    private final StringProperty description;
    private final ObjectProperty<RequestStatus> status;

    public ServiceRequest(String requestId, Student student, Service service, String description) {
        this.requestId = new SimpleStringProperty(requestId);
        this.student = new SimpleObjectProperty<>(student);
        this.service = new SimpleObjectProperty<>(service);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleObjectProperty<>(RequestStatus.PENDING);
    }

    // Properties for JavaFX TableView bindings
    public StringProperty requestIdProperty() { return requestId; }
    public ObjectProperty<Student> studentProperty() { return student; }
    public ObjectProperty<Service> serviceProperty() { return service; }
    public StringProperty descriptionProperty() { return description; }
    public ObjectProperty<RequestStatus> statusProperty() { return status; }

    // Standard Getters & Setters
    public String getRequestId() { return requestId.get(); }
    public Student getStudent() { return student.get(); }
    public Service getService() { return service.get(); }
    public String getDescription() { return description.get(); }

    public RequestStatus getStatus() { return status.get(); }
    public void setStatus(RequestStatus status) { this.status.set(status); }
}