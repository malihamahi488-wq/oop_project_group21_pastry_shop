package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

import com.example.oopfinalprojectowncontri.Payroll;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class payrollViewController
{
    @javafx.fxml.FXML
    private TableView<Payroll> payrollTableView;
    @javafx.fxml.FXML
    private TextField SalaryTextField;
    @javafx.fxml.FXML
    private TextField designationTextField;
    @javafx.fxml.FXML
    private TableColumn<Payroll,String> designationTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Payroll,Integer>  salaryTableColumn;

    private ArrayList<Payroll> Payrolllist;

    @javafx.fxml.FXML
    public void initialize() {
        salaryTableColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        designationTableColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));

    }

    @javafx.fxml.FXML
    public void savePayrollOnAction(ActionEvent actionEvent) {
        int salary = Integer.parseInt(SalaryTextField.getText());
        Payroll newPayroll = new Payroll(
                designationTextField.getText(),
                salary
        );

        if (SalaryTextField.getText() == null || designationTextField.getText().isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Fill all the fields up");
            error.show();
        } else {
            Payrolllist.add(newPayroll);
            payrollTableView.getItems().add(newPayroll);
    }
}}