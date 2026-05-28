package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.app.Main;
import org.example.app.models.Request;

public class StatusController {

    @FXML private TableView<Request> statusTable;
    @FXML private TableColumn<Request, String> serviceCol;
    @FXML private TableColumn<Request, String> dateCol;
    @FXML private TableColumn<Request, String> statusCol;
    @FXML private TableColumn<Request, String> notesCol;
    @FXML private Label totalLabel;
    @FXML private Label pendingLabel;
    @FXML private Label approvedLabel;
    @FXML private Label rejectedLabel;
    @FXML private Button themeButton;
    @FXML private BorderPane rootPane;

    private boolean isDarkMode = true;

    @FXML
    public void initialize() {
        loadRequests();
    }

    @FXML
    public void refreshTable() {
        loadRequests();
    }

    private void loadRequests() {
        // CONNECT BACKEND HERE:
        // Student student = SystemManager.getInstance().getCurrentStudent();
        // List<Request> requests = SystemManager.getInstance().getRequestsByStudent(student);
        // serviceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getService().getName()));
        // statusCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        // dateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
        // notesCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNotes()));
        // statusTable.setItems(FXCollections.observableArrayList(requests));
        // updateStats(requests);

        totalLabel.setText("0");
        pendingLabel.setText("0");
        approvedLabel.setText("0");
        rejectedLabel.setText("0");
    }

    @FXML private void goBack() { Main.navigateTo("StudentDashboard.fxml", 900, 850); }

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