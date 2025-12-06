package com.example.QualityControlOfficer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * User-8, Goal-8: SendReportToBranchManager
 *
 * Attributes:
 *  - officerID: int
 *  - reportID: int
 *  - managerID: int
 *
 * Methods:
 *  + selectReport(reportID): Report
 *  + sendReport(managerID): boolean
 *  + validateSending(): boolean
 */
public class SendReportToBranchManager {

    private int officerID;
    private int reportID;
    private int managerID;

    // Simulated report store
    private static final List<Report> REPORT_STORE = new ArrayList<>();

    static {
        REPORT_STORE.add(new Report(1,
                "Daily Quality Summary",
                "Summary of today's inspections and defect rate.",
                LocalDateTime.now().minusDays(1),
                "Ready"));
        REPORT_STORE.add(new Report(2,
                "Weekly Defective Products Report",
                "Overview of all defective product records for the week.",
                LocalDateTime.now().minusDays(3),
                "Ready"));
        REPORT_STORE.add(new Report(3,
                "Monthly Quality Dashboard Export",
                "Aggregated quality KPIs for the month.",
                LocalDateTime.now().minusDays(10),
                "Sent"));
    }

    // Helper: list of reports available to send
    public List<Report> viewAvailableReports() {
        return new ArrayList<>(REPORT_STORE);
    }

    // UML: + selectReport(reportID): Report
    public Report selectReport(int reportID) {
        this.reportID = reportID;
        for (Report r : REPORT_STORE) {
            if (r.getReportID() == reportID) {
                return r;
            }
        }
        return null;
    }

    // UML: + sendReport(managerID): boolean
    public boolean sendReport(int managerID) {
        this.managerID = managerID;

        if (!validateSending()) {
            return false;
        }

        Report r = findReportById(reportID);
        if (r == null) {
            return false;
        }

        // Simulate sending (e.g., email or internal messaging)
        r.setStatus("Sent");
        r.setLastSentToManagerID(managerID);
        r.setLastSentAt(LocalDateTime.now());

        // In a real system, persist and actually send
        System.out.println("Sending report ID " + r.getReportID() +
                " to manager " + managerID + " by officer " + officerID);

        return true;
    }

    // UML: + validateSending(): boolean
    public boolean validateSending() {
        if (reportID <= 0) return false;
        if (managerID <= 0) return false;

        Report r = findReportById(reportID);
        if (r == null) return false;

        // Example rule: can't send if already sent
        if ("Sent".equalsIgnoreCase(r.getStatus())) {
            return false;
        }

        return true;
    }

    private Report findReportById(int id) {
        for (Report r : REPORT_STORE) {
            if (r.getReportID() == id) {
                return r;
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

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    // Inner DTO class for Report
    public static class Report {
        private int reportID;
        private String title;
        private String summary;
        private LocalDateTime createdAt;
        private String status; // "Ready", "Sent", etc.
        private Integer lastSentToManagerID;
        private LocalDateTime lastSentAt;

        public Report(int reportID, String title, String summary,
                      LocalDateTime createdAt, String status) {
            this.reportID = reportID;
            this.title = title;
            this.summary = summary;
            this.createdAt = createdAt;
            this.status = status;
        }

        public int getReportID() { return reportID; }

        public String getTitle() { return title; }

        public String getSummary() { return summary; }

        public LocalDateTime getCreatedAt() { return createdAt; }

        public String getStatus() { return status; }

        public void setStatus(String status) { this.status = status; }

        public Integer getLastSentToManagerID() { return lastSentToManagerID; }

        public void setLastSentToManagerID(Integer lastSentToManagerID) {
            this.lastSentToManagerID = lastSentToManagerID;
        }

        public LocalDateTime getLastSentAt() { return lastSentAt; }

        public void setLastSentAt(LocalDateTime lastSentAt) {
            this.lastSentAt = lastSentAt;
        }

        public String getCreatedDateString() {
            if (createdAt == null) return "";
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return createdAt.format(fmt);
        }
    }
}
