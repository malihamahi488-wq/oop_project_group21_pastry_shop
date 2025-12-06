package com.example.QualityControlOfficer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User-8, Goal-4: ApproveProductsForSale
 *
 * Attributes:
 *  - officerID: int
 *  - productID: int
 *  - decision: String
 *
 * Methods:
 *  + viewPendingProducts(): List<Product>
 *  + approve(productID): boolean
 *  + reject(productID): boolean
 */
public class ApproveProductsForSale {

    private int officerID;
    private int productID;
    private String decision;
    private String decisionNotes;

    // Simulated store of products and decisions
    private static final List<Product> PRODUCT_STORE = new ArrayList<>();
    private static final List<DecisionRecord> DECISION_LOG = new ArrayList<>();

    static {
        PRODUCT_STORE.add(new Product(1, "Chocolate Cake - Box", "BATCH-CAKE-01", "Checked", "PendingApproval"));
        PRODUCT_STORE.add(new Product(2, "Croissant Pack", "BATCH-CR-05", "Checked", "PendingApproval"));
        PRODUCT_STORE.add(new Product(3, "Iced Latte Bottle", "BATCH-IL-10", "Checked", "ApprovedForSale"));
        PRODUCT_STORE.add(new Product(4, "Old Batch Sample", "BATCH-OLD-01", "Failed", "Rejected"));
    }

    // UML: + viewPendingProducts(): List<Product>
    public List<Product> viewPendingProducts() {
        List<Product> pending = new ArrayList<>();
        for (Product p : PRODUCT_STORE) {
            if ("PendingApproval".equalsIgnoreCase(p.getApprovalStatus())) {
                pending.add(p);
            }
        }
        return pending;
    }

    // UML: + approve(productID): boolean
    public boolean approve(int productID) {
        Product p = findProductById(productID);
        if (p == null) return false;

        p.setApprovalStatus("ApprovedForSale");

        recordDecision(productID, "Approved");
        // In real app, update DB here
        return true;
    }

    // UML: + reject(productID): boolean
    public boolean reject(int productID) {
        Product p = findProductById(productID);
        if (p == null) return false;

        p.setApprovalStatus("Rejected");

        recordDecision(productID, "Rejected");
        // In real app, update DB here
        return true;
    }

    private Product findProductById(int id) {
        for (Product p : PRODUCT_STORE) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private void recordDecision(int productID, String decision) {
        DecisionRecord record = new DecisionRecord(
                productID,
                officerID,
                decision,
                decisionNotes,
                LocalDateTime.now()
        );
        DECISION_LOG.add(record);
        // For debugging / simulation:
        System.out.println("Decision: productID=" + productID +
                ", officerID=" + officerID +
                ", decision=" + decision +
                ", notes=" + decisionNotes);
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

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getDecisionNotes() {
        return decisionNotes;
    }

    public void setDecisionNotes(String decisionNotes) {
        this.decisionNotes = decisionNotes;
    }

    // Inner classes

    public static class Product {
        private int id;
        private String name;
        private String batch;
        private String qualityStatus;   // e.g., "Checked", "Failed", etc.
        private String approvalStatus;  // "PendingApproval", "ApprovedForSale", "Rejected"

        public Product(int id, String name, String batch, String qualityStatus, String approvalStatus) {
            this.id = id;
            this.name = name;
            this.batch = batch;
            this.qualityStatus = qualityStatus;
            this.approvalStatus = approvalStatus;
        }

        public int getId() { return id; }

        public String getName() { return name; }

        public String getBatch() { return batch; }

        public String getQualityStatus() { return qualityStatus; }

        public void setQualityStatus(String qualityStatus) { this.qualityStatus = qualityStatus; }

        public String getApprovalStatus() { return approvalStatus; }

        public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }
    }

    public static class DecisionRecord {
        private int productID;
        private int officerID;
        private String decision;
        private String notes;
        private LocalDateTime decidedAt;

        public DecisionRecord(int productID, int officerID,
                              String decision, String notes,
                              LocalDateTime decidedAt) {
            this.productID = productID;
            this.officerID = officerID;
            this.decision = decision;
            this.notes = notes;
            this.decidedAt = decidedAt;
        }

        public int getProductID() { return productID; }

        public int getOfficerID() { return officerID; }

        public String getDecision() { return decision; }

        public String getNotes() { return notes; }

        public LocalDateTime getDecidedAt() { return decidedAt;
        }
    }
}
