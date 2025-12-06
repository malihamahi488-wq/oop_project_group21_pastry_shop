package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

import java.time.LocalDate;

public class Employee {
    private String employeeName,EmployeeId,Designation;
    private LocalDate DOB,JoiningDate;

    public Employee() {
    }

    public Employee(String employeeName, String employeeId, String designation, LocalDate DOB, LocalDate joiningDate) {
        this.employeeName = employeeName;
        EmployeeId = employeeId;
        Designation = designation;
        this.DOB = DOB;
        JoiningDate = joiningDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public String getDesignation() {
        return Designation;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public LocalDate getJoiningDate() {
        return JoiningDate;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        JoiningDate = joiningDate;
    }

    @Override
    public String toString() {
        return "EmployeeList{" +
                "employeeName='" + employeeName + '\'' +
                ", EmployeeId='" + EmployeeId + '\'' +
                ", Designation='" + Designation + '\'' +
                ", DOB=" + DOB +
                ", JoiningDate=" + JoiningDate +
                '}';
    }
}
