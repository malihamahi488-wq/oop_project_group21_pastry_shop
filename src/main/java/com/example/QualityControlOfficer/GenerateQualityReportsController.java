package com.example.QualityControlOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenerateQualityReportsController {

    @FXML
    private Label lblOfficerId;

    @FXML
    private DatePicker dpFrom;

    @FXML
    private DatePicker dpTo;

    @FXML
    private ComboBox<String> cbProductType;

    @FXML
    private TableView<GenerateQualityReports.ReportMetric> tblReport;
    @FXML
    private TableColumn<GenerateQualityReports.ReportMetric, String> colMetric;
    @FXML
    private TableColumn<GenerateQualityReports.ReportMetric, String> colValue;

    @FXML
    private Label lblReportInfo;

    @FXML
    private Label lblStatus;

    private final GenerateQualityReports model = new GenerateQualityReports();
    private final ObservableList<GenerateQualityReports.ReportMetric> reportData =
            FXCollections.observableArrayList();

    // Keep current report for download
    private GenerateQualityReports.QualityReport currentReport;

    @FXML
    private void initialize() {
        // Simulate officer ID from login
        model.setOfficerID(3001);
        lblOfficerId.setText(String.valueOf(model.getOfficerID()));

        setupProductTypes();
        setupReportTable();

        lblStatus.setText("Ready to generate quality reports.");
    }

    private void setupProductTypes() {
        cbProductType.setItems(FXCollections.observableArrayList(
                "All",
                "Cake",
                "Pastry",
                "Drink",
                "Snack"
        ));
        cbProductType.getSelectionModel().select("All");
    }

    private void setupReportTable() {
        colMetric.setCellValueFactory(new PropertyValueFactory<>("metric"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tblReport.setItems(reportData);
    }

    @FXML
    private void onResetFilters() {
        dpFrom.setValue(null);
        dpTo.setValue(null);
        cbProductType.getSelectionModel().select("All");
        reportData.clear();
        lblReportInfo.setText("No report generated yet.");
        currentReport = null;
        lblStatus.setText("Filters reset.");
    }

    @FXML
    private void onGenerateReport() {
        LocalDate from = dpFrom.getValue();
        LocalDate to = dpTo.getValue();
        String productType = cbProductType.getValue();

        if (from == null || to == null) {
            showError("Please select both 'Date From' and 'Date To'.");
            return;
        }
        if (to.isBefore(from)) {
            showError("'Date To' cannot be before 'Date From'.");
            return;
        }
        if (productType == null || productType.isBlank()) {
            productType = "All";
        }

        // Prepare dateRange string to match UML
        String dateRange = from.toString() + " to " + to.toString();

        // event-4: System pulls data via model.generateReport(...)
        currentReport = model.generateReport(dateRange, productType);

        // Display summary
        if (currentReport == null) {
            showError("Failed to generate report.");
            return;
        }

        reportData.clear();
        List<GenerateQualityReports.ReportMetric> metrics = new ArrayList<>();
        metrics.add(new GenerateQualityReports.ReportMetric("Report ID", String.valueOf(currentReport.getReportID())));
        metrics.add(new GenerateQualityReports.ReportMetric("Date Range", currentReport.getDateRange()));
        metrics.add(new GenerateQualityReports.ReportMetric("Product Type", currentReport.getProductType()));
        metrics.add(new GenerateQualityReports.ReportMetric("Total Products Checked",
                String.valueOf(currentReport.getTotalProductsChecked())));
        metrics.add(new GenerateQualityReports.ReportMetric("Total Passed",
                String.valueOf(currentReport.getTotalPassed())));
        metrics.add(new GenerateQualityReports.ReportMetric("Total Failed",
                String.valueOf(currentReport.getTotalFailed())));
        metrics.add(new GenerateQualityReports.ReportMetric("Defect Rate (%)",
                String.format("%.2f", currentReport.getDefectRatePercent())));

        // add extra details from map if present
        currentReport.getExtraMetrics().forEach((k, v) ->
                metrics.add(new GenerateQualityReports.ReportMetric(k, String.valueOf(v)))
        );

        reportData.addAll(metrics);

        lblReportInfo.setText("Report generated. ID: " + currentReport.getReportID());
        lblStatus.setText("Report generated successfully.");
    }

    @FXML
    private void onDownloadReport() {
        if (currentReport == null) {
            showError("No report to download. Please generate a report first.");
            return;
        }

        File file = model.downloadReport(currentReport.getReportID());
        if (file != null) {
            showInfo("Report downloaded/saved to:\n" + file.getAbsolutePath());
            lblStatus.setText("Report downloaded.");
        } else {
            showError("Failed to download report.");
        }
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
