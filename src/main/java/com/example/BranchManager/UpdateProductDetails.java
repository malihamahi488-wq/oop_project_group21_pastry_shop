package com.example.BranchManager;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Model implementing UpdateProductDetails UML.
 *
 * - loadProduct(): loads the product into internal buffer (mock)
 * - updateField(field, value): updates the buffer
 * - saveUpdatedProduct(): commits the buffer to the in-memory store (mock persistence)
 *
 * Contains an inner Product class used by TableView.
 */
public class UpdateProductDetails {

    private final int managerID;
    private final Map<String, Product> productStore = new LinkedHashMap<>();

    // buffer for currently loaded product
    private Product buffer = null;

    public UpdateProductDetails(int managerID) {
        this.managerID = managerID;
        seedMockProducts();
    }

    // --- UML methods ---

    /**
     * Load product by ID and return it (for TableView use we also provide loadAllProducts()).
     */
    public Product loadProduct(String productId) {
        buffer = productStore.get(productId);
        // return the buffer (could be null)
        return buffer;
    }

    /**
     * Update a field on the buffer.
     */
    public boolean updateField(String field, String value) {
        if (buffer == null) return false;
        switch (field) {
            case "name":
                buffer.setName(value);
                return true;
            case "category":
                buffer.setCategory(value);
                return true;
            case "price":
                try {
                    double p = Double.parseDouble(value);
                    buffer.setPrice(p);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case "stockQty":
                try {
                    int s = Integer.parseInt(value);
                    buffer.setStockQty(s);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            case "description":
                buffer.setDescription(value);
                return true;
            default:
                return false;
        }
    }

    /**
     * Save the buffer into the store (mock persistence).
     */
    public boolean saveUpdatedProduct() {
        if (buffer == null) return false;
        productStore.put(buffer.getProductId(), buffer);
        // in real app -> persist to DB
        return true;
    }

    /**
     * Return all products (for TableView display)
     */
    public List<Product> loadAllProducts() {
        return new ArrayList<>(productStore.values());
    }

    // --- Mock data ---
    private void seedMockProducts() {
        addMock("P100", "Chocolate Cake", "Cakes", 25.50, 12, "Rich chocolate layered cake");
        addMock("P101", "Vanilla Cupcake", "Pastries", 3.50, 60, "Classic vanilla cupcake with frosting");
        addMock("P102", "Blueberry Muffin", "Pastries", 2.75, 45, "Fresh blueberry muffin");
        addMock("P103", "Sourdough Bread", "Breads", 6.00, 20, "Handmade sourdough loaf");
    }

    private void addMock(String id, String name, String category, double price, int stock, String desc) {
        Product p = new Product(id, name, category, price, stock, desc);
        productStore.put(id, p);
    }

    // --- Inner Product class used by TableView and Controller ---
    public static class Product {
        private final SimpleStringProperty productId;
        private final SimpleStringProperty name;
        private final SimpleStringProperty category;
        private final SimpleDoubleProperty price;
        private final SimpleIntegerProperty stock;
        private String description;

        public Product(String productId, String name, String category, double price, int stockQty, String description) {
            this.productId = new SimpleStringProperty(productId);
            this.name = new SimpleStringProperty(name);
            this.category = new SimpleStringProperty(category);
            this.price = new SimpleDoubleProperty(price);
            this.stock = new SimpleIntegerProperty(stockQty);
            this.description = description;
        }

        public String getProductId() { return productId.get(); }
        public StringProperty productIdProperty() { return productId; }

        public String getName() { return name.get(); }
        public void setName(String newName) { this.name.set(newName); }
        public StringProperty nameProperty() { return name; }

        public String getCategory() { return category.get(); }
        public void setCategory(String c) { this.category.set(c); }
        public StringProperty categoryProperty() { return category; }

        public double getPrice() { return price.get(); }
        public void setPrice(double p) { this.price.set(p); }
        public SimpleDoubleProperty priceProperty() { return price; }
        // price as string for column
        public StringProperty priceStringProperty() { return new SimpleStringProperty(String.format("%.2f", getPrice())); }

        public int getStockQty() { return stock.get(); }
        public void setStockQty(int s) { this.stock.set(s); }
        public SimpleIntegerProperty stockProperty() { return stock; }
        public StringProperty stockStringProperty() { return new SimpleStringProperty(String.valueOf(getStockQty())); }

        public String getDescription() { return description; }
        public void setDescription(String d) { this.description = d; }
    }
}
