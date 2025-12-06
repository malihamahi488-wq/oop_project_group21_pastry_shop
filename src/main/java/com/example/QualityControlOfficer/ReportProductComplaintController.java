package com.example.QualityControlOfficer;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ReportProductComplaintController {

    @FXML
    private Label lblOfficerId;
    @FXML
    private TextField txtProductId;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Label lblStatus;

    private final ReportProductComplaint complaintModel = new ReportProductComplaint();

    @FXML
    private void initialize() {
        // In a real system, officerID would come from the login context
        complaintModel.setOfficerID(3001);
        lblOfficerId.setText(String.valueOf(complaintModel.getOfficerID()));
        lblStatus.setText("Ready to submit a complaint.");
    }

    @FXML
    private void onNewComplaint() {
        clearForm();
        lblStatus.setText("New complaint started.");
    }

    @FXML
    private void onSubmitComplaint() {
        String productIdText = txtProductId.getText();
        String description = txtDescription.getText();

        int productId;
        try {
            productId = Integer.parseInt(productIdText.trim());
        } catch (NumberFormatException e) {
            showError("Product ID must be a valid number.");
            return;
        }

        // Set values into model
        complaintModel.setProductID(productId);
        complaintModel.setDescription(description);

        // Validate input (event-4)
        if (!complaintModel.validateComplaintFields()) {
            showError("Validation failed. Please check product ID and description.");
            return;
        }

        // Submit complaint (event-5: save & send notification)
        boolean success = complaintModel.submitComplaint(productId, description);
        if (success) {
            showInfo("Complaint submitted successfully.");
            lblStatus.setText("Complaint submitted. ID: " + complaintModel.getComplaintID());
            clearForm();
        } else {
            showError("Failed to submit complaint. Please try again.");
        }
    }

    @FXML
    private void onClearForm() {
        clearForm();
        lblStatus.setText("Form cleared.");
    }

    private void clearForm() {
        txtProductId.clear();
        txtDescription.clear();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
