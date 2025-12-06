package com.example.mrbaker_group21_sec3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ceoController {
    @FXML
    private TableView<Employee> employeeManagementTableViewfxid;
    @FXML
    private TableColumn<Employee, String> employeenameCol;
    @FXML
    private TableColumn<Employee, String> postCol;
    @FXML
    private TableColumn<Employee, String> contactCol;
    @FXML
    private TableColumn<Employee, String> addressCol;

    @FXML
    private TextField employeeNameTextFieldfxid;
    @FXML
    private TextField employeePostTextFieldfxid;
    @FXML
    private TextField employeeContactTextFieldfxid;
    @FXML
    private TextField employeeAddressTextFieldfxid;

    @FXML
    private ComboBox<String> downloadMathodComboBox;
    @FXML
    private TableColumn expenceCol;
    @FXML
    private Label outputlableFxid;
    @FXML
    private TableColumn supplierCol;
    @FXML
    private TableView budgetRequestTableViewfxid;
    @FXML
    private TableColumn costCol;
    @FXML
    private TableColumn profitlossCol;
    @FXML
    private TableView branchRequestTableViewfxid;
    @FXML
    private TableColumn locationCol;
    @FXML
    private TableColumn incomCol;
    @FXML
    private TableColumn itemNameCol;
    @FXML
    private TableColumn requiredBudgetCol;
    @FXML
    private TableView financialReportTableViewfxid;
    @FXML
    private TableColumn statusCol;
    @FXML
    private TableColumn notesCol;
    @FXML
    private TableColumn managerNameCol;


    @javafx.fxml.FXML
    public void initialize() {
        employeenameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        postCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("post"));
        contactCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("contact"));
        addressCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("address"));
        downloadMathodComboBox.getItems().addAll("PDF", "Excel", "CSV");
    }

    @javafx.fxml.FXML
    public void addEmployeeOnActionButton(ActionEvent actionEvent) {
        String name = employeeNameTextFieldfxid.getText();
        String post = employeePostTextFieldfxid.getText();
        String contact = employeeContactTextFieldfxid.getText();
        String address = employeeAddressTextFieldfxid.getText();
        if(name.isEmpty() || post.isEmpty() || contact.isEmpty() || address.isEmpty()) {
            System.out.println("Please fill all fields!");
            return;
        }
        Employee newEmployee = new Employee(name, post, contact, address);
        employeeManagementTableViewfxid.getItems().add(newEmployee);

    }

    @javafx.fxml.FXML
    public void approveBranchOnAction(ActionEvent actionEvent) {
        Object selectedBranch = branchRequestTableViewfxid.getSelectionModel().getSelectedItem();
        if (selectedBranch == null) {
            outputlableFxid.setText("Please select a branch to approve!");
            return;
        }
        outputlableFxid.setText("Branch approved!");
    }

    @javafx.fxml.FXML
    public void approveBudgetOnActionButton(ActionEvent actionEvent) {
        Object selectedBudget = budgetRequestTableViewfxid.getSelectionModel().getSelectedItem();
        if (selectedBudget == null) {
            outputlableFxid.setText("Please select a budget to approve!");
            return;
        }
        outputlableFxid.setText("Budget approved!");
    }

    @javafx.fxml.FXML
    public void rejectBudgetOnActionButton(ActionEvent actionEvent) {
        Object selectedBudget = budgetRequestTableViewfxid.getSelectionModel().getSelectedItem();
        if (selectedBudget == null) {
            outputlableFxid.setText("Please select a budget to reject!");
            return;
        }
        outputlableFxid.setText("Budget rejected!");
    }

    @javafx.fxml.FXML
    public void downloadReportOnActionButton(ActionEvent actionEvent) {
        String selectedFormat = downloadMathodComboBox.getValue();
        if (selectedFormat == null) {
            outputlableFxid.setText("Please select a download format!");
            return;
        }
        outputlableFxid.setText("Downloading report as " + selectedFormat + "...");
    }

    @javafx.fxml.FXML
    public void rejectBranchOnAction(ActionEvent actionEvent) { Object selectedBranch = branchRequestTableViewfxid.getSelectionModel().getSelectedItem();
        if (selectedBranch == null) {
            outputlableFxid.setText("Please select a branch to reject!");
            return;
        }
        outputlableFxid.setText("Branch rejected!");
    }
}