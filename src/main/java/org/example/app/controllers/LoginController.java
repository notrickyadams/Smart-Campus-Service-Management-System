package org.example.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.app.Main;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    @FXML private Button themeButton;
    @FXML private BorderPane rootPane;
    @FXML private VBox loginCard;

    private boolean isDarkMode = true;

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        if (username.equals("admin") && password.equals("admin123")) {
            showSuccess("Welcome, Admin!");
            Main.navigateTo("AdminDashboard.fxml", 900, 850);

        } else if (username.equals("student") && password.equals("student123")) {
            showSuccess("Welcome, Student!");
            Main.navigateTo("StudentDashboard.fxml", 900, 850);

        } else {
            showError("Invalid username or password.");
        }
    }

    @FXML
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f172a, #1e293b);");
            loginCard.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.05);" +
                            "-fx-background-radius: 24; -fx-padding: 45;" +
                            "-fx-border-color: rgba(255,255,255,0.08); -fx-border-radius: 24;"
            );
            usernameField.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.07); -fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #475569; -fx-font-size: 14;" +
                            "-fx-background-radius: 14; -fx-border-color: #334155;" +
                            "-fx-border-radius: 14; -fx-padding: 0 15 0 15;"
            );
            passwordField.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.07); -fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #475569; -fx-font-size: 14;" +
                            "-fx-background-radius: 14; -fx-border-color: #334155;" +
                            "-fx-border-radius: 14; -fx-padding: 0 15 0 15;"
            );
            themeButton.setText("☾ Dark Mode");
            themeButton.setStyle(
                    "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-size: 13;" +
                            "-fx-font-weight: bold; -fx-background-radius: 14;" +
                            "-fx-border-color: #475569; -fx-border-radius: 14; -fx-cursor: hand;"
            );
        } else {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #e2e8f0, #f8fafc);");
            loginCard.setStyle(
                    "-fx-background-color: white; -fx-background-radius: 24; -fx-padding: 45;" +
                            "-fx-border-color: #e2e8f0; -fx-border-radius: 24;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 4);"
            );
            usernameField.setStyle(
                    "-fx-background-color: #f8fafc; -fx-text-fill: #0f172a;" +
                            "-fx-prompt-text-fill: #94a3b8; -fx-font-size: 14;" +
                            "-fx-background-radius: 14; -fx-border-color: #cbd5e1;" +
                            "-fx-border-radius: 14; -fx-padding: 0 15 0 15;"
            );
            passwordField.setStyle(
                    "-fx-background-color: #f8fafc; -fx-text-fill: #0f172a;" +
                            "-fx-prompt-text-fill: #94a3b8; -fx-font-size: 14;" +
                            "-fx-background-radius: 14; -fx-border-color: #cbd5e1;" +
                            "-fx-border-radius: 14; -fx-padding: 0 15 0 15;"
            );
            themeButton.setText("☀ Light Mode");
            themeButton.setStyle(
                    "-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-size: 13;" +
                            "-fx-font-weight: bold; -fx-background-radius: 14;" +
                            "-fx-border-color: #cbd5e1; -fx-border-radius: 14; -fx-cursor: hand;"
            );
        }
    }

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
}