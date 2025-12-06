package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

import com.example.oopfinalprojectowncontri.Policies;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class policyViewController
{
    @javafx.fxml.FXML
    private TextArea policyTextArea;
    @javafx.fxml.FXML
    private TableView policyTableView;
    @javafx.fxml.FXML
    private TableColumn policyTableColumn;

    public TextArea getPolicyTextArea() {
        return policyTextArea;
    }
    private ArrayList<Policies> policyList;

    @javafx.fxml.FXML
    public void initialize() {
        policyTableColumn.setCellValueFactory(new PropertyValueFactory<>("policy"));

    }

    @javafx.fxml.FXML
    public void postPoliciesOnAction(ActionEvent actionEvent) {
        Policies newPolicy = new Policies(

                policyTextArea.getText()

        );
        if(policyTextArea==null){
            Alert error= new Alert(Alert.AlertType.INFORMATION);
            error.setContentText("Do Provide Policy");
            error.show();


            

        }else {
            policyList.add(newPolicy);
            policyTableView.getItems().add(newPolicy);
    }


}}