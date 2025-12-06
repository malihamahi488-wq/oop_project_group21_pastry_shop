package com.example.BranchManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller for trackDailySalesSummary.fxml
 *
 * CRA mapping:
 * - event-1 UIE: opening screen -> initialize() prepares UI
 * - event-2 DP: system retrieves daily sales data -> model.viewDailySummary()
 * - event-3 OP: display results -> updateUIFromSummary()
 * - event-4 UID: manager exports summary -> onExportPdf/onExportExcel
 * - event-5 OP: system downloads the summary -> showInfo alert
 */
public class TrackDailySalesSummaryController {

    @FXML private DatePicker datePicker;
    @FXML private Label lblTotalRevenue;
    @FXML private Label lblItemsSold;
    @FXML private ListView<String> lvBestSellers;

    @FXML private Button exportPdfBtn;
    @FXML private Button exportExcelBtn;

    private TrackDailySalesSummary model;

    @FXML
    public void initialize() {
        model = new TrackDailySalesSummary(101); // pass managerID as needed
        datePicker.setValue(LocalDate.now());
        loadSummaryFor(datePicker.getValue());
    }

    @FXML
    public void onLoadSummary() {
        LocalDate date = datePicker.getValue();
        if (date == null) {
            showError("No date selected", "Please choose a date to load the summary.");
            return;
        }
        loadSummaryFor(date);
    }

    private void loadSummaryFor(LocalDate date) {
        // DP: fetch daily summary for the date
        TrackDailySalesSummary.DailySummary summary = model.viewDailySummary(date);
        // OP: update UI
        updateUIFromSummary(summary);
    }

    private void updateUIFromSummary(TrackDailySalesSummary.DailySummary s) {
        if (s == null) {
            lblTotalRevenue.setText("0.00");
            lblItemsSold.setText("0");
            lvBestSellers.getItems().clear();
            return;
        }
        lblTotalRevenue.setText(String.format("%.2f", s.getTotalRevenue()));
        lblItemsSold.setText(String.valueOf(s.getItemsSold()));
        lvBestSellers.getItems().setAll(s.getBestSellers());
    }

    @FXML
    public void onRefresh() {
        datePicker.setValue(LocalDate.now());
        loadSummaryFor(datePicker.getValue());
    }

    @FXML
    public void onExportPdf() {
        try {
            File f = model.exportSummary(datePicker.getValue(), "pdf");
            showInfo("Export complete", "PDF saved to:\n" + f.getAbsolutePath());
        } catch (IOException e) {
            showError("Export failed", e.getMessage());
        }
    }

    @FXML
    public void onExportExcel() {
        try {
            File f = model.exportSummary(datePicker.getValue(), "xlsx");
            showInfo("Export complete", "Excel saved to:\n" + f.getAbsolutePath());
        } catch (IOException e) {
            showError("Export failed", e.getMessage());
        }
    }

    // Small helpers
    private void showError(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            a.setTitle(title);
            a.setHeaderText(null);
            a.showAndWait();
        });
    }

    private void showInfo(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            a.setTitle(title);
            a.setHeaderText(null);
            a.showAndWait();
        });
    }
}
