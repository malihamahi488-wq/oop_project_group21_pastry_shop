package com.example.BranchManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Model class for TrackDailySalesSummary (UML)
 *
 * Attributes:
 * - managerID: int
 * - date: LocalDate (used in methods)
 *
 * Methods:
 * + viewDailySummary(date): DailySummary
 * + exportSummary(date, format): File
 *
 * This class uses mocked data for demonstration. Replace with DB queries in production.
 */
public class TrackDailySalesSummary {

    private final int managerID;

    // mock store of date -> daily data
    private final Map<LocalDate, DailySummary> store = new HashMap<>();

    public TrackDailySalesSummary(int managerID) {
        this.managerID = managerID;
        seedMockData();
    }

    /**
     * Return daily summary for the given date (mock implementation)
     */
    public DailySummary viewDailySummary(LocalDate date) {
        if (date == null) date = LocalDate.now();
        // In real app, query DB for date
        return store.getOrDefault(date, createEmptySummary(date));
    }

    /**
     * Export summary for date into a file. Returns created File.
     * Format: "pdf" or "xlsx" (simulated by extension; file content is plain text)
     */
    public File exportSummary(LocalDate date, String format) throws IOException {
        DailySummary s = viewDailySummary(date);
        String ext = format == null ? "txt" : format.toLowerCase();
        File out = new File(System.getProperty("java.io.tmpdir"), "DailySummary_" + date + "." + ext);
        try (FileWriter fw = new FileWriter(out)) {
            fw.write("Daily Sales Summary\n");
            fw.write("Manager ID: " + managerID + "\n");
            fw.write("Date: " + date + "\n");
            fw.write(String.format("Total Revenue: %.2f\n", s.getTotalRevenue()));
            fw.write("Items Sold: " + s.getItemsSold() + "\n");
            fw.write("Best Sellers:\n");
            for (String p : s.getBestSellers()) {
                fw.write("- " + p + "\n");
            }
            fw.flush();
        }
        return out;
    }

    // Helper: create an empty summary
    private DailySummary createEmptySummary(LocalDate date) {
        return new DailySummary(date, 0.0, 0, Collections.emptyList());
    }

    // Seed some mock data
    private void seedMockData() {
        LocalDate today = LocalDate.now();
        store.put(today, new DailySummary(today, 1540.75, 180, Arrays.asList("Chocolate Cake", "Vanilla Cupcake", "Blueberry Muffin")));
        store.put(today.minusDays(1), new DailySummary(today.minusDays(1), 1230.00, 150, Arrays.asList("Sourdough Bread", "Chocolate Cake", "Croissant")));
    }

    // Inner DailySummary class
    public static class DailySummary {
        private final LocalDate date;
        private final double totalRevenue;
        private final int itemsSold;
        private final List<String> bestSellers;

        public DailySummary(LocalDate date, double totalRevenue, int itemsSold, List<String> bestSellers) {
            this.date = date;
            this.totalRevenue = totalRevenue;
            this.itemsSold = itemsSold;
            this.bestSellers = new ArrayList<>(bestSellers);
        }

        public LocalDate getDate() { return date; }
        public double getTotalRevenue() { return totalRevenue; }
        public int getItemsSold() { return itemsSold; }
        public List<String> getBestSellers() { return Collections.unmodifiableList(bestSellers); }
    }
}
