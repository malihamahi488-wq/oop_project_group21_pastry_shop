package com.example.com_oop_project_group21_pastryshop.Nabiha;

import com.example.oopfinalprojectowncontri.TransactionLog;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class transactionLogViewController
{
    @javafx.fxml.FXML
    private ComboBox<String> transactionTypeComboBox;
    @javafx.fxml.FXML
    private TableColumn<TransactionLog, Integer> amountTableColumn;
    @javafx.fxml.FXML
    private TextField AmountTextField;
    @javafx.fxml.FXML
    private TableColumn<TransactionLog,String> nameTableColumn;
    @javafx.fxml.FXML
    private TextField transactionIDtextField;
    @javafx.fxml.FXML
    private TableColumn<TransactionLog,String> transactionTypeTableColumn;
    @javafx.fxml.FXML
    private TextField IDtextField;
    @javafx.fxml.FXML
    private TextField nametextField;
    @javafx.fxml.FXML
    private TableColumn<TransactionLog,String> TansactionIDTableColumn;
    @javafx.fxml.FXML
    private TableView<TransactionLog> TransactionLogTableView;
    @javafx.fxml.FXML
    private TableColumn<TransactionLog,String> IDTableColumn;
    @javafx.fxml.FXML
    private TextField filtertextField;

    private ArrayList<TransactionLog> Transactionlist;

    @javafx.fxml.FXML
    public void initialize() {
        Transactionlist= new ArrayList<TransactionLog>();
        transactionTypeComboBox.getItems().addAll("Send","Receive");
        amountTableColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("reciverName"));
        IDTableColumn.setCellValueFactory(new PropertyValueFactory<>("reciverID"));
        TansactionIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        transactionTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
    }

    @javafx.fxml.FXML
    public void SubmitToTableOnAction(ActionEvent actionEvent) {
        int amount = Integer.parseInt(AmountTextField.getText());
        TransactionLog newTransaction = new TransactionLog(

                transactionTypeComboBox.getValue(),
                transactionIDtextField.getText(),
                nametextField.getText(),
                IDtextField.getText(),
                amount
        );

        if (transactionTypeComboBox.getValue() == null ||nametextField.getText().isEmpty() || IDtextField.getText().isEmpty()|| transactionIDtextField.getText().isEmpty()||AmountTextField.getText().isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Fill all the fields up");
            error.show();
        } else {
            Transactionlist.add(newTransaction);
            TransactionLogTableView.getItems().add(newTransaction);
        }
    }

    @javafx.fxml.FXML
    public void SearchOnAction(ActionEvent actionEvent) {
        String filterText = filtertextField.getText();
        TransactionLogTableView.getItems().clear();

        if (filterText.isEmpty()) {
            TransactionLogTableView.getItems().addAll(Transactionlist);
        } else {
            for (TransactionLog transaction :  Transactionlist) {

                String type = transaction.getTransactionType();
                String transactionid = transaction.getTransactionId();
                String name = transaction.getReciverName();
                String id= transaction.getReciverID();
                String amount = String.valueOf(transaction.getAmount());



                if(type==filterText||transactionid==filterText||name==filterText||id==filterText||amount==filterText)
                    TransactionLogTableView.getItems().add(transaction);
            }
        }
    }
}