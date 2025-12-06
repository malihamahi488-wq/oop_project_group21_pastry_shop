package com.example.QualityControlOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ViewReturnedProductsController {

    @FXML
    private Label lblOfficerId;

    @FXML
    private TableView<ViewReturnedProducts.ReturnedProduct> tblReturns;
    @FXML
    private TableColumn<ViewReturnedProducts.ReturnedProduct, Integer> colReturnId;
    @FXML
    private TableColumn<ViewReturnedProducts.ReturnedProduct, String> colProductName;
    @FXML
    private TableColumn<ViewReturnedProducts.ReturnedProduct, Integer> colQty;
    @FXML
    private TableColumn<ViewReturnedProducts.ReturnedProduct, String> colReturnDate;
    @FXML
    private TableColumn<ViewReturnedProducts.ReturnedProduct, String> colStatus;

    @FXML
    private Label lblReturnId;
    @FXML
    private Label lblProductId;
    @FXML
    private Label lblProductName;
    @FXML
    private Label lblQuantity;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblReturnDate;
    @FXML
    private Label lblStatusDetail;

    @FXML
    private TextArea txtReason;
    @FXML
    private TextArea txtInternalNotes;

    @FXML
    private Label lblStatus;

    private final ViewReturnedProducts model = new ViewReturnedProducts();
    private final ObservableList<ViewReturnedProducts.ReturnedProduct> returnData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Simulated officerID from login
        model.setOfficerID(3001);
        lblOfficerId.setText(String.valueOf(model.getOfficerID()));

        setupTable();
        loadReturnList();

        tblReturns.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                onReturnSelected(newV);
            }
        });
    }

    private void setupTable() {
        colReturnId.setCellValueFactory(new PropertyValueFactory<>("returnID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDateString"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblReturns.setItems(returnData);
    }

    private void loadReturnList() {
        returnData.clear();
        List<ViewReturnedProducts.ReturnedProduct> list = model.viewReturnList();
        returnData.addAll(list);
        lblStatus.setText("Loaded " + list.size() + " returned product records.");
    }

    @FXML
    private void onReloadReturns() {
        loadReturnList();
        clearDetails();
    }

    private void onReturnSelected(ViewReturnedProducts.ReturnedProduct rp) {
        model.setReturnID(rp.getReturnID());

        ViewReturnedProducts.ReturnedProduct details = model.viewReturnDetails(rp.getReturnID());
        if (details == null) {
            clearDetails();
            lblStatus.setText("Return details not found.");
            return;
        }

        lblReturnId.setText(String.valueOf(details.getReturnID()));
        lblProductId.setText(String.valueOf(details.getProductID()));
        lblProductName.setText(details.getProductName());
        lblQuantity.setText(String.valueOf(details.getQuantity()));
        lblCustomerName.setText(details.getCustomerName());
        lblReturnDate.setText(details.getReturnDateString());
        lblStatusDetail.setText(details.getStatus());
        txtReason.setText(details.getReason());
        txtInternalNotes.setText(details.getInternalNotes() == null ? "" : details.getInternalNotes());

        lblStatus.setText("Viewing return ID " + details.getReturnID());
    }

    private void clearDetails() {
        lblReturnId.setText("-");
        lblProductId.setText("-");
        lblProductName.setText("-");
        lblQuantity.setText("-");
        lblCustomerName.setText("-");
        lblReturnDate.setText("-");
        lblStatusDetail.setText("-");
        txtReason.clear();
        txtInternalNotes.clear();
    }
}
