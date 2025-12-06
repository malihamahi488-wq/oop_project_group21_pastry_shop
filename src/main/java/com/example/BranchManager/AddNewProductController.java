package com.example.BranchManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for addNewProduct.fxml
 *
 * CRA mapping:
 * - event-1 (UIE): manager clicks Add Product -> this screen opens (initialize() prepares form)
 * - event-2 (OP): system shows registration form -> this FXML
 * - event-3 (UID): manager enters product details -> user types into TextFields/ComboBox
 * - event-4 (VL): validateProductData() called by onValidate/onSave
 * - event-5 (DP, OP): saveProduct() persists model and UI shows confirmation
 */
public class AddNewProductController {

    @FXML private TextField tfProductId;
    @FXML private TextField tfName;
    @FXML private ComboBox<String> cbCategory;
    @FXML private TextField tfPrice;
    @FXML private TextField tfStock;
    @FXML private TextArea taDescription;

    @FXML private Button btnValidate;
    @FXML private Button btnSave;
    @FXML private Label lblFeedback;

    private AddNewProduct model;

    @FXML
    public void initialize() {
        // create model (managerID could be injected)
        model = new AddNewProduct(101);

        // populate category combo (replace with DB lookup if available)
        cbCategory.getItems().addAll("Cakes", "Pastries", "Breads", "Drinks", "Snacks");
        cbCategory.getSelectionModel().selectFirst();

        lblFeedback.setText("");
        btnSave.setDisable(false);
    }

    /**
     * Validate input fields (UI action)
     */
    @FXML
    public void onValidate() {
        Map<String, String> data = collectForm();
        boolean ok = model.validateProductData(data);
        if (ok) {
            lblFeedback.setStyle("-fx-text-fill: green;");
            lblFeedback.setText("Validation succeeded. Ready to save.");
        } else {
            lblFeedback.setStyle("-fx-text-fill: red;");
            lblFeedback.setText("Validation failed: " + model.getLastValidationMessage());
        }
    }

    /**
     * Save product (UI action)
     */
    @FXML
    public void onSave() {
        Map<String, String> data = collectForm();

        // 1) Validate first
        if (!model.validateProductData(data)) {
            lblFeedback.setStyle("-fx-text-fill: red;");
            lblFeedback.setText("Validation failed: " + model.getLastValidationMessage());
            return;
        }

        // 2) Provide data to model and save
        model.setNewProductData(data);
        boolean saved;
        try {
            saved = model.saveProduct();
        } catch (IOException e) {
            saved = false;
            lblFeedback.setStyle("-fx-text-fill: red;");
            lblFeedback.setText("Save failed: " + e.getMessage());
        }

        if (saved) {
            lblFeedback.setStyle("-fx-text-fill: green;");
            lblFeedback.setText("Product saved successfully.");
            clearForm();
        } else {
            lblFeedback.setStyle("-fx-text-fill: red;");
            lblFeedback.setText("Product save failed.");
        }
    }

    /**
     * Clear form fields
     */
    @FXML
    public void onClear() {
        clearForm();
    }

    /**
     * Collects form fields into a simple map
     */
    private Map<String, String> collectForm() {
        Map<String, String> data = new HashMap<>();
        String id = tfProductId.getText().trim();
        if (id.isEmpty()) id = ""; // allow blank -> model can auto-generate
        data.put("productId", id);
        data.put("name", tfName.getText().trim());
        data.put("category", cbCategory.getValue() != null ? cbCategory.getValue() : "");
        data.put("price", tfPrice.getText().trim());
        data.put("stockQty", tfStock.getText().trim());
        data.put("description", taDescription.getText().trim());
        return data;
    }

    private void clearForm() {
        tfProductId.clear();
        tfName.clear();
        cbCategory.getSelectionModel().selectFirst();
        tfPrice.clear();
        tfStock.clear();
        taDescription.clear();
        lblFeedback.setText("");
    }

    // helper alert (if you prefer popups)
    private void showInfo(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            a.setTitle(title); a.setHeaderText(null); a.showAndWait();
        });
    }
}
