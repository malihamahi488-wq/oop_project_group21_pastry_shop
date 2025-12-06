package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;


import com.example.mrbaker_group21_sec3.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class humanResourceManagerDashBoardViewController
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void EvaluteEmployeesOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("evaluationReportView.fxml"));
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
    public void ViewAttendaceOfEmployeeOnAction(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void CreateNewPolicyOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("policyView.fxml"));
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
    public void CreateEmployeeDatabaseOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void addEmployeetoListOnAction(ActionEvent actionEvent)  {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addEmployeeToEmployeeListView.fxml"));
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
    public void JobPostOnAction(ActionEvent actionEvent){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("jobPostView.fxml"));
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
    public void ChangePayrollOnAction(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("payrollView.fxml"));
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
    public void LeaveApplicationsViewOnAction(ActionEvent actionEvent) {
    }
}