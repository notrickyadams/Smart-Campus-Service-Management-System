package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.app.Main;
import org.example.app.models.Request;

public class StudentDashboardController {

    @FXML private Label studentNameLabel;
    @FXML private Label studentIdLabel;
    @FXML private Label totalLabel;
    @FXML private Label pendingLabel;
    @FXML private Label approvedLabel;
    @FXML private TableView<Request> recentRequestsTable;
    @FXML private TableColumn<Request, String> serviceColumn;
    @FXML private TableColumn<Request, String> dateColumn;
    @FXML private TableColumn<Request, String> statusColumn;
    @FXML private Button themeButton;
    @FXML private BorderPane rootPane;

    private boolean isDarkMode = true;

    @FXML
    public void initialize() {
        // TEMP — replace with real student from SystemManager later
        studentNameLabel.setText("John Doe");
        studentIdLabel.setText("ID: 20230001");
        totalLabel.setText("0");
        pendingLabel.setText("0");
        approvedLabel.setText("0");
    }

    @FXML private void handleTranscriptRequest()  { goToRequestWith("Transcript Request"); }
    @FXML private void handleIDRequest()           { goToRequestWith("ID Replacement"); }
    @FXML private void handleEnrollmentRequest()   { goToRequestWith("Enrollment Letter"); }
    @FXML private void handleWithdrawalRequest()   { goToRequestWith("Course Withdrawal"); }
    @FXML private void goToRequest()               { Main.navigateTo("RequestScreen.fxml", 900, 850); }
    @FXML private void goToStatus()                { Main.navigateTo("StatusScreen.fxml", 900, 850); }
    @FXML private void handleLogout()              { Main.navigateTo("Login.fxml", 800, 850); }

    private void goToRequestWith(String serviceType) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/app/views/RequestScreen.fxml")
            );
            Parent root = loader.load();
            RequestController controller = loader.getController();
            controller.preSelectService(serviceType);
            Stage stage = (Stage) studentNameLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 850));
        } catch (Exception e) {
            e.printStackTrace();
        }
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