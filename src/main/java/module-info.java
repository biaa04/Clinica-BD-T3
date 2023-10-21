module com.example.clinicabdt3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.clinicabdt3 to javafx.fxml;
    exports com.example.clinicabdt3;
    exports com.example.clinicabdt3.Controller;
    opens com.example.clinicabdt3.Controller to javafx.fxml;
}