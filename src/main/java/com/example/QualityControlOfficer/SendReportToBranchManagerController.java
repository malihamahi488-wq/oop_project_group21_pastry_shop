package com.example.QualityControlOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SendReportToBranchManagerController {

    @FXML
    private Label lblOfficerId;

    @FXML
    private TableView<SendReportToBranchManager.Report> tblReports;
    @FXML
    private TableColumn<SendReportToBranchManager.Report, Integer> colReportId;
    @FXML
    private TableColumn<SendReportToBranchManager.Report, String> colTitle;
    @FXML
    private TableColumn<SendReportToBranchManager.Report, String> colCreatedDate;
    @FXML
    private TableColumn<SendReportToBranchManager.Report, String> colStatus;

    @FXML
    private Label lblSelectedReportId;
    @FXML
    private Label lblSelectedTitle;
    @FXML
    private Label lblSelectedCreated;
    @FXML
    private Label lblSelectedStatus;

    @FXML
    private TextField txtManagerId;
    @FXML
    private TextArea txtSummary;

    @FXML
    private Label lblStatus;

    private final SendReportToBranchManager model = new SendReportToBranchManager();
    private final ObservableList<SendReportToBranchManager.Report> reportData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Simulate current officer
        model.setOfficerID(3001);
        lblOfficerId.setText(String.valueOf(model.getOfficerID()));

        setupTable();
        loadReports();

        tblReports.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                onReportSelected(newV);
            }
        });
    }

    private void setupTable() {
        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDateString"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblReports.setItems(reportData);
    }

    private void loadReports() {
        reportData.clear();
        List<SendReportToBranchManager.Report> list = model.viewAvailableReports();
        reportData.addAll(list);
        lblStatus.setText("Loaded " + list.size() + " reports.");
    }

    @FXML
    private void onReloadReports() {
        loadReports();
        clearSelection();
    }

    private void onReportSelected(SendReportToBranchManager.Report r) {
        // UML: + selectReport(reportID): Report
        SendReportToBranchManager.Report selected = model.selectReport(r.getReportID());
        if (selected == null) {
            clearSelection();
            lblStatus.setText("Report not found.");
            return;
        }

        lblSelectedReportId.setText(String.valueOf(selected.getReportID()));
        lblSelectedTitle.setText(selected.getTitle());
        lblSelectedCreated.setText(selected.getCreatedDateString());
        lblSelectedStatus.setText(selected.getStatus());
        txtSummary.setText(selected.getSummary());

        // Pre-fill managerID if you want a fixed branch manager (example)
        if (txtManagerId.getText().isBlank()) {
            txtManagerId.setText("2001");
        }

        lblStatus.setText("Selected report ID " + selected.getReportID());
    }

    @FXML
    private void onSendReport() {
        String managerText = txtManagerId.getText();
        if (managerText == null || managerText.trim().isEmpty()) {
            showError("Please enter a Branch Manager ID.");
            return;
        }

        int managerId;
        try {
            managerId = Integer.parseInt(managerText.trim());
        } catch (NumberFormatException e) {
            showError("Manager ID must be a valid integer.");
            return;
        }

        SendReportToBranchManager.Report selected =
                tblReports.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a report to send.");
            return;
        }

        model.setReportID(selected.getReportID());
        model.setManagerID(managerId);

        if (!model.validateSending()) {
            showError("Validation failed. Check that the report is not already sent and Manager ID is valid.");
            return;
        }

        boolean success = model.sendReport(managerId);
        if (success) {
            showInfo("Report sent to Branch Manager (ID: " + managerId + ").");
            loadReports();
            clearSelection();
        } else {
            showError("Failed to send report. It may already be sent or not found.");
        }
    }

    @FXML
    private void onClearSelection() {
        clearSelection();
        lblStatus.setText("Selection cleared.");
    }

    private void clearSelection() {
        tblReports.getSelectionModel().clearSelection();
        lblSelectedReportId.setText("-");
        lblSelectedTitle.setText("-");
        lblSelectedCreated.setText("-");
        lblSelectedStatus.setText("-");
        txtSummary.clear();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
