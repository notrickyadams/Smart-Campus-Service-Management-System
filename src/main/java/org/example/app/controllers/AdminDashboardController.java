package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.app.Main;
import org.example.app.models.Request;

public class AdminDashboardController {

    @FXML private TableView<Request> requestTable;
    @FXML private TableColumn<Request, String> studentColumn;
    @FXML private TableColumn<Request, String> serviceColumn;
    @FXML private TableColumn<Request, String> statusColumn;
    @FXML private Label totalRequestsLabel;
    @FXML private Label pendingRequestsLabel;
    @FXML private Label approvedRequestsLabel;
    @FXML private Label rejectedRequestsLabel;
    @FXML private Button themeButton;
    @FXML private BorderPane rootPane;

    private boolean isDarkMode = true;

    @FXML
    public void initialize() {
        loadRequests();
    }

    @FXML
    public void refreshUI() {
        loadRequests();
    }

    private void loadRequests() {
        // CONNECT BACKEND HERE:
        // List<Request> requests = SystemManager.getInstance().getAllRequests();
        // studentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudent().getName()));
        // serviceColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getService().getName()));
        // statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        // requestTable.setItems(FXCollections.observableArrayList(requests));
        // updateStats(requests);

        totalRequestsLabel.setText("0");
        pendingRequestsLabel.setText("0");
        approvedRequestsLabel.setText("0");
        rejectedRequestsLabel.setText("0");
    }

    @FXML
    public void handleApprove() {
        Request selected = requestTable.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Please select a request first."); return; }

        // CONNECT BACKEND HERE:
        // SystemManager.getInstance().approveRequest(selected);
        // loadRequests();

        showAlert("Request approved! (connect backend to make it real)");
    }

    @FXML
    public void handleReject() {
        Request selected = requestTable.getSelectionModel().getSelectedItem();
        if (selected == null) { showAlert("Please select a request first."); return; }

        // CONNECT BACKEND HERE:
        // SystemManager.getInstance().rejectRequest(selected);
        // loadRequests();

        showAlert("Request rejected! (connect backend to make it real)");
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
        if (isDarkMode) {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f172a, #1e293b);");
            themeButton.setText("☾ Dark Mode");
            themeButton.setStyle(
                    "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-size: 13;" +
                            "-fx-font-weight: bold; -fx-background-radius: 14;" +
                            "-fx-border-color: #475569; -fx-border-radius: 14; -fx-cursor: hand;"
            );
        } else {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #e2e8f0, #f8fafc);");
            themeButton.setText("☀ Light Mode");
            themeButton.setStyle(
                    "-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-size: 13;" +
                            "-fx-font-weight: bold; -fx-background-radius: 14;" +
                            "-fx-border-color: #cbd5e1; -fx-border-radius: 14; -fx-cursor: hand;"
            );
        }
    }
}