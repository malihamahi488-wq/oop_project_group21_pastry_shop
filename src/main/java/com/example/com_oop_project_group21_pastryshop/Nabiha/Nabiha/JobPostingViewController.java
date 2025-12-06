package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JobPostingViewController
{
    @javafx.fxml.FXML
    private TextField EducationQualificationTextField;
    @javafx.fxml.FXML
    private TextField DesignationTextField;
    @javafx.fxml.FXML
    private DatePicker StartingDateDatePicker;
    @javafx.fxml.FXML
    private DatePicker EndingDateDatePicker;
    @javafx.fxml.FXML
    private TextArea JobDescriptionTextArea;
    @javafx.fxml.FXML
    private Label jobPostLabel;



    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void JobPostButtonOnAction(ActionEvent actionEvent) {
        String designation = DesignationTextField.getText();
        String qualification = EducationQualificationTextField.getText();

        String startDate = StartingDateDatePicker.getValue() != null ? StartingDateDatePicker.getValue().toString() : "N/A";
        String endDate = EndingDateDatePicker.getValue() != null ? EndingDateDatePicker.getValue().toString() : "N/A";

        String description = JobDescriptionTextArea.getText();


        String jobCircularText = "--- New Job Circular ---\n" +
                "Designation: " + designation + "\n" +
                "Educational Qualification: " + qualification + "\n" +
                "Application Start Date: " + startDate + "\n" +
                "Application End Date: " + endDate + "\n" +
                "Description & Requirements:\n" + description + "\n" +
                "--------------------------";


        jobPostLabel.setText(jobCircularText);


        DesignationTextField.clear();
        EducationQualificationTextField.clear();
        JobDescriptionTextArea.clear();
        StartingDateDatePicker.setValue(null);
        EndingDateDatePicker.setValue(null);
    }
}