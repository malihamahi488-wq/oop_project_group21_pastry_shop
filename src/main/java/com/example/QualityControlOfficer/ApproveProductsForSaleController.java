package com.example.QualityControlOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ApproveProductsForSaleController {

    @FXML
    private Label lblOfficerId;

    @FXML
    private TableView<ApproveProductsForSale.Product> tblProducts;
    @FXML
    private TableColumn<ApproveProductsForSale.Product, Integer> colProdId;
    @FXML
    private TableColumn<ApproveProductsForSale.Product, String> colProdName;
    @FXML
    private TableColumn<ApproveProductsForSale.Product, String> colBatch;
    @FXML
    private TableColumn<ApproveProductsForSale.Product, String> colQualityStatus;

    @FXML
    private Label lblSelectedProductId;
    @FXML
    private Label lblSelectedProductName;
    @FXML
    private Label lblSelectedBatch;
    @FXML
    private Label lblSelectedQualityStatus;

    @FXML
    private TextArea txtDecisionNotes;

    @FXML
    private Label lblStatus;

    private final ApproveProductsForSale approveModel = new ApproveProductsForSale();
    private final ObservableList<ApproveProductsForSale.Product> productData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Example officer ID; in real app, pass from login context
        approveModel.setOfficerID(3001);
        lblOfficerId.setText(String.valueOf(approveModel.getOfficerID()));

        setupTable();
        loadPendingProducts();

        tblProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                onProductSelected(newV);
            }
        });
    }

    private void setupTable() {
        colProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBatch.setCellValueFactory(new PropertyValueFactory<>("batch"));
        colQualityStatus.setCellValueFactory(new PropertyValueFactory<>("qualityStatus"));

        tblProducts.setItems(productData);
    }

    private void loadPendingProducts() {
        productData.clear();
        List<ApproveProductsForSale.Product> list = approveModel.viewPendingProducts();
        productData.addAll(list);
        lblStatus.setText("Loaded " + list.size() + " products pending approval.");
    }

    @FXML
    private void onReloadPending() {
        loadPendingProducts();
        clearSelection();
        txtDecisionNotes.clear();
    }

    private void onProductSelected(ApproveProductsForSale.Product p) {
        approveModel.setProductID(p.getId());

        lblSelectedProductId.setText(String.valueOf(p.getId()));
        lblSelectedProductName.setText(p.getName());
        lblSelectedBatch.setText(p.getBatch());
        lblSelectedQualityStatus.setText(p.getQualityStatus());

        lblStatus.setText("Selected product ID " + p.getId());
    }

    @FXML
    private void onApprove() {
        ApproveProductsForSale.Product selected =
                tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select a product to approve.");
            return;
        }

        approveModel.setProductID(selected.getId());
        approveModel.setDecision("Approved");
        approveModel.setDecisionNotes(txtDecisionNotes.getText());

        boolean success = approveModel.approve(selected.getId());
        if (success) {
            showInfo("Product approved for sale.");
            loadPendingProducts();
            clearSelection();
            txtDecisionNotes.clear();
        } else {
            showError("Failed to approve product.");
        }
    }

    @FXML
    private void onReject() {
        ApproveProductsForSale.Product selected =
                tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select a product to reject.");
            return;
        }

        approveModel.setProductID(selected.getId());
        approveModel.setDecision("Rejected");
        approveModel.setDecisionNotes(txtDecisionNotes.getText());

        boolean success = approveModel.reject(selected.getId());
        if (success) {
            showInfo("Product rejected.");
            loadPendingProducts();
            clearSelection();
            txtDecisionNotes.clear();
        } else {
            showError("Failed to reject product.");
        }
    }

    @FXML
    private void onClearSelection() {
        clearSelection();
        txtDecisionNotes.clear();
        lblStatus.setText("Selection cleared.");
    }

    private void clearSelection() {
        tblProducts.getSelectionModel().clearSelection();
        lblSelectedProductId.setText("-");
        lblSelectedProductName.setText("-");
        lblSelectedBatch.setText("-");
        lblSelectedQualityStatus.setText("-");
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    private void showWarning(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
