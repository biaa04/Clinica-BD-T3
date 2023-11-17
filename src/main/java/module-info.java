module com.example.clinicabdt3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.clinicabdt3 to javafx.fxml;
    exports com.example.clinicabdt3;
    exports com.example.clinicabdt3.Controller;
    exports com.example.clinicabdt3.Model.Domain;
    exports com.example.clinicabdt3.Model.Database;

    opens com.example.clinicabdt3.Controller to javafx.fxml;
    //exports com.example.clinicabdt3.Model.DAO;

//opens com.example.clinicabdt3 to javafx.fxml;
 //   exports com.example.clinicabdt3.Model.Database;

    //opens com.example.clinicabdt3.Model.Database to javafx.fxml;
    //exports com.example.clinicabdt3.Model.Domain;

    //opens com.example.clinicabdt3.Model.Domain to javafx.fxml;


}