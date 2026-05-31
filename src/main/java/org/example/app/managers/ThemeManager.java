package org.example.app.utils;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ThemeManager {

    public static void applyDark(BorderPane rootPane, Button themeButton) {
        rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f172a, #1e293b);");
        themeButton.setText("☾ Dark Mode");
        themeButton.setStyle(
                "-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-size: 13;" +
                        "-fx-font-weight: bold; -fx-background-radius: 14;" +
                        "-fx-border-color: #475569; -fx-border-radius: 14; -fx-cursor: hand;"
        );
    }

    public static void applyLight(BorderPane rootPane, Button themeButton) {
        rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #e2e8f0, #f8fafc);");
        themeButton.setText("☀ Light Mode");
        themeButton.setStyle(
                "-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-size: 13;" +
                        "-fx-font-weight: bold; -fx-background-radius: 14;" +
                        "-fx-border-color: #cbd5e1; -fx-border-radius: 14; -fx-cursor: hand;"
        );
    }

    public static void toggle(boolean isDarkMode, BorderPane rootPane, Button themeButton) {
        if (isDarkMode) {
            applyDark(rootPane, themeButton);
        } else {
            applyLight(rootPane, themeButton);
        }
    }
}