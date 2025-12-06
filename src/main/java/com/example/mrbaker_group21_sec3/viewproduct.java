package com.example.mrbaker_group21_sec3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class viewproduct {
    public static class product {
        private String productName;
        private String category;
        private Double price;

        public product(String productName, String category, Double price) {
            this.productName = productName;
            this.category = category;
            this.price = price;
        }
        public String getProductName() { return productName; }
        public String getCategory() { return category; }
        public Double getPrice() { return price; }
    }
    public static ObservableList<product> productList =
            FXCollections.observableArrayList(

                    new product("Black Forest Cake", "Cake", 550.0),
                    new product("Red Velvet Cake", "Cake", 700.0),
                    new product("Cream Bread", "Bread", 120.0),
                    new product("Milk Bread", "Bread", 80.0),
                    new product("Chicken Roll", "Snacks", 50.0),
                    new product("Cold Coffee", "Drinks", 150.0),
                    new product("Vanilla cake","Cake",800.00)
            );
}
