package org.example.app.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.app.Main;
import org.example.app.managers.AuthenticationManager;
import org.example.app.managers.SystemManager;
import org.example.app.models.Request;
import org.example.app.models.User;
import org.example.app.utils.ThemeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentAnalyticsController {

    @FXML private PieChart statusPieChart;
    @FXML private BarChart<String, Number> serviceBarChart;
    @FXML private Label     totalLabel;
    @FXML private Label     pendingLabel;
    @FXML private Label     approvedLabel;
    @FXML private Label     rejectedLabel;
    @FXML private Button    themeButton;
    @FXML private BorderPane rootPane;

    private boolean isDarkMode = true;

    @FXML
    public void initialize() {
        loadAnalyticsInBackground();
    }

    @FXML
    public void refreshAnalytics() {
        loadAnalyticsInBackground();
    }

    private void loadAnalyticsInBackground() {
        totalLabel.setText("...");
        pendingLabel.setText("...");
        approvedLabel.setText("...");
        rejectedLabel.setText("...");

        User user = AuthenticationManager.getInstance().getCurrentUser();
        if (user == null) return;

        new Thread(() -> {
            try {
                // Only this student's requests
                ArrayList<Request> requests =
                        SystemManager.getInstance().getRequestsByStudent(user.getUsername());

                long pending  = requests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Pending")).count();
                long approved = requests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Approved")).count();
                long rejected = requests.stream().filter(r -> r.getStatus().equalsIgnoreCase("Rejected")).count();

                Map<String, Integer> serviceCounts = new HashMap<>();
                for (Request r : requests) {
                    String name = r.getService().getName();
                    serviceCounts.put(name, serviceCounts.getOrDefault(name, 0) + 1);
                }

                Platform.runLater(() -> {
                    totalLabel.setText(String.valueOf(requests.size()));
                    pendingLabel.setText(String.valueOf(pending));
                    approvedLabel.setText(String.valueOf(approved));
                    rejectedLabel.setText(String.valueOf(rejected));

                    // PIE CHART
                    statusPieChart.getData().clear();
                    if (requests.isEmpty()) {
                        statusPieChart.setData(FXCollections.observableArrayList(
                                new PieChart.Data("No requests yet", 1)
                        ));
                    } else {
                        statusPieChart.setData(FXCollections.observableArrayList(
                                new PieChart.Data("Pending (" + pending + ")",   Math.max(pending, 0)),
                                new PieChart.Data("Approved (" + approved + ")", Math.max(approved, 0)),
                                new PieChart.Data("Rejected (" + rejected + ")", Math.max(rejected, 0))
                        ));
                    }

                    // BAR CHART
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    series.setName("My Requests");
                    for (Map.Entry<String, Integer> entry : serviceCounts.entrySet()) {
                        series.getData().add(
                                new XYChart.Data<>(entry.getKey(), entry.getValue())
                        );
                    }
                    serviceBarChart.getData().clear();
                    serviceBarChart.getData().add(series);
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

    @FXML
    private void goBack() {
        Main.navigateTo("StudentDashboard.fxml", 900, 850);
    }

    @FXML
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        ThemeManager.toggle(isDarkMode, rootPane, themeButton);
    }

    private void applyDark() {
        rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f172a, #1e293b);");
        themeButton.setText("☾ Dark Mode");
        themeButton.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 14; -fx-border-color: #475569; -fx-border-radius: 14; -fx-cursor: hand;");
    }

    private void applyLight() {
        rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #e2e8f0, #f8fafc);");
        themeButton.setText("☀ Light Mode");
        themeButton.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 14; -fx-border-color: #cbd5e1; -fx-border-radius: 14; -fx-cursor: hand;");
    }
}