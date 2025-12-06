package com.example.QualityControlOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckProductQualityController {

    @FXML
    private Label lblOfficerId;

    @FXML
    private TableView<CheckProductQuality.ProductUnderTest> tblProducts;
    @FXML
    private TableColumn<CheckProductQuality.ProductUnderTest, Integer> colProdId;
    @FXML
    private TableColumn<CheckProductQuality.ProductUnderTest, String> colProdName;
    @FXML
    private TableColumn<CheckProductQuality.ProductUnderTest, String> colBatch;
    @FXML
    private TableColumn<CheckProductQuality.ProductUnderTest, String> colStatus;

    @FXML
    private Label lblSelectedProductId;
    @FXML
    private Label lblSelectedProductName;
    @FXML
    private Label lblSelectedBatch;

    @FXML
    private TextField txtTemperature;
    @FXML
    private ComboBox<String> cbAppearance;
    @FXML
    private ComboBox<String> cbHygiene;
    @FXML
    private ComboBox<String> cbFinalStatus;
    @FXML
    private TextArea txtNotes;

    @FXML
    private Label lblStatus;

    private final CheckProductQuality qualityModel = new CheckProductQuality();
    private final ObservableList<CheckProductQuality.ProductUnderTest> productData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Simulate logged-in officer
        qualityModel.setOfficerID(3001);
        lblOfficerId.setText(String.valueOf(qualityModel.getOfficerID()));

        setupTable();
        setupComboBoxes();
        loadProducts();

        // When a product is selected, load its info into labels
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
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblProducts.setItems(productData);
    }

    private void setupComboBoxes() {
        cbAppearance.setItems(FXCollections.observableArrayList(
                "Good", "Damaged", "Discolored", "Other"
        ));
        cbHygiene.setItems(FXCollections.observableArrayList(
                "Clean", "Minor Concern", "Major Concern"
        ));
        cbFinalStatus.setItems(FXCollections.observableArrayList(
                "Pass", "Fail"
        ));
    }

    private void loadProducts() {
        productData.clear();
        List<CheckProductQuality.ProductUnderTest> list =
                qualityModel.getProductsAwaitingInspection();
        productData.addAll(list);
        lblStatus.setText("Loaded " + list.size() + " products awaiting inspection.");
    }

    @FXML
    private void onReloadProducts() {
        loadProducts();
        clearForm();
        clearSelectionLabels();
    }

    private void onProductSelected(CheckProductQuality.ProductUnderTest product) {
        qualityModel.setProductID(product.getId());

        lblSelectedProductId.setText(String.valueOf(product.getId()));
        lblSelectedProductName.setText(product.getName());
        lblSelectedBatch.setText(product.getBatch());

        lblStatus.setText("Selected product ID " + product.getId());
    }

    @FXML
    private void onSaveQualityTest() {
        CheckProductQuality.ProductUnderTest selected =
                tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select a product to test.");
            return;
        }

        String tempText = txtTemperature.getText();
        String appearance = cbAppearance.getValue();
        String hygiene = cbHygiene.getValue();
        String finalStatus = cbFinalStatus.getValue();
        String notes = txtNotes.getText();

        Map<String, Object> results = new HashMap<>();
        results.put("temperature", tempText);
        results.put("appearance", appearance);
        results.put("hygiene", hygiene);
        results.put("finalStatus", finalStatus);
        results.put("notes", notes);

        qualityModel.setProductID(selected.getId());
        qualityModel.setTestResults(results);

        // event-4: validate quality data
        if (!qualityModel.validateTestResults()) {
            showError("Validation failed. Please check the quality test fields.");
            return;
        }

        // event-5: store quality report & update status
        boolean success = qualityModel.recordQualityTest(selected.getId(), results);
        if (success) {
            showInfo("Quality test saved and product status updated.");
            loadProducts();
            clearForm();
            clearSelectionLabels();
        } else {
            showError("Failed to record quality test.");
        }
    }

    @FXML
    private void onClearForm() {
        clearForm();
        lblStatus.setText("Form cleared.");
    }

    private void clearForm() {
        txtTemperature.clear();
        cbAppearance.setValue(null);
        cbHygiene.setValue(null);
        cbFinalStatus.setValue(null);
        txtNotes.clear();
    }

    private void clearSelectionLabels() {
        lblSelectedProductId.setText("-");
        lblSelectedProductName.setText("-");
        lblSelectedBatch.setText("-");
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
