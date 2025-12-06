package com.example.QualityControlOfficer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User-8, Goal-3: CheckProductQuality
 *
 * Attributes:
 *  - officerID: int
 *  - productID: int
 *  - testResults: Map<String, Object>
 *
 * Methods:
 *  + recordQualityTest(productID, results): boolean
 *  + validateTestResults(): boolean
 */
public class CheckProductQuality {

    private int officerID;
    private int productID;
    private Map<String, Object> testResults = new HashMap<>();

    // Simulated products awaiting inspection
    private static final List<ProductUnderTest> PRODUCT_STORE = new ArrayList<>();

    // Simulated quality reports
    private static final List<QualityReport> QUALITY_REPORTS = new ArrayList<>();

    static {
        PRODUCT_STORE.add(new ProductUnderTest(1, "Chocolate Cake - Box", "BATCH-CAKE-01", "Pending"));
        PRODUCT_STORE.add(new ProductUnderTest(2, "Croissant Pack", "BATCH-CR-05", "Pending"));
        PRODUCT_STORE.add(new ProductUnderTest(3, "Iced Latte Bottle", "BATCH-IL-10", "Pending"));
        PRODUCT_STORE.add(new ProductUnderTest(4, "Old Sample", "BATCH-OLD-01", "Approved")); // will be filtered out
    }

    // Returns only products that are pending inspection
    public List<ProductUnderTest> getProductsAwaitingInspection() {
        List<ProductUnderTest> pending = new ArrayList<>();
        for (ProductUnderTest p : PRODUCT_STORE) {
            if ("Pending".equalsIgnoreCase(p.getStatus())) {
                pending.add(p);
            }
        }
        return pending;
    }

    // UML: + recordQualityTest(productID, results): boolean
    public boolean recordQualityTest(int productID, Map<String, Object> results) {
        this.productID = productID;
        this.testResults = results != null ? results : new HashMap<>();

        if (!validateTestResults()) {
            return false;
        }

        ProductUnderTest product = findProductById(productID);
        if (product == null) {
            return false;
        }

        // Parse fields
        double temperature = parseTemperature((String) testResults.get("temperature"));
        String appearance = (String) testResults.get("appearance");
        String hygiene = (String) testResults.get("hygiene");
        String finalStatus = (String) testResults.get("finalStatus");
        String notes = (String) testResults.getOrDefault("notes", "");

        // Update product status based on finalStatus
        if ("Pass".equalsIgnoreCase(finalStatus)) {
            product.setStatus("Approved");
        } else {
            product.setStatus("Rejected");
        }

        // Create a quality report record
        QualityReport report = new QualityReport(
                productID,
                officerID,
                temperature,
                appearance,
                hygiene,
                finalStatus,
                notes,
                LocalDateTime.now()
        );
        QUALITY_REPORTS.add(report);

        // In real application, persist PRODUCT_STORE and QUALITY_REPORTS to DB
        return true;
    }

    // UML: + validateTestResults(): boolean
    public boolean validateTestResults() {
        if (testResults == null) return false;

        Object tempObj = testResults.get("temperature");
        Object appearanceObj = testResults.get("appearance");
        Object hygieneObj = testResults.get("hygiene");
        Object finalStatusObj = testResults.get("finalStatus");

        if (tempObj == null || appearanceObj == null ||
                hygieneObj == null || finalStatusObj == null) {
            return false;
        }

        String tempStr = tempObj.toString().trim();
        double temperature;
        try {
            temperature = Double.parseDouble(tempStr);
        } catch (NumberFormatException e) {
            return false;
        }

        // Example rule: temperature between -5°C and 15°C for pastry storage
        if (temperature < -5 || temperature > 25) {
            return false;
        }

        String appearance = appearanceObj.toString();
        String hygiene = hygieneObj.toString();
        String finalStatus = finalStatusObj.toString();

        if (appearance.isBlank() || hygiene.isBlank() || finalStatus.isBlank()) {
            return false;
        }

        if (!finalStatus.equalsIgnoreCase("Pass") &&
                !finalStatus.equalsIgnoreCase("Fail")) {
            return false;
        }

        return true;
    }

    private ProductUnderTest findProductById(int id) {
        for (ProductUnderTest p : PRODUCT_STORE) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private double parseTemperature(String tempStr) {
        try {
            return Double.parseDouble(tempStr.trim());
        } catch (Exception e) {
            return 0.0;
        }
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

    public Map<String, Object> getTestResults() {
        return testResults;
    }

    public void setTestResults(Map<String, Object> testResults) {
        this.testResults = testResults;
    }

    // Inner classes

    public static class ProductUnderTest {
        private int id;
        private String name;
        private String batch;
        private String status; // Pending / Approved / Rejected

        public ProductUnderTest(int id, String name, String batch, String status) {
            this.id = id;
            this.name = name;
            this.batch = batch;
            this.status = status;
        }

        public int getId() { return id; }

        public String getName() { return name; }

        public String getBatch() { return batch; }

        public String getStatus() { return status; }

        public void setStatus(String status) { this.status = status; }
    }

    public static class QualityReport {
        private int productID;
        private int officerID;
        private double temperature;
        private String appearance;
        private String hygiene;
        private String finalStatus;
        private String notes;
        private LocalDateTime createdAt;

        public QualityReport(int productID, int officerID,
                             double temperature, String appearance,
                             String hygiene, String finalStatus,
                             String notes, LocalDateTime createdAt) {
            this.productID = productID;
            this.officerID = officerID;
            this.temperature = temperature;
            this.appearance = appearance;
            this.hygiene = hygiene;
            this.finalStatus = finalStatus;
            this.notes = notes;
            this.createdAt = createdAt;
        }

        public int getProductID() { return productID; }

        public int getOfficerID() { return officerID; }

        public double getTemperature() { return temperature; }

        public String getAppearance() { return appearance; }

        public String getHygiene() { return hygiene; }

        public String getFinalStatus() { return finalStatus; }

        public String getNotes() { return notes; }

        public LocalDateTime getCreatedAt() { return createdAt;
        }
    }
}
