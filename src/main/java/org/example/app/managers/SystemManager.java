package org.example.app.managers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.app.exceptions.ProcessingException;
import org.example.app.models.RequestStatus;
import org.example.app.models.ServiceRequest;
import org.example.app.services.DataStorageObserver;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {

    // Thread-safe volatile instance for Singleton Pattern
    private static volatile SystemManager instance;

    // ObservableList allows JavaFX UI components to auto-update when records change
    private final ObservableList<ServiceRequest> globalRequests;
    private final List<DataStorageObserver> storageObservers;

    // Private constructor prevents direct initialization
    private SystemManager() {
        this.globalRequests = FXCollections.observableArrayList();
        this.storageObservers = new ArrayList<>();
    }

    /**
     * Thread-Safe Double-Checked Locking Singleton Implementation
     */
    public static SystemManager getInstance() {
        if (instance == null) {
            synchronized (SystemManager.class) {
                if (instance == null) {
                    instance = new SystemManager();
                }
            }
        }
        return instance;
    }

    // --- Observer Hooks ---
    public void registerStorageObserver(DataStorageObserver observer) {
        this.storageObservers.add(observer);
    }

    private void notifyObservers() {
        for (DataStorageObserver observer : storageObservers) {
            observer.onDataChanged();
        }
    }

    // --- Request Operations ---
    public synchronized void addRequest(ServiceRequest request) {
        if (request == null) {
            throw new ProcessingException("Cannot process null service request.");
        }

        // Prevent duplicates
        for (ServiceRequest r : globalRequests) {
            if (r.getRequestId().equalsIgnoreCase(request.getRequestId())) {
                throw new ProcessingException("Duplicate Request Error: ID " + request.getRequestId() + " already exists.");
            }
        }

        globalRequests.add(request);
        notifyObservers();
    }

    public synchronized void updateRequestStatus(String requestId, RequestStatus newStatus) {
        ServiceRequest request = findRequestById(requestId);
        if (request == null) {
            throw new ProcessingException("Request Update Error: Request ID " + requestId + " not found.");
        }

        request.setStatus(newStatus);
        notifyObservers(); // Triggers automated data saves via observers if registered
    }

    public synchronized void removeRequest(String requestId) {
        ServiceRequest request = findRequestById(requestId);
        if (request == null) {
            throw new ProcessingException("Deletion Error: Request ID " + requestId + " not found.");
        }
        globalRequests.remove(request);
        notifyObservers();
    }

    private ServiceRequest findRequestById(String requestId) {
        return globalRequests.stream()
                .filter(r -> r.getRequestId().equalsIgnoreCase(requestId))
                .findFirst()
                .orElse(null);
    }

    public ObservableList<ServiceRequest> getGlobalRequests() {
        return globalRequests;
    }

    // --- Analytics Preparation Methods ---
    public long getTotalRequests() {
        return globalRequests.size();
    }

    public long getCountByStatus(RequestStatus status) {
        return globalRequests.stream().filter(r -> r.getStatus() == status).count();
    }

    public String getMostRequestedService() {
        if (globalRequests.isEmpty()) return "None";
        return globalRequests.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        r -> r.getService().getServiceName(),
                        java.util.stream.Collectors.counting()
                ))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse("None");
    }
}