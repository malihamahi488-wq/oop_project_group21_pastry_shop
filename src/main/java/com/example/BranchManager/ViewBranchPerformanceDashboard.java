package com.example.BranchManager;

import java.time.LocalDate;
import java.util.*;

/**
 * Goal-7 Model: ViewBranchPerformanceDashboard
 *
 * Attributes:
 *  - managerID: int
 *  - filters: Map<String,String>
 *
 * Methods:
 *  + viewDashboard(): PerformanceSummary
 *  + applyFilter(type: String, value: String): PerformanceSummary
 */
public class ViewBranchPerformanceDashboard {

    private int managerID;
    private Map<String, String> filters = new HashMap<>();

    // UML: + viewDashboard(): PerformanceSummary
    public PerformanceSummary viewDashboard() {
        // If no filters set, use some defaults
        if (!filters.containsKey("period")) {
            filters.put("period", "This Month");
        }
        if (!filters.containsKey("metric")) {
            filters.put("metric", "Revenue");
        }
        return buildSummary();
    }

    // UML: + applyFilter(type: String, value: String): PerformanceSummary
    public PerformanceSummary applyFilter(String type, String value) {
        if (type != null && value != null) {
            filters.put(type, value);
        }
        return buildSummary();
    }

    /**
     * Build a PerformanceSummary based on current filters.
     * Right now this is dummy data; replace with real queries later.
     */
    private PerformanceSummary buildSummary() {
        String period = filters.getOrDefault("period", "This Month");
        // String metric = filters.getOrDefault("metric", "Revenue"); // not strictly needed here

        // Decide x-axis labels based on period
        List<String> labels = new ArrayList<>();
        switch (period) {
            case "Today":
                labels = Arrays.asList("8 AM", "10 AM", "12 PM", "2 PM", "4 PM", "6 PM");
                break;
            case "This Week":
                labels = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
                break;
            case "This Year":
                labels = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
                break;
            case "This Month":
            default:
                labels = Arrays.asList("Week 1", "Week 2", "Week 3", "Week 4");
                break;
        }

        // Dummy data generation (could vary with period)
        List<MetricPoint> revenue = new ArrayList<>();
        List<MetricPoint> orders = new ArrayList<>();
        List<MetricPoint> rating = new ArrayList<>();

        Random random = new Random(Objects.hash(period, LocalDate.now().getDayOfYear()));

        for (String label : labels) {
            double rev = 1000 + random.nextInt(4000); // 1000–5000
            double ord = 50 + random.nextInt(150);    // 50–200
            double rat = 3.5 + (random.nextDouble() * 1.5); // 3.5–5.0

            revenue.add(new MetricPoint(label, rev));
            orders.add(new MetricPoint(label, ord));
            rating.add(new MetricPoint(label, Math.round(rat * 10.0) / 10.0));
        }

        // Dummy category share data
        Map<String, Double> categoryShare = new LinkedHashMap<>();
        categoryShare.put("Cakes", 40.0);
        categoryShare.put("Pastries", 25.0);
        categoryShare.put("Drinks", 20.0);
        categoryShare.put("Snacks", 15.0);

        PerformanceSummary summary = new PerformanceSummary();
        summary.setRevenueSeries(revenue);
        summary.setOrdersSeries(orders);
        summary.setRatingSeries(rating);
        summary.setCategoryShare(categoryShare);
        summary.setFilters(new HashMap<>(filters));

        return summary;
    }

    // Getters & setters
    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    // Helper types

    public static class MetricPoint {
        private String label;
        private double value;

        public MetricPoint(String label, double value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public double getValue() {
            return value;
        }
    }

    public static class PerformanceSummary {
        private List<MetricPoint> revenueSeries;
        private List<MetricPoint> ordersSeries;
        private List<MetricPoint> ratingSeries;
        private Map<String, Double> categoryShare;
        private Map<String, String> filters;

        public List<MetricPoint> getRevenueSeries() {
            return revenueSeries;
        }

        public void setRevenueSeries(List<MetricPoint> revenueSeries) {
            this.revenueSeries = revenueSeries;
        }

        public List<MetricPoint> getOrdersSeries() {
            return ordersSeries;
        }

        public void setOrdersSeries(List<MetricPoint> ordersSeries) {
            this.ordersSeries = ordersSeries;
        }

        public List<MetricPoint> getRatingSeries() {
            return ratingSeries;
        }

        public void setRatingSeries(List<MetricPoint> ratingSeries) {
            this.ratingSeries = ratingSeries;
        }

        public Map<String, Double> getCategoryShare() {
            return categoryShare;
        }

        public void setCategoryShare(Map<String, Double> categoryShare) {
            this.categoryShare = categoryShare;
        }

        public Map<String, String> getFilters() {
            return filters;
        }

        public void setFilters(Map<String, String> filters) {
            this.filters = filters;
        }
    }
}
