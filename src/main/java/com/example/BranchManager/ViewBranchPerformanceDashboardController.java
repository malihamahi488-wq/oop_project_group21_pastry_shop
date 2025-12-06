package com.example.BranchManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Map;

public class ViewBranchPerformanceDashboardController {

    @FXML
    private Label lblManagerId;
    @FXML
    private ComboBox<String> cbPeriod;
    @FXML
    private ComboBox<String> cbMetric;
    @FXML
    private LineChart<String, Number> linePerformance;
    @FXML
    private PieChart pieCategory;
    @FXML
    private Label lblLineChartTitle;
    @FXML
    private Label lblStatus;

    private final ViewBranchPerformanceDashboard dashboardModel = new ViewBranchPerformanceDashboard();

    @FXML
    private void initialize() {
        // Simulate the manager who is logged in
        dashboardModel.setManagerID(1001);
        lblManagerId.setText(String.valueOf(dashboardModel.getManagerID()));

        setupFilters();
        loadInitialDashboard();
    }

    private void setupFilters() {
        cbPeriod.setItems(FXCollections.observableArrayList(
                "Today", "This Week", "This Month", "This Year"
        ));
        cbPeriod.getSelectionModel().select("This Month");

        cbMetric.setItems(FXCollections.observableArrayList(
                "Revenue", "Orders", "Customer Rating"
        ));
        cbMetric.getSelectionModel().select("Revenue");
    }

    private void loadInitialDashboard() {
        // Default dashboard without explicit filter (or using model defaults)
        ViewBranchPerformanceDashboard.PerformanceSummary summary =
                dashboardModel.viewDashboard();
        String metric = cbMetric.getValue();
        updateCharts(summary, metric);
        lblStatus.setText("Dashboard loaded.");
    }

    @FXML
    private void onRefreshDashboard() {
        loadInitialDashboard();
    }

    @FXML
    private void onApplyFilters() {
        String period = cbPeriod.getValue();
        String metric = cbMetric.getValue();

        if (period == null || metric == null) {
            showWarning("Please select both period and metric.");
            return;
        }

        // Apply filters to model (type, value)
        dashboardModel.applyFilter("period", period);
        ViewBranchPerformanceDashboard.PerformanceSummary summary =
                dashboardModel.applyFilter("metric", metric);

        updateCharts(summary, metric);
        lblStatus.setText("Filters applied: " + period + " / " + metric);
    }

    private void updateCharts(ViewBranchPerformanceDashboard.PerformanceSummary summary, String metric) {
        // Update line chart depending on selected metric
        linePerformance.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(metric);

        List<ViewBranchPerformanceDashboard.MetricPoint> points;
        switch (metric) {
            case "Orders":
                points = summary.getOrdersSeries();
                lblLineChartTitle.setText("Orders Over Time");
                break;
            case "Customer Rating":
                points = summary.getRatingSeries();
                lblLineChartTitle.setText("Customer Rating Over Time");
                break;
            case "Revenue":
            default:
                points = summary.getRevenueSeries();
                lblLineChartTitle.setText("Revenue Over Time");
                break;
        }

        for (ViewBranchPerformanceDashboard.MetricPoint p : points) {
            series.getData().add(new XYChart.Data<>(p.getLabel(), p.getValue()));
        }
        linePerformance.getData().add(series);

        // Update pie chart with category share
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Double> entry : summary.getCategoryShare().entrySet()) {
            pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieCategory.setData(pieData);
    }

    private void showWarning(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }

    @SuppressWarnings("unused")
    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    @SuppressWarnings("unused")
    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
