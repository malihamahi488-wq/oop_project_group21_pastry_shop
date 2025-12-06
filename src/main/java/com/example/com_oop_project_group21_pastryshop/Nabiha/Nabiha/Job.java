package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

import java.time.LocalDate;

public class Job {
    private String designation,educationalQualification,description;
    private LocalDate startingDate,endingDate;


    public Job() {
    }

    public Job(String designation, String educationalQualification, String description, LocalDate startingDate, LocalDate endingDate) {
        this.designation = designation;
        this.educationalQualification = educationalQualification;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEducationalQualification() {
        return educationalQualification;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    @Override
    public String toString() {
        return "JobPost{" +
                "designation='" + designation + '\'' +
                ", educationalQualification='" + educationalQualification + '\'' +
                ", description='" + description + '\'' +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                '}';
    }
}
