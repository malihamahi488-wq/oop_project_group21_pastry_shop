package com.example.BranchManager;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for manageEmployeeAccounts.fxml
 *
 * CRA mapping:
 * event-1 UIE -> initialize() prepares UI
 * event-2 OP  -> table displays list from model.viewEmployees()
 * event-3 UID -> manager selects Add/Edit (onAddEmployee/onEditSelected)
 * event-4 VL  -> onSave() calls model.validateEmployee()
 * event-5 DP/OP -> model.addOrEditEmployee() and show confirmation
 */
public class ManageEmployeeAccountsController {

    // Table
    @FXML private TableView<ManageEmployeeAccounts.Employee> employeeTable;
    @FXML private TableColumn<ManageEmployeeAccounts.Employee, String> colEmpId;
    @FXML private TableColumn<ManageEmployeeAccounts.Employee, String> colName;
    @FXML private TableColumn<ManageEmployeeAccounts.Employee, String> colRole;
    @FXML private TableColumn<ManageEmployeeAccounts.Employee, String> colContact;
    @FXML private TableColumn<ManageEmployeeAccounts.Employee, String> colStatus;

    // Search and buttons
    @FXML private TextField searchField;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;

    // Form fields
    @FXML private Label lblEmpId;
    @FXML private TextField tfName;
    @FXML private ComboBox<String> cbRole;
    @FXML private TextField tfContact;
    @FXML private TextField tfEmail;
    @FXML private ComboBox<String> cbStatus;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Label lblFeedback;

    private ManageEmployeeAccounts model;
    private final ObservableList<ManageEmployeeAccounts.Employee> tableData = FXCollections.observableArrayList();
    private ManageEmployeeAccounts.Employee editingEmployee = null;

    @FXML
    public void initialize() {
        model = new ManageEmployeeAccounts(101); // sample managerID

        // prepare table columns
        colEmpId.setCellValueFactory(c -> c.getValue().employeeIdProperty());
        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colRole.setCellValueFactory(c -> c.getValue().roleProperty());
        colContact.setCellValueFactory(c -> c.getValue().contactProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());

        employeeTable.setItems(tableData);
        employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> onTableSelectionChanged(newVal));

        // form combos
        cbRole.getItems().addAll("Cashier", "Baker", "Supervisor", "Cleaner", "ManagerAssistant");
        cbStatus.getItems().addAll("Active", "Inactive", "On Leave");
        cbRole.getSelectionModel().selectFirst();
        cbStatus.getSelectionModel().selectFirst();

        loadEmployees();
        setFormEnabled(false);
        lblFeedback.setText("");
    }

    private void loadEmployees() {
        tableData.clear();
        List<ManageEmployeeAccounts.Employee> list = model.viewEmployees();
        tableData.addAll(list);
    }

    private void onTableSelectionChanged(ManageEmployeeAccounts.Employee selected) {
        if (selected == null) {
            clearForm();
            return;
        }
        editingEmployee = selected;
        lblEmpId.setText(selected.getEmployeeId());
        tfName.setText(selected.getName());
        cbRole.getSelectionModel().select(selected.getRole());
        tfContact.setText(selected.getContactNumber());
        tfEmail.setText(selected.getEmail());
        cbStatus.getSelectionModel().select(selected.getStatus());
        setFormEnabled(false);
    }

    @FXML
    public void onAddEmployee() {
        editingEmployee = null;
        lblEmpId.setText("(auto)");
        tfName.clear();
        cbRole.getSelectionModel().selectFirst();
        tfContact.clear();
        tfEmail.clear();
        cbStatus.getSelectionModel().select("Active");
        setFormEnabled(true);
    }

    @FXML
    public void onEditSelected() {
        ManageEmployeeAccounts.Employee sel = employeeTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showInfo("Select employee", "Please select an employee to edit.");
            return;
        }
        editingEmployee = sel;
        setFormEnabled(true);
    }

    @FXML
    public void onSave() {
        // collect data into map
        String id = lblEmpId.getText();
        String name = tfName.getText().trim();
        String role = cbRole.getValue();
        String contact = tfContact.getText().trim();
        String email = tfEmail.getText().trim();
        String status = cbStatus.getValue();

        Map<String,String> data = Map.of(
                "employeeId", id.equals("(auto)") ? "" : id,
                "name", name,
                "role", role,
                "contactNumber", contact,
                "email", email,
                "status", status
        );

        // validation
        boolean valid = model.validateEmployee(data);
        if (!valid) {
            lblFeedback.setStyle("-fx-text-fill:red;");
            lblFeedback.setText(model.getLastValidationMessage());
            return;
        }

        boolean ok = model.addOrEditEmployee(data);
        if (ok) {
            lblFeedback.setStyle("-fx-text-fill:green;");
            lblFeedback.setText("Employee saved.");
            loadEmployees();
            setFormEnabled(false);
            editingEmployee = null;
        } else {
            lblFeedback.setStyle("-fx-text-fill:red;");
            lblFeedback.setText("Failed to save employee.");
        }
    }

    @FXML
    public void onCancel() {
        if (editingEmployee != null) {
            onTableSelectionChanged(editingEmployee);
        } else {
            clearForm();
        }
        setFormEnabled(false);
        editingEmployee = null;
    }

    @FXML
    public void onSearch() {
        String q = searchField.getText();
        tableData.clear();
        if (q == null || q.isBlank()) {
            tableData.addAll(model.viewEmployees());
        } else {
            tableData.addAll(model.search(q));
        }
    }

    @FXML
    public void onShowAll() {
        searchField.clear();
        loadEmployees();
    }

    @FXML
    public void onRefresh() {
        loadEmployees();
        clearForm();
    }

    private void clearForm() {
        lblEmpId.setText("(auto)");
        tfName.clear();
        tfContact.clear();
        tfEmail.clear();
        cbRole.getSelectionModel().selectFirst();
        cbStatus.getSelectionModel().selectFirst();
        lblFeedback.setText("");
        setFormEnabled(false);
    }

    private void setFormEnabled(boolean enabled) {
        tfName.setDisable(!enabled);
        cbRole.setDisable(!enabled);
        tfContact.setDisable(!enabled);
        tfEmail.setDisable(!enabled);
        cbStatus.setDisable(!enabled);
        btnSave.setDisable(!enabled);
        btnCancel.setDisable(!enabled);
        btnAdd.setDisable(enabled);
        btnEdit.setDisable(enabled);
    }

    private void showInfo(String title, String msg) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
            a.setTitle(title); a.setHeaderText(null); a.showAndWait();
        });
    }
}
