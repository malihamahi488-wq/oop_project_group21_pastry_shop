package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

import com.example.oopfinalprojectowncontri.MonthlyIncome;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class monthlyIncomeViewController
{
    @javafx.fxml.FXML
    private TableView<MonthlyIncome> monthlyIncomeTavleView;
    @javafx.fxml.FXML
    private TextField yearTextField;
    @javafx.fxml.FXML
    private TableColumn<MonthlyIncome,String> yearTableColumn;
    @javafx.fxml.FXML
    private TextField filterTextField;
    @javafx.fxml.FXML
    private ComboBox<String> monthComboBox;
    @javafx.fxml.FXML
    private TableColumn<MonthlyIncome,String> monthTableColumn;
    @javafx.fxml.FXML
    private TableColumn<MonthlyIncome,String> incomeTableColumn;
    @javafx.fxml.FXML
    private TextField incomeTextField;



    private ArrayList<MonthlyIncome> MonthlyIncomelist;

    @javafx.fxml.FXML
    public void initialize() {
        monthComboBox.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");
        monthTableColumn.setCellValueFactory(new PropertyValueFactory<>("monthName"));
        yearTableColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        incomeTableColumn.setCellValueFactory(new PropertyValueFactory<>("income"));

    }

    @javafx.fxml.FXML
    public void addMonthlyIncomOnAction(ActionEvent actionEvent) {
        int totaIncome = Integer.parseInt(incomeTextField.getText());
        MonthlyIncome newIncome = new MonthlyIncome(

                monthComboBox.getValue(),
                yearTextField.getText(),
                totaIncome
        );

        if (monthComboBox.getValue() == null || yearTextField.getText().isEmpty() || incomeTextField.getText().isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Fill all the fields up");
            error.show();
        } else {
            MonthlyIncomelist.add(newIncome);
            monthlyIncomeTavleView.getItems().add(newIncome);
        }
    }

    @javafx.fxml.FXML
    public void searchOnAction(ActionEvent actionEvent) {


        String filterText = filterTextField.getText();
        monthlyIncomeTavleView.getItems().clear();

        if (filterText.isEmpty()) {
            monthlyIncomeTavleView.getItems().addAll(MonthlyIncomelist);
        } else {
            for (MonthlyIncome income :  MonthlyIncomelist) {

                String month = income.getMonthName();
                String year = income.getYear();
                String totalIncome = String.valueOf(income.getIncome());

                if(month==filterText||year==filterText||totalIncome==filterText)
                    monthlyIncomeTavleView.getItems().add(income);
                }
            }
        }
    }
