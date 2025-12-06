package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class addEmployeeToEmployeeListViewController {


    @FXML
    private TableView<Employee> EmployeeListTableView;
    @FXML
    private TextField NameTextField;
    @FXML
    private TextField IDTextField;
    @FXML
    private TextField DesignationTextField;
    @FXML
    private DatePicker DOBDatePicker;
    @FXML
    private DatePicker JoiningDateDatePicker;
    @FXML
    private TextField SearchTextField;
    @FXML
    private TableColumn<Employee, String> NameTableColumn;
    @FXML
    private TableColumn<Employee, String> IDTableColumn;
    @FXML
    private TableColumn<Employee, String> DesignationTableColumn;
    @FXML
    private TableColumn<Employee, LocalDate> DateOfBirthTableColumn;
    @FXML
    private TableColumn<Employee, LocalDate> JoiningDateTableColumn;


    private ArrayList<Employee> Employeelist = new ArrayList<>();

    private ObservableList<Employee> observableEmployeeList;



    @FXML
    public void initialize() {

        observableEmployeeList = FXCollections.observableArrayList(Employeelist);
        EmployeeListTableView.setItems(observableEmployeeList);


        NameTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        IDTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        DesignationTableColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        DateOfBirthTableColumn.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        JoiningDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("joiningDate"));
    }


    @FXML
    public void AddToEmployeeOnAction(ActionEvent actionEvent) {
        String name = NameTextField.getText();
        String id = IDTextField.getText();
        String designation = DesignationTextField.getText();
        LocalDate dob = DOBDatePicker.getValue();
        LocalDate joiningDate = JoiningDateDatePicker.getValue();


        if (name.isEmpty() || id.isEmpty() || designation.isEmpty() || dob == null || joiningDate == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Fill all the fields up");
            error.show();
            return;
        }


        Employee newEmployee = new Employee(name, id, designation, dob, joiningDate);

        Employeelist.add(newEmployee);
        observableEmployeeList.add(newEmployee);
        NameTextField.clear();
        IDTextField.clear();
        DesignationTextField.clear();
        DOBDatePicker.setValue(null);
        JoiningDateDatePicker.setValue(null);
    }


    @FXML
    public void FilterOnAction(ActionEvent actionEvent) {
        String filterText = SearchTextField.getText().toLowerCase();


        if (filterText.isEmpty()) {
            observableEmployeeList.setAll(Employeelist);
            return;
        }


        List<Employee> filteredList = new ArrayList<>();
        for (Employee employee : Employeelist) {


            if (employee.getEmployeeName().toLowerCase().contains(filterText) ||
                    employee.getEmployeeId().toLowerCase().contains(filterText) ||
                    employee.getDesignation().toLowerCase().contains(filterText))
            {
                filteredList.add(employee);
            }
        }


        observableEmployeeList.setAll(filteredList);
    }
}