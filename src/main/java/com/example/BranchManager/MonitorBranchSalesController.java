package com.example.BranchManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.time.LocalDate;

/**
 * Controller for MonitorBranchSales.fxml
 *
 * Maps to CRA workflow:
 * - event-1 (UIE) : opening screen -> initialize() will load data
 * - event-2 (DP)  : fetch sales data -> model.viewSalesDashboard / applySalesFilter
 * - event-3 (OP)  : display summary -> updateLabels(...)
 * - event-4 (UID) : user applies filters -> onApplyFilter()
 * - event-5 (DP,OP): update dashboard -> applySalesFilter
 * - event-6 (UID) : export report -> onExportPdf/onExportExcel
 * - event-7 (OP) : notify user of exported file -> showAlert(...)
 */
public class MonitorBranchSalesController {

    // UI components bound from FXML
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private ComboBox<String> productCombo;

    @FXML private Label totalRevenueLabel;
    @FXML private Label totalOrdersLabel;
    @FXML private Label bestProductLabel;

    @FXML private Button applyFilterBtn;
    @FXML private Button exportPdfBtn;
    @FXML private Button exportExcelBtn;

    // model instance (UML class MonitorBranchSales)
    private MonitorBranchSales model;

    @FXML
    public void initialize() {
        // instantiate model (managerID and branchID can be passed dynamically)
        model = new MonitorBranchSales(101, 10);

        // populate combos with sample values - replace with DB values later
        categoryCombo.getItems().addAll("All", "Cakes", "Pastries", "Breads", "Drinks");
        productCombo.getItems().addAll("All", "Chocolate Cake", "Vanilla Cupcake", "Blueberry Muffin", "Croissant");

        // select defaults
        categoryCombo.getSelectionModel().selectFirst();
        productCombo.getSelectionModel().selectFirst();

        // initial load maps to CRA event-2 + event-3
        loadDashboard();
    }

    // Load initial dashboard
    private void loadDashboard() {
        // DP: fetch sales summary
        MonitorBranchSales.SalesSummary summary = model.viewSalesDashboard();
        // OP: display
        updateLabels(summary);
    }

    // Apply filters (called by Apply Filters button) - CRA event-4 => event-5
    @FXML
    public void onApplyFilter() {
        String selectedCategory = categoryCombo.getValue();
        String selectedProduct = productCombo.getValue();
        LocalDate date = datePicker.getValue();

        // build filter in a simple form (model stores filters map)
        if (selectedCategory != null && !selectedCategory.equals("All")) {
            model.applySalesFilter("category", selectedCategory);
        }

        if (selectedProduct != null && !selectedProduct.equals("All")) {
            model.applySalesFilter("product", selectedProduct);
        }

        if (date != null) {
            model.applySalesFilter("date", date.toString());
        }

        // Get filtered summary (placeholder)
        MonitorBranchSales.SalesSummary summary = model.applySalesFilter("combined", ""); // model uses current filters
        updateLabels(summary);
    }

    // Refresh (re-fetch without filters)
    @FXML
    public void onRefresh() {
        model.clearFilters();
        categoryCombo.getSelectionModel().selectFirst();
        productCombo.getSelectionModel().selectFirst();
        datePicker.setValue(null);
        loadDashboard();
    }

    // Export as PDF (CRA event-6 and event-7)
    @FXML
    public void onExportPdf() {
        try {
            File f = model.exportSalesReport("pdf");
            showInfo("Export complete", "PDF saved to:\n" + f.getAbsolutePath());
        } catch (Exception e) {
            showError("Export failed", e.getMessage());
        }
    }

    // Export as Excel
    @FXML
    public void onExportExcel() {
        try {
            File f = model.exportSalesReport("xlsx");
            showInfo("Export complete", "Excel saved to:\n" + f.getAbsolutePath());
        } catch (Exception e) {
            showError("Export failed", e.getMessage());
        }
    }

    // Helper: update UI labels from summary
    private void updateLabels(MonitorBranchSales.SalesSummary summary) {
        if (summary == null) {
            totalRevenueLabel.setText("0.00");
            totalOrdersLabel.setText("0");
            bestProductLabel.setText("N/A");
            return;
        }
        totalRevenueLabel.setText(String.format("%.2f", summary.getTotalRevenue()));
        totalOrdersLabel.setText(String.valueOf(summary.getTotalOrders()));
        bestProductLabel.setText(summary.getBestProduct() != null ? summary.getBestProduct() : "N/A");
    }

    // Simple alert helpers
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
