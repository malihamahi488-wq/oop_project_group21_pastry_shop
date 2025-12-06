package com.example.QualityControlOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ReviewCustomerFeedbackController {

    @FXML
    private Label lblOfficerId;
    @FXML
    private TableView<ReviewCustomerFeedback.Feedback> tblFeedback;
    @FXML
    private TableColumn<ReviewCustomerFeedback.Feedback, Integer> colFeedbackId;
    @FXML
    private TableColumn<ReviewCustomerFeedback.Feedback, Integer> colRating;
    @FXML
    private TableColumn<ReviewCustomerFeedback.Feedback, String> colCommentShort;
    @FXML
    private TableColumn<ReviewCustomerFeedback.Feedback, String> colTimestamp;
    @FXML
    private TableColumn<ReviewCustomerFeedback.Feedback, String> colStatus;

    @FXML
    private Label lblSelectedFeedbackId;
    @FXML
    private Label lblRating;
    @FXML
    private Label lblTimestamp;
    @FXML
    private TextArea txtFullComment;
    @FXML
    private TextArea txtInternalNotes;
    @FXML
    private Label lblStatus;

    private final ReviewCustomerFeedback feedbackModel = new ReviewCustomerFeedback();
    private final ObservableList<ReviewCustomerFeedback.Feedback> feedbackData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Simulated officer ID; normally injected from login context
        feedbackModel.setOfficerID(3001);
        lblOfficerId.setText(String.valueOf(feedbackModel.getOfficerID()));

        setupTable();
        loadFeedback();

        // When table selection changes, load details
        tblFeedback.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                onFeedbackSelected(newV);
            }
        });
    }

    private void setupTable() {
        colFeedbackId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colCommentShort.setCellValueFactory(new PropertyValueFactory<>("shortComment"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestampString"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblFeedback.setItems(feedbackData);
    }

    private void loadFeedback() {
        feedbackData.clear();
        List<ReviewCustomerFeedback.Feedback> list = feedbackModel.viewAllFeedback();
        feedbackData.addAll(list);
        lblStatus.setText("Loaded " + list.size() + " feedback records.");
    }

    @FXML
    private void onReloadFeedback() {
        loadFeedback();
        clearDetails();
    }

    private void onFeedbackSelected(ReviewCustomerFeedback.Feedback feedback) {
        // event-4: Officer selects specific feedback
        int id = feedback.getId();
        feedbackModel.setFeedbackID(id);

        // event-5: System shows full details
        ReviewCustomerFeedback.Feedback full = feedbackModel.viewFeedbackDetails(id);
        if (full == null) {
            clearDetails();
            lblStatus.setText("Feedback not found.");
            return;
        }

        lblSelectedFeedbackId.setText(String.valueOf(full.getId()));
        lblRating.setText(String.valueOf(full.getRating()));
        lblTimestamp.setText(full.getTimestampString());
        txtFullComment.setText(full.getComment());
        txtInternalNotes.setText(full.getInternalNotes() == null ? "" : full.getInternalNotes());

        lblStatus.setText("Viewing feedback ID " + id);
    }

    @FXML
    private void onMarkReviewed() {
        ReviewCustomerFeedback.Feedback selected = tblFeedback.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Please select a feedback item first.");
            return;
        }

        String notes = txtInternalNotes.getText();

        boolean success = feedbackModel.markAsReviewed(selected.getId(), notes);
        if (success) {
            showInfo("Feedback marked as reviewed.");
            loadFeedback();
            // reselect the same ID for convenience
            feedbackData.stream()
                    .filter(f -> f.getId() == selected.getId())
                    .findFirst()
                    .ifPresent(f -> {
                        tblFeedback.getSelectionModel().select(f);
                        onFeedbackSelected(f);
                    });
            lblStatus.setText("Feedback ID " + selected.getId() + " marked as reviewed.");
        } else {
            showError("Failed to mark feedback as reviewed.");
        }
    }

    @FXML
    private void onClearNotes() {
        txtInternalNotes.clear();
        lblStatus.setText("Internal notes cleared.");
    }

    private void clearDetails() {
        lblSelectedFeedbackId.setText("-");
        lblRating.setText("-");
        lblTimestamp.setText("-");
        txtFullComment.clear();
        txtInternalNotes.clear();
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
