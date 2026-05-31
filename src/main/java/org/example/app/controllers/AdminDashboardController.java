package org.example.app.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.app.managers.SystemManager;
import org.example.app.models.Request;
import org.example.app.utils.ThemeManager;

public class AdminDashboardController {

    @FXML private TableView<Request>           requestTable;
    @FXML private TableColumn<Request, String> studentColumn;
    @FXML private TableColumn<Request, String> serviceColumn;
    @FXML private TableColumn<Request, String> statusColumn;
    @FXML private Label      totalRequestsLabel;
    @FXML private Label      pendingRequestsLabel;
    @FXML private Label      approvedRequestsLabel;
    @FXML private Label      rejectedRequestsLabel;
    @FXML private Button     themeButton;
    @FXML private BorderPane rootPane;

    private boolean isDarkMode = true;

    @FXML
    public void initialize() {
        studentColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStudent().getUsername()));
        serviceColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getService().getName()));
        statusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus()));

        loadRequestsInBackground();
    }

    @FXML
    public void refreshUI() {
        loadRequestsInBackground();
    }

    private void loadRequestsInBackground() {
        // Show loading
        totalRequestsLabel.setText("...");
        pendingRequestsLabel.setText("...");
        approvedRequestsLabel.setText("...");
        rejectedRequestsLabel.setText("...");

        new Thread(() -> {
            try {
                java.util.ArrayList<Request> requests =
                        SystemManager.getInstance().getAllRequests();

                long pending  = requests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Pending")).count();
                long approved = requests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Approved")).count();
                long rejected = requests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Rejected")).count();

                Platform.runLater(() -> {
                    requestTable.setItems(
                            FXCollections.observableArrayList(requests));
                    totalRequestsLabel.setText(String.valueOf(requests.size()));
                    pendingRequestsLabel.setText(String.valueOf(pending));
                    approvedRequestsLabel.setText(String.valueOf(approved));
                    rejectedRequestsLabel.setText(String.valueOf(rejected));
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    totalRequestsLabel.setText("0");
                    pendingRequestsLabel.setText("0");
                    approvedRequestsLabel.setText("0");
                    rejectedRequestsLabel.setText("0");
                });
            }
        }).start();
    }

    @FXML
    public void handleApprove() {
        Request selected = requestTable.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Select a request first."); return; }
        if (!selected.getStatus().equalsIgnoreCase("Pending")) {
            showAlert("Only pending requests can be approved."); return;
        }

        SystemManager.getInstance().approveRequest(selected);

        new Thread(() -> {
            try {
                Thread.sleep(3500);
                Platform.runLater(this::loadRequestsInBackground);
            } catch (InterruptedException ignored) {}
        }).start();

        showAlert("Approving... table refreshes in 3 seconds.");
    }

    @FXML
    public void handleReject() {
        Request selected = requestTable.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Select a request first."); return; }
        if (!selected.getStatus().equalsIgnoreCase("Pending")) {
            showAlert("Only pending requests can be rejected."); return;
        }

        SystemManager.getInstance().rejectRequest(selected);

        new Thread(() -> {
            try {
                Thread.sleep(3500);
                Platform.runLater(this::loadRequestsInBackground);
            } catch (InterruptedException ignored) {}
        }).start();

        showAlert("Rejecting... table refreshes in 3 seconds.");
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin Action");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        ThemeManager.toggle(isDarkMode, rootPane, themeButton);
    }


    @FXML
    private void goToAnalytics() {
        org.example.app.Main.navigateTo("AnalyticsScreen.fxml", 900, 850);
    }
}