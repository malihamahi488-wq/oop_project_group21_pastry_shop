module com.example.mrbaker_group21_sec3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mrbaker_group21_sec3 to javafx.fxml;
    exports com.example.mrbaker_group21_sec3;
}