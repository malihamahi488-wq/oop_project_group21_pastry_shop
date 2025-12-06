package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

import com.example.oopfinalprojectowncontri.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class accountantDashBoardViewController
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void FinanceOnAction(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void salarySheetforEmployeesOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("salarySheetForCEOView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //
        }


    }

    @javafx.fxml.FXML
    public void ExpenseRecordOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("expenseView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //
        }
    }

    @javafx.fxml.FXML
    public void CreateSalarySheetForEmployeesOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("salarySheetView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //
        }
    }

    @javafx.fxml.FXML
    public void FinancialReportOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void TransactionOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("transactionLogView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //
        }
    }

    @javafx.fxml.FXML
    public void RecordExpenseApprovalOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("expenseReportForCEOView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //
        }
    }

    @javafx.fxml.FXML
    public void generatingMonthlyIncomeOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("monthlyIncomeView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //
        }
    }
}