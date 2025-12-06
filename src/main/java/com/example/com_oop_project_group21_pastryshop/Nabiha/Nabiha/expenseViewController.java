package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;


import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class expenseViewController
{
    @javafx.fxml.FXML
    private TableColumn<Expense,Integer> totalExpenseTableColumn;
    @javafx.fxml.FXML
    private TextField yearTextField;
    @javafx.fxml.FXML
    private TableColumn<Expense,String>yearTableColumn;
    @javafx.fxml.FXML
    private ComboBox<String> monthComboBox;
    @javafx.fxml.FXML
    private TableColumn <Expense,String>monthTableColumn;
    @javafx.fxml.FXML
    private TableView<Expense> expenseTableView;
    @javafx.fxml.FXML
    private TextField totaExpenseTextField;



    private ArrayList<Expense>Expenselist;




    @javafx.fxml.FXML
    public void initialize() {
        Expenselist= new ArrayList<Expense>();
        monthComboBox.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");
        totalExpenseTableColumn.setCellValueFactory(new PropertyValueFactory<>("totalExpense"));
        yearTableColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        monthTableColumn.setCellValueFactory(new PropertyValueFactory<>("monthName"));


    }

    @javafx.fxml.FXML
    public void submitOnAction(ActionEvent event) {
        int totalExpense = Integer.parseInt(totaExpenseTextField.getText());
        Expense newExpense = new Expense(

                monthComboBox.getValue(),
                yearTextField.getText(),
                totalExpense
        );

        if (monthComboBox.getValue() == null || yearTextField.getText().isEmpty() || totaExpenseTextField.getText().isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Fill all the fields up");
            error.show();
        } else {
            Expenselist.add(newExpense);
            expenseTableView.getItems().add(newExpense);
        }
    }
}