package com.example.QualityControlOfficer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * User-8, Goal-6: GenerateQualityReports
 *
 * Attributes:
 *  - officerID: int
 *  - dateRange: String
 *  - productType: String
 *
 * Methods:
 *  + generateReport(dateRange, productType): QualityReport
 *  + downloadReport(reportID): File
 */
public class GenerateQualityReports {

    private int officerID;
    private String dateRange;
    private String productType;

    // Hold generated reports by ID
    private static final Map<Integer, QualityReport> REPORT_STORE = new HashMap<>();
    private static int NEXT_REPORT_ID = 1;

    // UML: + generateReport(dateRange, productType): QualityReport
    public QualityReport generateReport(String dateRange, String productType) {
        this.dateRange = dateRange;
        this.productType = productType;

        // Simulate calculating statistics from DB
        Random random = new Random(Objects.hash(dateRange, productType));
        int totalChecked = 50 + random.nextInt(200); // 50–249
        int totalFailed = random.nextInt(Math.max(1, totalChecked / 3));
        int totalPassed = totalChecked - totalFailed;
        double defectRate = totalChecked == 0 ? 0.0 : (totalFailed * 100.0 / totalChecked);

        QualityReport report = new QualityReport();
        report.setReportID(NEXT_REPORT_ID++);
        report.setOfficerID(officerID);
        report.setDateRange(dateRange);
        report.setProductType(productType);
        report.setTotalProductsChecked(totalChecked);
        report.setTotalPassed(totalPassed);
        report.setTotalFailed(totalFailed);
        report.setDefectRatePercent(defectRate);

        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("High-Risk Batches", random.nextInt(5));
        extra.put("Average Inspection Time (min)", 5 + random.nextInt(20));
        extra.put("Most Common Defect", "Packaging Damage");
        report.setExtraMetrics(extra);

        REPORT_STORE.put(report.getReportID(), report);

        return report;
    }

    // UML: + downloadReport(reportID): File
    public File downloadReport(int reportID) {
        QualityReport report = REPORT_STORE.get(reportID);
        if (report == null) {
            return null;
        }

        // Simple CSV/text file as the downloaded report
        File file = new File("quality_report_" + reportID + ".txt");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Quality Report\n");
            fw.write("Report ID: " + report.getReportID() + "\n");
            fw.write("Officer ID: " + report.getOfficerID() + "\n");
            fw.write("Date Range: " + report.getDateRange() + "\n");
            fw.write("Product Type: " + report.getProductType() + "\n");
            fw.write("Total Products Checked: " + report.getTotalProductsChecked() + "\n");
            fw.write("Total Passed: " + report.getTotalPassed() + "\n");
            fw.write("Total Failed: " + report.getTotalFailed() + "\n");
            fw.write(String.format("Defect Rate: %.2f%%\n", report.getDefectRatePercent()));
            fw.write("\nAdditional Metrics:\n");
            for (Map.Entry<String, Object> e : report.getExtraMetrics().entrySet()) {
                fw.write(e.getKey() + ": " + e.getValue() + "\n");
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters & setters

    public int getOfficerID() {
        return officerID;
    }

    public void setOfficerID(int officerID) {
        this.officerID = officerID;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    // Inner classes / DTOs

    public static class QualityReport {
        private int reportID;
        private int officerID;
        private String dateRange;
        private String productType;
        private int totalProductsChecked;
        private int totalPassed;
        private int totalFailed;
        private double defectRatePercent;
        private Map<String, Object> extraMetrics = new LinkedHashMap<>();

        public int getReportID() {
            return reportID;
        }

        public void setReportID(int reportID) {
            this.reportID = reportID;
        }

        public int getOfficerID() {
            return officerID;
        }

        public void setOfficerID(int officerID) {
            this.officerID = officerID;
        }

        public String getDateRange() {
            return dateRange;
        }

        public void setDateRange(String dateRange) {
            this.dateRange = dateRange;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public int getTotalProductsChecked() {
            return totalProductsChecked;
        }

        public void setTotalProductsChecked(int totalProductsChecked) {
            this.totalProductsChecked = totalProductsChecked;
        }

        public int getTotalPassed() {
            return totalPassed;
        }

        public void setTotalPassed(int totalPassed) {
            this.totalPassed = totalPassed;
        }

        public int getTotalFailed() {
            return totalFailed;
        }

        public void setTotalFailed(int totalFailed) {
            this.totalFailed = totalFailed;
        }

        public double getDefectRatePercent() {
            return defectRatePercent;
        }

        public void setDefectRatePercent(double defectRatePercent) {
            this.defectRatePercent = defectRatePercent;
        }

        public Map<String, Object> getExtraMetrics() {
            return extraMetrics;
        }

        public void setExtraMetrics(Map<String, Object> extraMetrics) {
            this.extraMetrics = extraMetrics;
        }
    }

    // For table display: metric name + value
    public static class ReportMetric {
        private String metric;
        private String value;

        public ReportMetric(String metric, String value) {
            this.metric = metric;
            this.value = value;
        }

        public String getMetric() {
            return metric;
        }

        public String getValue() {
            return value;
        }
    }
}
