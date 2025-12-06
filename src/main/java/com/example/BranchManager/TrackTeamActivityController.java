package com.example.BranchManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Controller for trackTeamActivity.fxml
 *
 * Maps CRA events:
 * - event-1 (UIE): initialize / opening section -> initialize()
 * - event-2 (DP): model.viewTeamActivity()
 * - event-3 (OP): display table content
 * - event-4 (UID): selection in table -> onTableSelect
 * - event-5 (OP): show detailed logs in details pane
 * - event-6 (UID): download report -> onDownloadReport / onExport*
 * - event-7 (OP): confirm / file created
 */
public class TrackTeamActivityController {

    @FXML private TextField searchField;
    @FXML private TableView<TrackTeamActivity.ActivitySummary> activityTable;
    @FXML private TableColumn<TrackTeamActivity.ActivitySummary, String> colEmployeeId;
    @FXML private TableColumn<TrackTeamActivity.ActivitySummary, String> colName;
    @FXML private TableColumn<TrackTeamActivity.ActivitySummary, String> colLastActivity;
    @FXML private TableColumn<TrackTeamActivity.ActivitySummary, String> colRating;

    // Details pane
    @FXML private Label detailEmployeeId;
    @FXML private Label detailName;
    @FXML private Label detailShifts;
    @FXML private Label detailOrders;
    @FXML private Label detailRating;
    @FXML private TextArea detailRecent;

    @FXML private Button exportPdfBtn;
    @FXML private Button exportExcelBtn;

    // model
    private TrackTeamActivity model;
    private ObservableList<TrackTeamActivity.ActivitySummary> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        model = new TrackTeamActivity(101); // sample managerId

        // setup columns (using property accessors from ActivitySummary)
        colEmployeeId.setCellValueFactory(cell -> cell.getValue().employeeIdProperty());
        colName.setCellValueFactory(cell -> cell.getValue().nameProperty());
        colLastActivity.setCellValueFactory(cell -> cell.getValue().lastActivityProperty());
        colRating.setCellValueFactory(cell -> cell.getValue().ratingProperty());

        // double-click or selection listener to show details
        activityTable.setItems(tableData);
        activityTable.setOnMouseClicked(this::onTableClicked);

        // load data (event-2 + event-3)
        loadTeamActivity();
    }

    // Load / refresh table data
    private void loadTeamActivity() {
        tableData.clear();
        List<TrackTeamActivity.ActivitySummary> list = model.viewTeamActivity();
        tableData.addAll(list);
    }

    // Search button action
    @FXML
    public void onSearch() {
        String q = searchField.getText();
        tableData.clear();
        if (q == null || q.isBlank()) {
            tableData.addAll(model.viewTeamActivity());
        } else {
            tableData.addAll(model.searchByNameOrId(q));
        }
    }

    // Table click -> selection -> show details
    private void onTableClicked(MouseEvent event) {
        TrackTeamActivity.ActivitySummary selected = activityTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showDetailsFor(selected.getEmployeeId());
        }
    }

    // show details - maps to viewEmployeeDetails(empID) and displays logs (event-4 + event-5)
    private void showDetailsFor(String empId) {
        TrackTeamActivity.ActivityDetails details = model.viewEmployeeDetails(empId);
        if (details == null) {
            clearDetails();
            return;
        }
        detailEmployeeId.setText(details.getEmployeeId());
        detailName.setText(details.getName());
        detailShifts.setText(details.getShiftTimes());
        detailOrders.setText(String.valueOf(details.getOrdersHandled()));
        detailRating.setText(String.format("%.1f", details.getPerformanceRating()));
        detailRecent.setText(String.join("\n", details.getRecentActivities()));
    }

    @FXML
    public void onRefresh() {
        searchField.clear();
        loadTeamActivity();
        clearDetails();
    }

    // Download report for currently selected employee or all
    @FXML
    public void onDownloadReport() {
        TrackTeamActivity.ActivitySummary selected = activityTable.getSelectionModel().getSelectedItem();
        try {
            File f;
            if (selected != null) {
                f = model.exportTeamActivityReportForEmployee(selected.getEmployeeId(), "pdf");
                showInfo("Report exported", "Report for " + selected.getName() + " saved at:\n" + f.getAbsolutePath());
            } else {
                f = model.exportTeamActivityReport("pdf");
                showInfo("Report exported", "Team report saved at:\n" + f.getAbsolutePath());
            }
        } catch (IOException e) {
            showError("Export failed", e.getMessage());
        }
    }

    @FXML
    public void onExportPdf() {
        try {
            File f = model.exportTeamActivityReport("pdf");
            showInfo("Export complete", "PDF saved to:\n" + f.getAbsolutePath());
        } catch (IOException e) {
            showError("Export failed", e.getMessage());
        }
    }

    @FXML
    public void onExportExcel() {
        try {
            File f = model.exportTeamActivityReport("xlsx");
            showInfo("Export complete", "Excel saved to:\n" + f.getAbsolutePath());
        } catch (IOException e) {
            showError("Export failed", e.getMessage());
        }
    }

    private void clearDetails() {
        detailEmployeeId.setText("-");
        detailName.setText("-");
        detailShifts.setText("-");
        detailOrders.setText("-");
        detailRating.setText("-");
        detailRecent.clear();
    }

    // Alerts
    private void showError(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
            a.setTitle(title); a.setHeaderText(null); a.showAndWait();
        });
    }

    private void showInfo(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            a.setTitle(title); a.setHeaderText(null); a.showAndWait();
        });
    }
}
