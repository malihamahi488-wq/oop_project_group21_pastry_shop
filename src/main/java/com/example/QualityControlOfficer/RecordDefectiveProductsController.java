package com.example.QualityControlOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RecordDefectiveProductsController {

    @FXML
    private Label lblOfficerId;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextArea txtDefectReason;

    @FXML
    private Label lblStatus;

    // Optional: small stock table to visualize stock reduction
    @FXML
    private TableView<RecordDefectiveProducts.ProductStock> tblStock;
    @FXML
    private TableColumn<RecordDefectiveProducts.ProductStock, Integer> colStockProdId;
    @FXML
    private TableColumn<RecordDefectiveProducts.ProductStock, String> colStockProdName;
    @FXML
    private TableColumn<RecordDefectiveProducts.ProductStock, Integer> colStockQty;

    private final RecordDefectiveProducts model = new RecordDefectiveProducts();
    private final ObservableList<RecordDefectiveProducts.ProductStock> stockData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Simulated officer from login
        model.setOfficerID(3001);
        lblOfficerId.setText(String.valueOf(model.getOfficerID()));

        setupStockTable();
        loadStock();

        lblStatus.setText("Ready to record defective products.");
    }

    private void setupStockTable() {
        if (tblStock == null) return; // if you remove the stock table from FXML, avoid NPE

        colStockProdId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colStockProdName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colStockQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tblStock.setItems(stockData);
    }

    private void loadStock() {
        if (tblStock == null) return;

        stockData.clear();
        List<RecordDefectiveProducts.ProductStock> list = model.getCurrentStock();
        stockData.addAll(list);
    }

    @FXML
    private void onNewEntry() {
        clearForm();
        lblStatus.setText("New defective record entry started.");
    }

    @FXML
    private void onSaveDefectiveRecord() {
        String productIdText = txtProductId.getText();
        String qtyText = txtQuantity.getText();
        String reason = txtDefectReason.getText();

        int productId;
        int quantity;
        try {
            productId = Integer.parseInt(productIdText.trim());
        } catch (NumberFormatException e) {
            showError("Product ID must be a valid integer.");
            return;
        }

        try {
            quantity = Integer.parseInt(qtyText.trim());
        } catch (NumberFormatException e) {
            showError("Quantity must be a valid integer.");
            return;
        }

        // Populate model
        model.setProductID(productId);
        model.setQuantity(quantity);
        model.setDefectReason(reason);

        // Validate fields
        if (!model.validateDefectEntry()) {
            showError("Validation failed. Check product ID, quantity and reason. " +
                    "Quantity must be positive and not exceed available stock.");
            return;
        }

        // Save record and update stock
        boolean success = model.addDefectiveRecord(productId, reason, quantity);
        if (success) {
            showInfo("Defective record saved and stock updated.");
            clearForm();
            loadStock();
            lblStatus.setText("Defective record saved successfully.");
        } else {
            showError("Failed to save defective record. Product may not exist or stock too low.");
        }
    }

    @FXML
    private void onClearForm() {
        clearForm();
        lblStatus.setText("Form cleared.");
    }

    private void clearForm() {
        txtProductId.clear();
        txtQuantity.clear();
        txtDefectReason.clear();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
