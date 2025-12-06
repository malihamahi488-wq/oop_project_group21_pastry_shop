package com.example.QualityControlOfficer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * User-8, Goal-7: ViewReturnedProducts
 *
 * Attributes:
 *  - officerID: int
 *  - returnID: int
 *
 * Methods:
 *  + viewReturnList(): List<ReturnedProduct>
 *  + viewReturnDetails(returnID): ReturnedProduct
 */
public class ViewReturnedProducts {

    private int officerID;
    private int returnID;

    // Simulated returned products store
    private static final List<ReturnedProduct> RETURN_STORE = new ArrayList<>();

    static {
        RETURN_STORE.add(new ReturnedProduct(
                1, 101, "Chocolate Cake - Box", 2,
                "Damaged packaging",
                "John Doe",
                LocalDateTime.now().minusDays(1),
                "Processed",
                "Checked with logistics; likely courier issue."));
        RETURN_STORE.add(new ReturnedProduct(
                2, 102, "Croissant Pack", 5,
                "Stale taste reported",
                "Jane Smith",
                LocalDateTime.now().minusDays(3),
                "Pending Inspection",
                "Awaiting lab results."));
        RETURN_STORE.add(new ReturnedProduct(
                3, 103, "Iced Latte Bottle", 1,
                "Leaking bottle",
                "Alex Brown",
                LocalDateTime.now().minusHours(6),
                "Processed",
                "Batch flagged for additional checks."));
    }

    // UML: + viewReturnList(): List<ReturnedProduct>
    public List<ReturnedProduct> viewReturnList() {
        return new ArrayList<>(RETURN_STORE);
    }

    // UML: + viewReturnDetails(returnID): ReturnedProduct
    public ReturnedProduct viewReturnDetails(int returnID) {
        for (ReturnedProduct rp : RETURN_STORE) {
            if (rp.getReturnID() == returnID) {
                return rp;
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

    public int getReturnID() {
        return returnID;
    }

    public void setReturnID(int returnID) {
        this.returnID = returnID;
    }

    // Inner DTO/entity
    public static class ReturnedProduct {
        private int returnID;
        private int productID;
        private String productName;
        private int quantity;
        private String reason;
        private String customerName;
        private LocalDateTime returnDate;
        private String status;
        private String internalNotes;

        public ReturnedProduct(int returnID,
                               int productID,
                               String productName,
                               int quantity,
                               String reason,
                               String customerName,
                               LocalDateTime returnDate,
                               String status,
                               String internalNotes) {
            this.returnID = returnID;
            this.productID = productID;
            this.productName = productName;
            this.quantity = quantity;
            this.reason = reason;
            this.customerName = customerName;
            this.returnDate = returnDate;
            this.status = status;
            this.internalNotes = internalNotes;
        }

        public int getReturnID() { return returnID; }

        public int getProductID() { return productID; }

        public String getProductName() { return productName; }

        public int getQuantity() { return quantity; }

        public String getReason() { return reason; }

        public String getCustomerName() { return customerName; }

        public LocalDateTime getReturnDate() { return returnDate; }

        public String getStatus() { return status; }

        public String getInternalNotes() { return internalNotes; }

        public void setStatus(String status) { this.status = status; }

        public void setInternalNotes(String internalNotes) { this.internalNotes = internalNotes; }

        public String getReturnDateString() {
            if (returnDate == null) return "";
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return returnDate.format(fmt);
        }
    }
}
