package com.example.BranchManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Model class implementing the UML MonitorBranchSales
 *
 * Methods are moderate-level placeholders:
 * - viewSalesDashboard() returns a SalesSummary (mocked data)
 * - applySalesFilter(...) updates internal filters and returns a filtered SalesSummary
 * - exportSalesReport(format) generates a simple text file to simulate export
 */
public class MonitorBranchSales {

    // --- Attributes ---
    private int managerID;
    private int branchID;
    private String dateRange;
    private final Map<String, String> filters = new HashMap<>();

    // Constructor
    public MonitorBranchSales(int managerID, int branchID) {
        this.managerID = managerID;
        this.branchID = branchID;
    }

    // --- Methods (Goal Functions) ---

    /**
     * Returns an overall sales summary (mock implementation)
     */
    public SalesSummary viewSalesDashboard() {
        // DP: In a real app, fetch from DB. Here we return sample values.
        double revenue = 15240.75;
        int orders = 312;
        String bestProduct = "Chocolate Cake";
        return new SalesSummary(revenue, orders, bestProduct);
    }

    /**
     * Apply a single filter. This updates internal filters map.
     * Returns a SalesSummary that would be computed using the current filters.
     */
    public SalesSummary applySalesFilter(String filterType, String filterValue) {
        if (filterType != null && filterValue != null) {
            filters.put(filterType, filterValue);
        }

        // DP placeholder: compute filtered summary based on filters map.
        // For demonstration we adjust numbers slightly based on filters.
        double revenue = 12000.00;
        int orders = 240;
        String bestProduct = "Vanilla Cupcake";

        // Simple mock behavior
        if (filters.containsKey("category") && filters.get("category").equalsIgnoreCase("Cakes")) {
            revenue += 3000;
            bestProduct = "Chocolate Cake";
        }
        if (filters.containsKey("product") && filters.get("product").toLowerCase().contains("chocolate")) {
            revenue += 1500;
            orders += 30;
            bestProduct = "Chocolate Cake";
        }
        if (filters.containsKey("date")) {
            // could parse date for actual filtering; here we just change data slightly
            revenue *= 0.9;
            orders -= 10;
        }

        return new SalesSummary(revenue, orders, bestProduct);
    }

    /**
     * Clear filters (helper)
     */
    public void clearFilters() {
        filters.clear();
    }

    /**
     * Export the current report into a file. This is a placeholder that
     * writes a small text file named SalesReport.<format>.
     *
     * @param format "pdf" or "xlsx" (case-insensitive)
     * @return the created File object (path to file)
     * @throws IOException if file writing fails
     */
    public File exportSalesReport(String format) throws IOException {
        String ext = (format == null) ? "txt" : format.toLowerCase();
        // For demo, create a file in the user's temp directory
        File tmp = new File(System.getProperty("java.io.tmpdir"), "SalesReport." + ext);
        try (FileWriter fw = new FileWriter(tmp)) {
            SalesSummary summary = viewSalesDashboard();
            fw.write("Sales Report\n");
            fw.write("Manager ID: " + managerID + "\n");
            fw.write("Branch ID: " + branchID + "\n");
            fw.write("Filters: " + filters.toString() + "\n");
            fw.write(String.format("Total Revenue: %.2f\n", summary.getTotalRevenue()));
            fw.write("Total Orders: " + summary.getTotalOrders() + "\n");
            fw.write("Best Product: " + summary.getBestProduct() + "\n");
            fw.flush();
        }
        return tmp;
    }

    // --- Inner SalesSummary class ---
    public static class SalesSummary {
        private final double totalRevenue;
        private final int totalOrders;
        private final String bestProduct;

        public SalesSummary(double totalRevenue, int totalOrders, String bestProduct) {
            this.totalRevenue = totalRevenue;
            this.totalOrders = totalOrders;
            this.bestProduct = bestProduct;
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public int getTotalOrders() {
            return totalOrders;
        }

        public String getBestProduct() {
            return bestProduct;
        }
    }
}
