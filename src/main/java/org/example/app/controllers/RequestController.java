package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.app.Main;
import org.example.app.managers.AuthenticationManager;
import org.example.app.models.Request;
import org.example.app.models.Service;
import org.example.app.models.Student;
import org.example.app.models.User;

public class RequestController {

    @FXML private ComboBox<String> serviceComboBox;
    @FXML private TextArea notesArea;
    @FXML private Label messageLabel;
    @FXML private Button themeButton;
    @FXML private BorderPane rootPane;
    @FXML private VBox requestCard;

    private boolean isDarkMode = true;

    @FXML
    public void initialize() {
        serviceComboBox.setItems(FXCollections.observableArrayList(
                "Transcript Request",
                "ID Replacement",
                "Enrollment Letter",
                "Course Withdrawal"
        ));
    }

    public void preSelectService(String serviceType) {
        serviceComboBox.setValue(serviceType);
    }

    @FXML
    public void handleSubmit() {
        String selectedService = serviceComboBox.getValue();

        if (selectedService == null || selectedService.isEmpty()) {
            showError("Please select a service.");
            return;
        }

        User user = AuthenticationManager.getInstance().getCurrentUser();

        if (!(user instanceof Student student)) {
            showError("No student logged in.");
            return;
        }

        // Create real objects using YOUR models
        Service service = new Service(selectedService, "University service request");
        Request request = new Request(student, service);

        // Add to SystemManager — Member 3's job, uncomment when ready:
        // SystemManager.getInstance().addRequest(request);

        showSuccess("Request submitted successfully!");
        serviceComboBox.setValue(null);
        notesArea.clear();

        new Thread(() -> {
            try {
                Thread.sleep(1200);
                javafx.application.Platform.runLater(() ->
                        Main.navigateTo("StudentDashboard.fxml", 900, 850)
                );
            } catch (InterruptedException ignored) {}
        }).start();
    }

    @FXML private void goBack() { Main.navigateTo("StudentDashboard.fxml", 900, 850); }

    private void showError(String msg) {
        messageLabel.setText(msg);
        messageLabel.setStyle(
                "-fx-text-fill: #ef4444; -fx-font-size: 13;" +
                        "-fx-font-weight: bold; -fx-alignment: CENTER;"
        );
    }

    private void showSuccess(String msg) {
        messageLabel.setText(msg);
        messageLabel.setStyle(
                "-fx-text-fill: #22c55e; -fx-font-size: 13;" +
                        "-fx-font-weight: bold; -fx-alignment: CENTER;"
        );
    }

    @FXML
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f172a, #1e293b);");
            requestCard.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.05); -fx-background-radius: 24;" +
                            "-fx-padding: 45; -fx-border-color: rgba(255,255,255,0.08); -fx-border-radius: 24;"
            );
            themeButton.setText("☾ Dark Mode");
            themeButton.setStyle(
                    "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-size: 13;" +
                            "-fx-font-weight: bold; -fx-background-radius: 14;" +
                            "-fx-border-color: #475569; -fx-border-radius: 14; -fx-cursor: hand;"
            );
        } else {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #e2e8f0, #f8fafc);");
            requestCard.setStyle(
                    "-fx-background-color: white; -fx-background-radius: 24; -fx-padding: 45;" +
                            "-fx-border-color: #e2e8f0; -fx-border-radius: 24;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 4);"
            );
            themeButton.setText("☀ Light Mode");
            themeButton.setStyle(
                    "-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-size: 13;" +
                            "-fx-font-weight: bold; -fx-background-radius: 14;" +
                            "-fx-border-color: #cbd5e1; -fx-border-radius: 14; -fx-cursor: hand;"
            );
        }
    }
}