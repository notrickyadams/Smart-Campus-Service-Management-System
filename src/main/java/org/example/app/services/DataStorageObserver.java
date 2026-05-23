package org.example.app.services;

import org.example.app.models.ServiceRequest;

/**
 * Interface that allows other team modules (like Member 4's FileManager)
 * to intercept request updates and save changes without tight coupling.
 */
public interface DataStorageObserver {
    void onDataChanged();
}