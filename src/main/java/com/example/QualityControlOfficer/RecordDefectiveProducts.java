package com.example.QualityControlOfficer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User-8, Goal-5: RecordDefectiveProducts
 *
 * Attributes:
 *  - officerID: int
 *  - productID: int
 *  - defectReason: String
 *  - quantity: int
 *
 * Methods:
 *  + addDefectiveRecord(productID, reason, qty): boolean
 *  + validateDefectEntry(): boolean
 */
public class RecordDefectiveProducts {

    private int officerID;
    private int productID;
    private String defectReason;
    private int quantity;

    // Simulated stock table
    private static final List<ProductStock> STOCK = new ArrayList<>();

    // Simulated defective records table
    private static final List<DefectRecord> DEFECT_RECORDS = new ArrayList<>();

    static {
        // Initial fake stock
        STOCK.add(new ProductStock(1, "Chocolate Cake - Box", 50));
        STOCK.add(new ProductStock(2, "Croissant Pack", 120));
        STOCK.add(new ProductStock(3, "Iced Latte Bottle", 80));
    }

    // For optional display of current stock
    public List<ProductStock> getCurrentStock() {
        return new ArrayList<>(STOCK);
    }

    // UML: + addDefectiveRecord(productID, reason, qty): boolean
    public boolean addDefectiveRecord(int productID, String reason, int qty) {
        this.productID = productID;
        this.defectReason = reason;
        this.quantity = qty;

        if (!validateDefectEntry()) {
            return false;
        }

        ProductStock stockItem = findStockByProductId(productID);
        if (stockItem == null) {
            return false; // product doesn't exist in stock
        }

        if (stockItem.getQuantity() < qty) {
            return false; // not enough stock
        }

        // Decrease stock
        stockItem.setQuantity(stockItem.getQuantity() - qty);

        // Add defective record
        DefectRecord record = new DefectRecord(
                productID,
                officerID,
                reason.trim(),
                qty,
                LocalDateTime.now()
        );
        DEFECT_RECORDS.add(record);

        // In real app, persist both stock and defect tables to DB
        System.out.println("Defect recorded: productID=" + productID +
                ", qty=" + qty + ", reason=" + reason +
                ", officerID=" + officerID);

        return true;
    }

    // UML: + validateDefectEntry(): boolean
    public boolean validateDefectEntry() {
        if (productID <= 0) return false;
        if (quantity <= 0) return false;
        if (defectReason == null || defectReason.trim().length() < 5) return false;

        ProductStock stockItem = findStockByProductId(productID);
        if (stockItem == null) {
            return false;
        }

        if (quantity > stockItem.getQuantity()) {
            return false;
        }

        return true;
    }

    private ProductStock findStockByProductId(int productId) {
        for (ProductStock s : STOCK) {
            if (s.getProductId() == productId) {
                return s;
            }
        }
        return null;
    }

    // Getters & setters

    public int getOfficerID() {
        return officerID;
    }

    public void setOfficerID(int officerID) {
        this.officerID = officerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDefectReason() {
        return defectReason;
    }

    public void setDefectReason(String defectReason) {
        this.defectReason = defectReason;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Helper inner classes

    // Represents stock for a product
    public static class ProductStock {
        private int productId;
        private String productName;
        private int quantity;

        public ProductStock(int productId, String productName, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
        }

        public int getProductId() { return productId; }

        public String getProductName() { return productName; }

        public int getQuantity() { return quantity; }

        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    // Represents a defective record
    public static class DefectRecord {
        private int productID;
        private int officerID;
        private String reason;
        private int quantity;
        private LocalDateTime createdAt;

        public DefectRecord(int productID, int officerID, String reason, int quantity, LocalDateTime createdAt) {
            this.productID = productID;
            this.officerID = officerID;
            this.reason = reason;
            this.quantity = quantity;
            this.createdAt = createdAt;
        }

        public int getProductID() { return productID; }

        public int getOfficerID() { return officerID; }

        public String getReason() { return reason; }

        public int getQuantity() { return quantity; }

        public LocalDateTime getCreatedAt() { return createdAt;
        }
    }
}
