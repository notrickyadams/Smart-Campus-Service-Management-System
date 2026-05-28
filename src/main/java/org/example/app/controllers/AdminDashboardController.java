package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.example.app.managers.SystemManager;
import org.example.app.models.Request;

public class AdminDashboardController {

    private SystemManager systemManager;

    // ================= UI ELEMENTS (FROM FXML) =================

    @FXML private TableView<Request> requestTable;

    @FXML private Label totalRequestsLabel;
    @FXML private Label pendingRequestsLabel;
    @FXML private Label approvedRequestsLabel;
    @FXML private Label rejectedRequestsLabel;

    // ================= INITIALIZE =================

    @FXML
    public void initialize() {

        systemManager = SystemManager.getInstance();

        loadRequests();
        updateStats();
    }

    // ================= BUTTON ACTIONS =================

    @FXML
    public void handleApprove() {

        Request selected = requestTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            systemManager.approveRequest(selected);
            refreshUI();
        }
    }

    @FXML
    public void handleReject() {

        Request selected = requestTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            systemManager.rejectRequest(selected);
            refreshUI();
        }
    }

    @FXML
    public void refreshUI() {
        loadRequests();
        updateStats();
    }

    // ================= DATA LOADING =================

    private void loadRequests() {

        ObservableList<Request> data =
                FXCollections.observableArrayList(systemManager.getAllRequests());

        requestTable.setItems(data);
    }

    // ================= STATS =================

    private void updateStats() {

        totalRequestsLabel.setText(String.valueOf(systemManager.getTotalRequests()));
        pendingRequestsLabel.setText(String.valueOf(systemManager.getPendingRequestsCount()));
        approvedRequestsLabel.setText(String.valueOf(systemManager.getApprovedRequestsCount()));
        rejectedRequestsLabel.setText(String.valueOf(systemManager.getRejectedRequestsCount()));
    }
}