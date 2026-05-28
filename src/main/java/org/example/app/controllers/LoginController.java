package org.example.app.controllers;

import org.example.app.managers.AuthenticationManager;
import org.example.app.models.User;
import org.example.app.exceptions.AuthenticationException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    public void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill all fields");
            return;
        }

        // TEMP TEST (we connect backend later)
        if (username.equals("admin")) {
            messageLabel.setText("Admin login success");
        } else {
            messageLabel.setText("Student login success");
        }
    }
}