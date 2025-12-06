package com.example.QualityControlOfficer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportProductComplaint {

    private int officerID;
    private int complaintID;
    private int productID;
    private String description;

    // Simulated persistent storage
    private static final List<ComplaintRecord> COMPLAINT_STORE = new ArrayList<>();
    private static int NEXT_COMPLAINT_ID = 1;

    /**
     * UML: + submitComplaint(productID, details): boolean
     * Saves the complaint and "sends notification".
     */
    public boolean submitComplaint(int productID, String details) {
        this.productID = productID;
        this.description = details;

        if (!validateComplaintFields()) {
            return false;
        }

        this.complaintID = NEXT_COMPLAINT_ID++;

        ComplaintRecord record = new ComplaintRecord(
                complaintID,
                officerID,
                productID,
                description,
                LocalDateTime.now()
        );
        COMPLAINT_STORE.add(record);

        // TODO: send real notification (e.g., email to manager / admin)
        // For now, just simulate with a console output:
        System.out.println("Notification: New complaint submitted, ID = " + complaintID);

        return true;
    }

    /**
     * UML: + validateComplaintFields(): boolean
     * Checks productID and description.
     */
    public boolean validateComplaintFields() {
        if (productID <= 0) {
            return false;
        }
        if (description == null) {
            return false;
        }
        String trimmed = description.trim();
        // Basic rule: at least 10 chars of explanation
        if (trimmed.isEmpty() || trimmed.length() < 10) {
            return false;
        }
        return true;
    }

    // Getters & setters

    public int getOfficerID() {
        return officerID;
    }

    public void setOfficerID(int officerID) {
        this.officerID = officerID;
    }

    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Simple record of a complaint
    public static class ComplaintRecord {
        private final int complaintID;
        private final int officerID;
        private final int productID;
        private final String description;
        private final LocalDateTime createdAt;

        public ComplaintRecord(int complaintID, int officerID, int productID,
                               String description, LocalDateTime createdAt) {
            this.complaintID = complaintID;
            this.officerID = officerID;
            this.productID = productID;
            this.description = description;
            this.createdAt = createdAt;
        }

        public int getComplaintID() {
            return complaintID;
        }

        public int getOfficerID() {
            return officerID;
        }

        public int getProductID() {
            return productID;
        }

        public String getDescription() {
            return description;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
    }
}
