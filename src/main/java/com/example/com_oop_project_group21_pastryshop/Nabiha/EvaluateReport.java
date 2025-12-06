package com.example.com_oop_project_group21_pastryshop.Nabiha;

public class EvaluateReport {
    private String  employeeName,Id, monthlyPerformance, designation;

    public EvaluateReport() {
    }

    public EvaluateReport(String employeeName, String id, String monthlyPerformance, String designation) {
        this.employeeName = employeeName;
        Id = id;
        this.monthlyPerformance = monthlyPerformance;
        this.designation = designation;
    }

    public EvaluateReport(String name, String id, String designation, String month, String rating) {
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setMonthlyPerformance(String monthlyPerformance) {
        this.monthlyPerformance = monthlyPerformance;
    }

    @Override
    public String toString() {
        return "EvaluateReport{" +
                "employeeName='" + employeeName + '\'' +
                ", Id='" + Id + '\'' +
                ", monthlyPerformance='" + monthlyPerformance + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
