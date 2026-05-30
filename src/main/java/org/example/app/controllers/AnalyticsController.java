package org.example.app.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.app.Main;
import org.example.app.managers.SystemManager;
import org.example.app.models.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsController {

    @FXML private PieChart  statusPieChart;
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
        loadAnalytics();
    }

    @FXML
    public void refreshAnalytics() {
        loadAnalytics();
    }

    private void loadAnalytics() {
        ArrayList<Request> requests = SystemManager.getInstance().getAllRequests();

        int total    = requests.size();
        int pending  = SystemManager.getInstance().getPendingRequestsCount();
        int approved = SystemManager.getInstance().getApprovedRequestsCount();
        int rejected = SystemManager.getInstance().getRejectedRequestsCount();

        // Update stat labels
        totalLabel.setText(String.valueOf(total));
        pendingLabel.setText(String.valueOf(pending));
        approvedLabel.setText(String.valueOf(approved));
        rejectedLabel.setText(String.valueOf(rejected));

        // PIE CHART — status breakdown
        statusPieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Pending (" + pending + ")",   pending),
                new PieChart.Data("Approved (" + approved + ")", approved),
                new PieChart.Data("Rejected (" + rejected + ")", rejected)
        ));
        statusPieChart.setLegendVisible(true);
        statusPieChart.setLabelsVisible(true);

        // BAR CHART — most requested services
        Map<String, Integer> serviceCounts = new HashMap<>();
        for (Request r : requests) {
            String name = r.getService().getName();
            serviceCounts.put(name, serviceCounts.getOrDefault(name, 0) + 1);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Requests");
        for (Map.Entry<String, Integer> entry : serviceCounts.entrySet()) {
            series.getData().add(
                    new XYChart.Data<>(entry.getKey(), entry.getValue())
            );
        }

        serviceBarChart.getData().clear();
        serviceBarChart.getData().add(series);
        serviceBarChart.setLegendVisible(false);
    }

    @FXML
    private void goBack() {
        Main.navigateTo("AdminDashboard.fxml", 900, 850);
    }

    @FXML
    public void toggleTheme() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f172a, #1e293b);");
            themeButton.setText("☾ Dark Mode");
            themeButton.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 14; -fx-border-color: #475569; -fx-border-radius: 14; -fx-cursor: hand;");
        } else {
            rootPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #e2e8f0, #f8fafc);");
            themeButton.setText("☀ Light Mode");
            themeButton.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-size: 13; -fx-font-weight: bold; -fx-background-radius: 14; -fx-border-color: #cbd5e1; -fx-border-radius: 14; -fx-cursor: hand;");
        }
    }
}