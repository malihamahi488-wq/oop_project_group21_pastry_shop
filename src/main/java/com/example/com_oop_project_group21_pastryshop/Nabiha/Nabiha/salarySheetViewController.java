package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;


import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class salarySheetViewController
{
    @javafx.fxml.FXML
    private TextField salaryTextField;
    @javafx.fxml.FXML
    private TableColumn<SalarySheet,Integer> SalaryTableColumn;
    @javafx.fxml.FXML
    private TableView<SalarySheet> salarySheetTableView;
    @javafx.fxml.FXML
    private TableColumn<SalarySheet,String> EmployeeIdTablecolumn;
    @javafx.fxml.FXML
    private TableColumn<SalarySheet,String> DesignationTableColumn;
    @javafx.fxml.FXML
    private TextField employeeNameTextField;
    @javafx.fxml.FXML
    private TextField designationTextField;
    @javafx.fxml.FXML
    private TableColumn<SalarySheet,String> employeeNameTableColumn;
    @javafx.fxml.FXML
    private TextField IDTextField;
    private ArrayList<SalarySheet> SalarySheetlist;

    @javafx.fxml.FXML
    public void initialize() {
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        SalaryTableColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        EmployeeIdTablecolumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        DesignationTableColumn.setCellValueFactory(new PropertyValueFactory<>("Designation"));
    }

    @javafx.fxml.FXML
    public void CreatSalarySheetOnAction(ActionEvent actionEvent) {
        int salary = Integer.parseInt(salaryTextField.getText());
        SalarySheet newSalarySheet = new SalarySheet(
                employeeNameTextField.getText(),
                IDTextField.getText(),
                designationTextField.getText(),
                salary
        );

        if (employeeNameTextField.getText() == null || IDTextField.getText().isEmpty() || designationTextField.getText().isEmpty()||salaryTextField.getText()==null) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Fill all the fields up");
            error.show();
        } else {
            SalarySheetlist.add(newSalarySheet);
            salarySheetTableView.getItems().add(newSalarySheet);
        }
    }
}