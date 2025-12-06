package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

public class Expense {
    private String monthName,year;
    private int totalExpense;

    public Expense() {
    }

    public Expense(String monthName, String year, int totalExpense) {
        this.monthName = monthName;
        this.year = year;
        this.totalExpense = totalExpense;
    }

    public String getMonthName() {
        return monthName;
    }

    public String getYear() {
        return year;
    }

    public int getTotalExpense() {
        return totalExpense;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTotalExpense(int totalExpense) {
        this.totalExpense = totalExpense;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "monthName='" + monthName + '\'' +
                ", year='" + year + '\'' +
                ", totalExpense=" + totalExpense +
                '}';
    }
}
