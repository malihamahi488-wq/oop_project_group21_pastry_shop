package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

import java.time.LocalDate;

public class SalarySheet extends Employee {
    private int salary;

    public SalarySheet(int salary) {
        this.salary = salary;
    }

    public SalarySheet(String employeeName, String employeeId, String designation, LocalDate DOB, LocalDate joiningDate, int salary) {
        super(employeeName, employeeId, designation, DOB, joiningDate);
        this.salary = salary;
    }

    public SalarySheet(String text, String text1, String text2, int salary) {
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "SalarySheet{" +
                "salary=" + salary +
                '}';
    }
}
