package org.example.app.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.app.Main;
import org.example.app.managers.AuthenticationManager;

public class RegisterController {

    @FXML private TextField     usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label         messageLabel;
    @FXML private BorderPane    rootPane;
    @FXML private VBox          registerCard;



    @FXML
    public void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirm  = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }
        if (username.length() < 3) {
            showError("Username must be at least 3 characters.");
            return;
        }
        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            return;
        }
        if (!password.equals(confirm)) {
            showError("Passwords do not match.");
            return;
        }

        // Show loading while we call Supabase in background
        showInfo("Creating account...");

        new Thread(() -> {
            try {
                boolean success = AuthenticationManager.getInstance()
                        .register(username, password);

                Platform.runLater(() -> {
                    if (success) {
                        showSuccess("Account created! Go back and sign in.");
                    } else {
                        showError("Registration failed. Try a different username.");
                    }
                });

            } catch (Exception e) {
                Platform.runLater(() ->
                        showError("Error: " + e.getMessage())
                );
            }
        }).start();
    }

    @FXML
    private void goToLogin() {
        Main.navigateTo("Login.fxml", 800, 850);
    }

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
}