package com.example.com_oop_project_group21_pastryshop.Nabiha;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class evaluationReportViewController {

    // FXML fields for TextFields and ComboBox
    @FXML
    private TextField DesignationTextField;
    @FXML
    private TextField EmployeeNameTextField;
    @FXML
    private TextField IDTextField;
    @FXML
    private ComboBox<String> monthNameComboBox;

    // FXML fields for TableView and Columns
    @FXML
    private TableView<EvaluateReport> evaluationTableView;
    @FXML
    private TableColumn<EvaluateReport, String> NameTableColumn;
    @FXML
    private TableColumn<EvaluateReport, String> IDTableColumn;
    @FXML
    private TableColumn<EvaluateReport, String> DesignationTableColumn;
    @FXML
    private TableColumn<EvaluateReport, String> MonthNameTableColumn;
    @FXML
    private TableColumn<EvaluateReport, String> RatingTableColumn;


    @FXML
    private RadioButton excellentRadioButton;
    @FXML
    private RadioButton veryGoodRadioButton;
    @FXML
    private RadioButton goodRadioButton;
    @FXML
    private RadioButton poorRadioButton;
    @FXML
    private RadioButton badRadioButton;

    // Data structure for the TableView
    private ObservableList<EvaluateReport> monthlyEvaluations;



    @FXML
    public void initialize() {

        ToggleGroup ratingToggleGroup = new ToggleGroup();
        excellentRadioButton.setToggleGroup(ratingToggleGroup);
        veryGoodRadioButton.setToggleGroup(ratingToggleGroup);
        goodRadioButton.setToggleGroup(ratingToggleGroup);
        poorRadioButton.setToggleGroup(ratingToggleGroup);
        badRadioButton.setToggleGroup(ratingToggleGroup);


        monthNameComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );


        monthlyEvaluations = FXCollections.observableArrayList();
        evaluationTableView.setItems(monthlyEvaluations);

        NameTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName")); // Assuming EvaluateReport has getEmployeeName()
        IDTableColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Assuming EvaluateReport has getId()
        DesignationTableColumn.setCellValueFactory(new PropertyValueFactory<>("designation")); // Assuming EvaluateReport has getDesignation()
        MonthNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("monthName")); // Assuming EvaluateReport has getMonthName()
        RatingTableColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyPerformance")); // Based on your screenshot's usage
    }


    @FXML
    public void AddToMonthlyEvaluationListOnAction(ActionEvent actionEvent) {

        String name = EmployeeNameTextField.getText();
        String id = IDTextField.getText();
        String designation = DesignationTextField.getText();
        String month = monthNameComboBox.getValue();


        Toggle selectedToggle = excellentRadioButton.getToggleGroup().getSelectedToggle();
        String rating = null;

        if (selectedToggle != null) {

            rating = ((RadioButton) selectedToggle).getText();
        }


        if (name.isEmpty() || id.isEmpty() || designation.isEmpty() || month == null || rating == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Please fill out all employee details, select a month, and choose a rating.");
            error.setHeaderText("Missing Information");
            error.show();
            return;
        }


        EvaluateReport newReport = new EvaluateReport(name, id, designation, month, rating);
        monthlyEvaluations.add(newReport);


        EmployeeNameTextField.clear();
        IDTextField.clear();
        DesignationTextField.clear();
        monthNameComboBox.getSelectionModel().clearSelection();
        if (selectedToggle != null) {
            selectedToggle.setSelected(false); // Deselect the radio button
        }


        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setContentText("Monthly evaluation added successfully.");
        confirmation.show();
    }
}