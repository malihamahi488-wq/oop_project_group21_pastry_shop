package com.example.mrbaker_group21_sec3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class customerController {
    @javafx.fxml.FXML
    private TableColumn<viewproduct, String> historyCategoryCol;
    @javafx.fxml.FXML
    private TextField searchTextFieldfxid;
    @javafx.fxml.FXML
    private TextField phoneTextFieldfxid;
    @javafx.fxml.FXML
    private Label outputLablefxid;
    @javafx.fxml.FXML
    private TableColumn<viewproduct, String> categoryCol;
    @javafx.fxml.FXML
    private TableColumn<viewproduct, String> productNameCol;
    @javafx.fxml.FXML
    private TableColumn<viewproduct, Double> priceCol;
    @javafx.fxml.FXML
    private ComboBox<String> categoryComboBox;
    @javafx.fxml.FXML
    private TableView<order> orderhistorytableViewfxid;
    @javafx.fxml.FXML
    private TableColumn<order, Double> historyPriceCol;
    @javafx.fxml.FXML
    private TextField addressTextFieldfxid;
    @javafx.fxml.FXML
    private ComboBox<String> paymentMathodcomboBox;
    @javafx.fxml.FXML
    private TableView<viewproduct.product> productTableViewfxid;
    @javafx.fxml.FXML
    private TableColumn<order, String> historyProductcol;

    @javafx.fxml.FXML
    public void initialize() {
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryComboBox.getItems().addAll("Cake", "Bread", "Snacks", "Drinks", "All");
        paymentMathodcomboBox.getItems().addAll("bkash", "nagad","cash on delivary");

        productTableViewfxid.setItems(viewproduct.productList);

        historyProductcol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        historyCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        historyPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderhistorytableViewfxid.setItems(order.orderList);

    }


    @javafx.fxml.FXML
    public void placeOrderOnAction(ActionEvent actionEvent) { viewproduct.product p = productTableViewfxid.getSelectionModel().getSelectedItem();

        if (p == null) {
            outputLablefxid.setText("please select a product");
            return;
        }

        String address = addressTextFieldfxid.getText();
        String phone   = phoneTextFieldfxid.getText();
        String pay     = paymentMathodcomboBox.getValue();

        if (address.isEmpty() || phone.isEmpty() || pay == null) {
            outputLablefxid.setText("please fillup all information");
            return;
        }
        order newOrder = new order(
                p.getProductName(),
                p.getCategory(),
                p.getPrice(),
                address,
                phone,
                pay
        );

        order.orderList.add(newOrder);

        orderhistorytableViewfxid.setItems(order.orderList);

        outputLablefxid.setText("Order placed!");
    }

    @javafx.fxml.FXML
    public void cancelOrderOnAction(ActionEvent actionEvent) { order selectedOrder = orderhistorytableViewfxid.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            outputLablefxid.setText("there is no order of you");
            return;
        }
        order.orderList.remove(selectedOrder);
        orderhistorytableViewfxid.refresh();

        outputLablefxid.setText("Order Cancelled!");
    }

    @javafx.fxml.FXML
    public void categorybuttonOnAction(ActionEvent actionEvent) {
        String cat = categoryComboBox.getValue();
        if(cat ==null||cat.equals("All")) {
            productTableViewfxid.setItems(viewproduct.productList);
            return;
        }
        ObservableList<viewproduct.product> list = FXCollections.observableArrayList();
        for(viewproduct.product p :viewproduct.productList) {
            if (p.getCategory().equals(cat)) {
                list.add(p);
            }
        }

        productTableViewfxid.setItems(list);
    }





    @javafx.fxml.FXML
    public void searchButtonOnAction(ActionEvent actionEvent) { String text = searchTextFieldfxid.getText().toLowerCase();


        if (text.isEmpty()) {
            productTableViewfxid.setItems(viewproduct.productList);
            return;
        }
        ObservableList<viewproduct.product> list = FXCollections.observableArrayList();
        for (viewproduct.product p : viewproduct.productList) {
            if (p.getProductName().toLowerCase().contains(text)) {
                list.add(p);
            }
        }

        productTableViewfxid.setItems(list);
    }
}