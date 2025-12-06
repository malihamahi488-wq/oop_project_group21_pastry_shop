package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

public class MonthlyIncome {
    private String  monthName,year;
    private int income;

    public MonthlyIncome() {
    }

    public MonthlyIncome(String monthName, String year, int income) {
        this.monthName = monthName;
        this.year = year;
        this.income = income;
    }

    public String getMonthName() {
        return monthName;
    }

    public String getYear() {
        return year;
    }

    public int getIncome() {
        return income;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "MonthlyIncome{" +
                "monthName='" + monthName + '\'' +
                ", year='" + year + '\'' +
                ", income=" + income +
                '}';
    }
}
