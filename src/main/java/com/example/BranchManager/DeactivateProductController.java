package com.example.BranchManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class DeactivateProductController {

    @FXML
    private Label lblManagerId;
    @FXML
    private TableView<DeactivateProduct.Product> tblProducts;
    @FXML
    private TableColumn<DeactivateProduct.Product, Integer> colProdId;
    @FXML
    private TableColumn<DeactivateProduct.Product, String> colProdName;
    @FXML
    private TableColumn<DeactivateProduct.Product, Double> colProdPrice;
    @FXML
    private TableColumn<DeactivateProduct.Product, String> colProdStatus;
    @FXML
    private Label lblStatus;

    private final DeactivateProduct deactivateProductModel = new DeactivateProduct();
    private final ObservableList<DeactivateProduct.Product> productData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Example manager ID; normally you pass it from login screen
        deactivateProductModel.setManagerID(1001);
        lblManagerId.setText(String.valueOf(deactivateProductModel.getManagerID()));

        setupTable();
        loadProducts();
    }

    private void setupTable() {
        colProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProdStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblProducts.setItems(productData);
    }

    private void loadProducts() {
        productData.clear();
        List<DeactivateProduct.Product> products = deactivateProductModel.showProducts();
        productData.addAll(products);
        lblStatus.setText("Products loaded. (" + products.size() + " active)");
    }

    @FXML
    private void onRefreshProducts() {
        loadProducts();
    }

    @FXML
    private void onDeactivateSelected() {
        DeactivateProduct.Product selected = tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select a product to deactivate.");
            return;
        }

        if (!"Active".equalsIgnoreCase(selected.getStatus())) {
            showWarning("This product is already inactive.");
            return;
        }

        deactivateProductModel.setProductID(selected.getId());

        // event-4: Show confirmation prompt
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deactivation");
        alert.setHeaderText("Deactivate product from sale");
        alert.setContentText("Are you sure you want to deactivate \"" + selected.getName() + "\"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            lblStatus.setText("Deactivation canceled.");
            return;
        }

        // event-5: Manager confirms the action (UID)
        boolean confirmed = deactivateProductModel.confirmDeactivation();
        if (!confirmed) {
            showError("Deactivation could not be confirmed by system.");
            return;
        }

        // event-6: System updates product status to inactive
        boolean success = deactivateProductModel.deactivate(selected.getId());
        if (success) {
            showInfo("Product deactivated successfully.");
            loadProducts(); // refresh list
            lblStatus.setText("Product ID " + selected.getId() + " deactivated.");
        } else {
            showError("Failed to deactivate product.");
        }
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
