package org.example.app.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.app.Main;
import org.example.app.managers.AuthenticationManager;
import org.example.app.managers.SystemManager;
import org.example.app.models.Request;
import org.example.app.models.Service;
import org.example.app.models.Student;
import org.example.app.models.User;
import org.example.app.utils.ThemeManager;

public class RequestController {

    @FXML private ComboBox<String> serviceComboBox;
    @FXML private TextArea         notesArea;
    @FXML private Label            messageLabel;
    @FXML private Button           themeButton;
    @FXML private BorderPane       rootPane;
    @FXML private VBox             requestCard;

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
        if (!(user instanceof Student)) {
            showError("No student logged in.");
            return;
        }

        Student student = (Student) user;
        showInfo("Submitting request...");

        // Run in background so UI doesn't freeze
        new Thread(() -> {
            try {
                Service service = new Service(selectedService, "University service request");
                Request request = new Request(student, service);

                SystemManager.getInstance().addRequest(request);

                Platform.runLater(() -> {
                    showSuccess("Request submitted successfully!");
                    serviceComboBox.setValue(null);
                    if (notesArea != null) notesArea.clear();

                    // Navigate back after 1.5 seconds
                    new Thread(() -> {
                        try {
                            Thread.sleep(1500);
                            Platform.runLater(() ->
                                    Main.navigateTo("StudentDashboard.fxml", 900, 850));
                        } catch (InterruptedException ignored) {}
                    }).start();
                });

            } catch (Exception e) {
                Platform.runLater(() ->
                        showError("Failed: " + e.getMessage()));
                e.printStackTrace();
            }
        }).start();
    }

    @FXML private void goBack() { Main.navigateTo("StudentDashboard.fxml", 900, 850); }

    private void showError(String msg) {
        messageLabel.setText(msg);
        messageLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 13; -fx-font-weight: bold; -fx-alignment: CENTER;");
    }

    private void showSuccess(String msg) {
        messageLabel.setText(msg);
        messageLabel.setStyle("-fx-text-fill: #22c55e; -fx-font-size: 13; -fx-font-weight: bold; -fx-alignment: CENTER;");
    }

    private void showInfo(String msg) {
        messageLabel.setText(msg);
        messageLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13; -fx-font-weight: bold; -fx-alignment: CENTER;");
    }

    @FXML
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        ThemeManager.toggle(isDarkMode, rootPane, themeButton);
    }

}