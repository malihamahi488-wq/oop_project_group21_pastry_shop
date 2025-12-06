package com.example.BranchManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller for updateProductDetails.fxml
 *
 * CRA mapping:
 * event-1 UIE -> initialize() loads the product list
 * event-2 OP   -> table displays products
 * event-3 UIE  -> manager clicks Edit (onEditSelected)
 * event-4 UID  -> manager edits fields (form)
 * event-5 VL/DP-> onSave validates then model.saveUpdatedProduct()
 * event-6 OP   -> success confirmation shown
 */
public class UpdateProductDetailsController {

    @FXML private TableView<UpdateProductDetails.Product> productTable;
    @FXML private TableColumn<UpdateProductDetails.Product, String> colProductId;
    @FXML private TableColumn<UpdateProductDetails.Product, String> colName;
    @FXML private TableColumn<UpdateProductDetails.Product, String> colCategory;
    @FXML private TableColumn<UpdateProductDetails.Product, String> colPrice;
    @FXML private TableColumn<UpdateProductDetails.Product, String> colStock;

    @FXML private Label lblProductId;
    @FXML private TextField tfName;
    @FXML private TextField tfCategory;
    @FXML private TextField tfPrice;
    @FXML private TextField tfStock;
    @FXML private TextArea taDescription;

    @FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private UpdateProductDetails model;
    private final ObservableList<UpdateProductDetails.Product> products = FXCollections.observableArrayList();
    private UpdateProductDetails.Product currentlyEditing = null;

    @FXML
    public void initialize() {
        model = new UpdateProductDetails(1001); // sample manager id

        // setup table columns
        colProductId.setCellValueFactory(cell -> cell.getValue().productIdProperty());
        colName.setCellValueFactory(cell -> cell.getValue().nameProperty());
        colCategory.setCellValueFactory(cell -> cell.getValue().categoryProperty());
        colPrice.setCellValueFactory(cell -> cell.getValue().priceProperty());
        colStock.setCellValueFactory(cell -> cell.getValue().stockProperty());

        productTable.setItems(products);
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> onTableSelectionChanged(newSel));

        loadProducts();

        setEditingEnabled(false);
    }

    private void loadProducts() {
        products.clear();
        products.addAll(model.loadAllProducts());
    }

    private void onTableSelectionChanged(UpdateProductDetails.Product selected) {
        if (selected == null) {
            clearForm();
            return;
        }
        lblProductId.setText(selected.getProductId());
        tfName.setText(selected.getName());
        tfCategory.setText(selected.getCategory());
        tfPrice.setText(String.valueOf(selected.getPrice()));
        tfStock.setText(String.valueOf(selected.getStockQty()));
        taDescription.setText(selected.getDescription());
        setEditingEnabled(false);
    }

    @FXML
    public void onEditSelected() {
        UpdateProductDetails.Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("No selection", "Please select a product to edit.");
            return;
        }
        currentlyEditing = selected;
        setEditingEnabled(true);
    }

    @FXML
    public void onSave() {
        if (currentlyEditing == null) {
            showInfo("Nothing to save", "Click 'Edit Selected' for a product first.");
            return;
        }

        // VL: validation
        String name = tfName.getText().trim();
        String category = tfCategory.getText().trim();
        String priceText = tfPrice.getText().trim();
        String stockText = tfStock.getText().trim();
        String desc = taDescription.getText().trim();

        String validationError = validateFields(name, category, priceText, stockText);
        if (validationError != null) {
            showError("Validation failed", validationError);
            return;
        }

        double price = Double.parseDouble(priceText);
        int stock = Integer.parseInt(stockText);

        // DP: update model fields
        model.loadProduct(currentlyEditing.getProductId()); // ensure product loaded in model
        model.updateField("name", name);
        model.updateField("category", category);
        model.updateField("price", String.valueOf(price));
        model.updateField("stockQty", String.valueOf(stock));
        model.updateField("description", desc);

        boolean ok = model.saveUpdatedProduct();
        if (ok) {
            // reflect changes in UI list
            currentlyEditing.setName(name);
            currentlyEditing.setCategory(category);
            currentlyEditing.setPrice(price);
            currentlyEditing.setStockQty(stock);
            currentlyEditing.setDescription(desc);
            productTable.refresh();

            showInfo("Saved", "Product updated successfully.");
            setEditingEnabled(false);
            currentlyEditing = null;
        } else {
            showError("Save failed", "Could not save product. Try again.");
        }
    }

    @FXML
    public void onCancel() {
        if (currentlyEditing != null) {
            // revert fields to selected item's values
            onTableSelectionChanged(currentlyEditing);
            setEditingEnabled(false);
            currentlyEditing = null;
        } else {
            clearForm();
        }
    }

    @FXML
    public void onRefresh() {
        loadProducts();
        clearForm();
    }

    private void setEditingEnabled(boolean enabled) {
        tfName.setDisable(!enabled);
        tfCategory.setDisable(!enabled);
        tfPrice.setDisable(!enabled);
        tfStock.setDisable(!enabled);
        taDescription.setDisable(!enabled);
        btnSave.setDisable(!enabled);
        btnCancel.setDisable(!enabled);
        btnEdit.setDisable(enabled); // disable Edit while editing
    }

    private void clearForm() {
        lblProductId.setText("-");
        tfName.clear();
        tfCategory.clear();
        tfPrice.clear();
        tfStock.clear();
        taDescription.clear();
        setEditingEnabled(false);
        currentlyEditing = null;
    }

    private String validateFields(String name, String category, String priceText, String stockText) {
        if (name.isEmpty()) return "Product name cannot be empty.";
        if (category.isEmpty()) return "Category cannot be empty.";
        try {
            double p = Double.parseDouble(priceText);
            if (p < 0) return "Price cannot be negative.";
        } catch (NumberFormatException e) {
            return "Invalid price value.";
        }
        try {
            int s = Integer.parseInt(stockText);
            if (s < 0) return "Stock cannot be negative.";
        } catch (NumberFormatException e) {
            return "Invalid stock quantity.";
        }
        return null;
    }

    // Alerts
    private void showInfo(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            a.setTitle(title); a.setHeaderText(null); a.showAndWait();
        });
    }

    private void showError(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            a.setTitle(title); a.setHeaderText(null); a.showAndWait();
        });
    }
}
