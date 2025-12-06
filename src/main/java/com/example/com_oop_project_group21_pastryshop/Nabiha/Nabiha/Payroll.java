package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

public class Payroll {
    private String designation;
    private int salary;

    public Payroll() {
    }

    public Payroll(String designation, int salary) {
        this.designation = designation;
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public int getSalary() {
        return salary;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "designation='" + designation + '\'' +
                ", salary=" + salary +
                '}';
    }
}
