package org.example.app.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.Main;
import org.example.app.managers.AuthenticationManager;
import org.example.app.managers.SystemManager;
import org.example.app.models.Request;
import org.example.app.models.Student;
import org.example.app.models.User;
import org.example.app.utils.ThemeManager;

import java.util.ArrayList;

public class StudentDashboardController {

    @FXML private Label              studentNameLabel;
    @FXML private Label              studentIdLabel;
    @FXML private Label              totalLabel;
    @FXML private Label              pendingLabel;
    @FXML private Label              approvedLabel;
    @FXML private TableView<Request> recentRequestsTable;
    @FXML private TableColumn<Request, String> serviceColumn;
    @FXML private TableColumn<Request, String> dateColumn;
    @FXML private TableColumn<Request, String> statusColumn;
    @FXML private Button             themeButton;
    @FXML private BorderPane         rootPane;
    @FXML
    private void goToAnalytics() {
        Main.navigateTo("StudentAnalyticsScreen.fxml", 900, 850);
    }
    private boolean isDarkMode = true;

    @FXML
    public void initialize() {
        User user = AuthenticationManager.getInstance().getCurrentUser();
        if (user != null) {
            studentNameLabel.setText(user.getUsername());
            studentIdLabel.setText("Student Account");
        }

        serviceColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getService().getName()));
        statusColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus()));
        if (dateColumn != null)
            dateColumn.setCellValueFactory(data ->
                    new SimpleStringProperty("-"));

        loadRequestsInBackground();
    }

    private void loadRequestsInBackground() {
        totalLabel.setText("...");
        pendingLabel.setText("...");
        approvedLabel.setText("...");

        User user = AuthenticationManager.getInstance().getCurrentUser();
        if (user == null) return;

        new Thread(() -> {
            try {
                ArrayList<Request> myRequests =
                        SystemManager.getInstance().getRequestsByStudent(user.getUsername());

                long pending  = myRequests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Pending")).count();
                long approved = myRequests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Approved")).count();

                Platform.runLater(() -> {
                    recentRequestsTable.setItems(
                            FXCollections.observableArrayList(myRequests));
                    totalLabel.setText(String.valueOf(myRequests.size()));
                    pendingLabel.setText(String.valueOf(pending));
                    approvedLabel.setText(String.valueOf(approved));
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    totalLabel.setText("0");
                    pendingLabel.setText("0");
                    approvedLabel.setText("0");
                });
            }
        }).start();
    }

    @FXML private void handleTranscriptRequest() { goToRequestWith("Transcript Request"); }
    @FXML private void handleIDRequest()          { goToRequestWith("ID Replacement"); }
    @FXML private void handleEnrollmentRequest()  { goToRequestWith("Enrollment Letter"); }
    @FXML private void handleWithdrawalRequest()  { goToRequestWith("Course Withdrawal"); }
    @FXML private void goToRequest()              { Main.navigateTo("RequestScreen.fxml", 900, 850); }
    @FXML private void goToStatus()               { Main.navigateTo("StatusScreen.fxml", 900, 850); }

    @FXML
    private void handleLogout() {
        AuthenticationManager.getInstance().logout();
        Main.navigateTo("Login.fxml", 800, 850);
    }

    private void goToRequestWith(String serviceType) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/app/fxml/RequestScreen.fxml")
            );
            Parent root = loader.load();
            RequestController controller = loader.getController();
            controller.preSelectService(serviceType);
            Stage stage = (Stage) studentNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 850));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        ThemeManager.toggle(isDarkMode, rootPane, themeButton);
    }
}