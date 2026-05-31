package org.example.app.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.app.Main;
import org.example.app.managers.AuthenticationManager;
import org.example.app.managers.SystemManager;
import org.example.app.models.Request;
import org.example.app.models.User;
import org.example.app.utils.ThemeManager;

import java.util.ArrayList;

public class StatusController {

    @FXML private TableView<Request>           statusTable;
    @FXML private TableColumn<Request, String> serviceCol;
    @FXML private TableColumn<Request, String> dateCol;
    @FXML private TableColumn<Request, String> statusCol;
    @FXML private TableColumn<Request, String> notesCol;
    @FXML private Label      totalLabel;
    @FXML private Label      pendingLabel;
    @FXML private Label      approvedLabel;
    @FXML private Label      rejectedLabel;
    @FXML private Button     themeButton;
    @FXML private BorderPane rootPane;

    private boolean isDarkMode = true;

    @FXML
    public void initialize() {
        serviceCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getService().getName()));
        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus()));
        if (dateCol  != null)
            dateCol.setCellValueFactory(data -> new SimpleStringProperty("-"));
        if (notesCol != null)
            notesCol.setCellValueFactory(data -> new SimpleStringProperty("-"));

        loadRequestsInBackground();
    }

    @FXML
    public void refreshTable() {
        loadRequestsInBackground();
    }

    private void loadRequestsInBackground() {
        totalLabel.setText("...");
        pendingLabel.setText("...");
        approvedLabel.setText("...");
        rejectedLabel.setText("...");

        User user = AuthenticationManager.getInstance().getCurrentUser();
        if (user == null) return;

        new Thread(() -> {
            try {
                ArrayList<Request> myRequests =
                        SystemManager.getInstance().getRequestsByStudent(user.getUsername());

                long pending  = myRequests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Pending")).count();
                long approved = myRequests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Approved")).count();
                long rejected = myRequests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Rejected")).count();

                Platform.runLater(() -> {
                    statusTable.setItems(
                            FXCollections.observableArrayList(myRequests));
                    totalLabel.setText(String.valueOf(myRequests.size()));
                    pendingLabel.setText(String.valueOf(pending));
                    approvedLabel.setText(String.valueOf(approved));
                    rejectedLabel.setText(String.valueOf(rejected));
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    totalLabel.setText("0");
                    pendingLabel.setText("0");
                    approvedLabel.setText("0");
                    rejectedLabel.setText("0");
                });
            }
        }).start();
    }

    @FXML private void goBack() { Main.navigateTo("StudentDashboard.fxml", 900, 850); }

    @FXML
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        ThemeManager.toggle(isDarkMode, rootPane, themeButton);
    }
}