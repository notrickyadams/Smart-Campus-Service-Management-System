package org.example.app.controllers;

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

        boolean success = AuthenticationManager.getInstance()
                .register(username, password);

        if (success) {
            showSuccess("Account created! Redirecting to login...");
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    javafx.application.Platform.runLater(() ->
                            Main.navigateTo("Login.fxml", 800, 850)
                    );
                } catch (InterruptedException ignored) {}
            }).start();
        } else {
            showError("Username already taken. Try another.");
        }
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
}