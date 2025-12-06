package com.example.BranchManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Goal-8 Model: DeactivateProduct
 *
 * Attributes:
 *  - productID: int
 *  - managerID: int
 *
 * Methods:
 *  + showProducts(): List<Product>
 *  + deactivate(productID: int): boolean
 *  + confirmDeactivation(): boolean
 */
public class DeactivateProduct {

    private int productID;
    private int managerID;

    // Simple in-memory store for demonstration
    private static final List<Product> PRODUCT_STORE = new ArrayList<>();

    static {
        PRODUCT_STORE.add(new Product(1, "Chocolate Cake", 15.99, true));
        PRODUCT_STORE.add(new Product(2, "Croissant", 2.99, true));
        PRODUCT_STORE.add(new Product(3, "Latte", 3.49, true));
        PRODUCT_STORE.add(new Product(4, "Old Seasonal Pie", 7.99, false)); // already inactive
    }

    // UML: + showProducts(): List<Product>
    // "System displays available products" -> commonly means active products
    public List<Product> showProducts() {
        List<Product> active = new ArrayList<>();
        for (Product p : PRODUCT_STORE) {
            if (p.isActive()) {
                active.add(p);
            }
        }
        return active;
    }

    // UML: + deactivate(productID: int): boolean
    public boolean deactivate(int productID) {
        for (Product p : PRODUCT_STORE) {
            if (p.getId() == productID) {
                if (!p.isActive()) {
                    // already inactive
                    return false;
                }
                p.setActive(false);
                // TODO: persist this change to DB
                return true;
            }
        }
        return false;
    }

    // UML: + confirmDeactivation(): boolean
    // In a real system, this might perform extra checks/logging.
    // Here it just returns true after UI confirmation is done.
    public boolean confirmDeactivation() {
        // You could log managerID and productID here if needed
        return true;
    }

    // Getters & setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    // Inner Product representation
    public static class Product {
        private int id;
        private String name;
        private double price;
        private boolean active;

        public Product(int id, String name, double price, boolean active) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.active = active;
        }

        public int getId() { return id; }

        public String getName() { return name; }

        public double getPrice() { return price; }

        public boolean isActive() { return active; }

        public void setActive(boolean active) { this.active = active; }

        // For TableColumn "status"
        public String getStatus() {
            return active ? "Active" : "Inactive";
        }
    }
}
