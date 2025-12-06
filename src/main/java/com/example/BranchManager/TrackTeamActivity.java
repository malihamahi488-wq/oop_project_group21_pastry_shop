package com.example.BranchManager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Model for TrackTeamActivity (UML)
 *
 * Methods:
 * - viewTeamActivity(): returns a list of ActivitySummary (mock data)
 * - viewEmployeeDetails(empID): returns ActivityDetails
 * - exportTeamActivityReport(format): export mocked report file
 *
 * Also provides simple search and per-employee export helper.
 */
public class TrackTeamActivity {

    private final int managerID;

    // In-memory mock datastore
    private final Map<String, ActivityDetails> detailsMap = new HashMap<>();

    public TrackTeamActivity(int managerID) {
        this.managerID = managerID;
        seedMockData();
    }

    // --- UML Methods ---

    /**
     * Returns a list of ActivitySummary (for TableView)
     */
    public List<ActivitySummary> viewTeamActivity() {
        List<ActivitySummary> out = new ArrayList<>();
        for (ActivityDetails d : detailsMap.values()) {
            out.add(new ActivitySummary(d.getEmployeeId(), d.getName(), d.getMostRecentActivity(), String.format("%.1f", d.getPerformanceRating())));
        }
        // sort by name
        out.sort(Comparator.comparing(ActivitySummary::getName));
        return out;
    }

    /**
     * Get detailed info for one employee
     */
    public ActivityDetails viewEmployeeDetails(String empID) {
        return detailsMap.get(empID);
    }

    /**
     * Export team report (mock: writes a text file)
     */
    public File exportTeamActivityReport(String format) throws IOException {
        File f = new File(System.getProperty("java.io.tmpdir"), "TeamActivityReport." + (format == null ? "txt" : format));
        try (FileWriter fw = new FileWriter(f)) {
            fw.write("Team Activity Report\n");
            fw.write("Manager ID: " + managerID + "\n");
            fw.write("Generated: " + new Date() + "\n\n");
            for (ActivityDetails d : detailsMap.values()) {
                fw.write(String.format("%s | %s | Rating: %.1f | Last: %s\n", d.getEmployeeId(), d.getName(), d.getPerformanceRating(), d.getMostRecentActivity()));
            }
        }
        return f;
    }

    /**
     * Export per-employee report
     */
    public File exportTeamActivityReportForEmployee(String empID, String format) throws IOException {
        ActivityDetails d = detailsMap.get(empID);
        File f = new File(System.getProperty("java.io.tmpdir"), "ActivityReport_" + empID + "." + (format == null ? "txt" : format));
        try (FileWriter fw = new FileWriter(f)) {
            fw.write("Activity Report for " + d.getName() + " (" + d.getEmployeeId() + ")\n");
            fw.write("Manager ID: " + managerID + "\n");
            fw.write("Generated: " + new Date() + "\n\n");
            fw.write("Shift times: " + d.getShiftTimes() + "\n");
            fw.write("Orders handled: " + d.getOrdersHandled() + "\n");
            fw.write("Rating: " + d.getPerformanceRating() + "\n\n");
            fw.write("Recent activities:\n");
            for (String act : d.getRecentActivities()) fw.write("- " + act + "\n");
        }
        return f;
    }

    // search helper for controller (simple)
    public List<ActivitySummary> searchByNameOrId(String query) {
        query = query.toLowerCase().trim();
        List<ActivitySummary> out = new ArrayList<>();
        for (ActivityDetails d : detailsMap.values()) {
            if (d.getEmployeeId().toLowerCase().contains(query) || d.getName().toLowerCase().contains(query)) {
                out.add(new ActivitySummary(d.getEmployeeId(), d.getName(), d.getMostRecentActivity(), String.format("%.1f", d.getPerformanceRating())));
            }
        }
        return out;
    }

    // --- Mock data seeding ---
    private void seedMockData() {
        ActivityDetails a = new ActivityDetails("E101", "Arif Ahmed", "08:00-16:00", 42, 4.5, Arrays.asList("Handled 12 orders", "Logged shift start", "Assisted delivery"));
        ActivityDetails b = new ActivityDetails("E102", "Bipasha Roy", "09:00-17:00", 58, 4.8, Arrays.asList("Packaged 20 items", "Quality check", "Updated inventory"));
        ActivityDetails c = new ActivityDetails("E103", "Camille Khan", "10:00-18:00", 35, 4.2, Arrays.asList("Baked 50 muffins", "Customer assistance", "Shift end"));
        detailsMap.put(a.getEmployeeId(), a);
        detailsMap.put(b.getEmployeeId(), b);
        detailsMap.put(c.getEmployeeId(), c);
    }

    // --- Inner classes used by controller / TableView ---

    public static class ActivitySummary {
        private final StringProperty employeeId;
        private final StringProperty name;
        private final StringProperty lastActivity;
        private final StringProperty rating;

        public ActivitySummary(String employeeId, String name, String lastActivity, String rating) {
            this.employeeId = new SimpleStringProperty(employeeId);
            this.name = new SimpleStringProperty(name);
            this.lastActivity = new SimpleStringProperty(lastActivity);
            this.rating = new SimpleStringProperty(rating);
        }

        public String getEmployeeId() { return employeeId.get(); }
        public StringProperty employeeIdProperty() { return employeeId; }

        public String getName() { return name.get(); }
        public StringProperty nameProperty() { return name; }

        public String getLastActivity() { return lastActivity.get(); }
        public StringProperty lastActivityProperty() { return lastActivity; }

        public String getRating() { return rating.get(); }
        public StringProperty ratingProperty() { return rating; }
    }

    public static class ActivityDetails {
        private final String employeeId;
        private final String name;
        private final String shiftTimes;
        private final int ordersHandled;
        private final double performanceRating;
        private final List<String> recentActivities;

        public ActivityDetails(String employeeId, String name, String shiftTimes, int ordersHandled, double performanceRating, List<String> recentActivities) {
            this.employeeId = employeeId;
            this.name = name;
            this.shiftTimes = shiftTimes;
            this.ordersHandled = ordersHandled;
            this.performanceRating = performanceRating;
            this.recentActivities = new ArrayList<>(recentActivities);
        }

        public String getEmployeeId() { return employeeId; }
        public String getName() { return name; }
        public String getShiftTimes() { return shiftTimes; }
        public int getOrdersHandled() { return ordersHandled; }
        public double getPerformanceRating() { return performanceRating; }
        public List<String> getRecentActivities() { return Collections.unmodifiableList(recentActivities); }

        // convenience: return most recent activity short string
        public String getMostRecentActivity() {
            return recentActivities.isEmpty() ? "-" : recentActivities.get(0);
        }
    }
}
