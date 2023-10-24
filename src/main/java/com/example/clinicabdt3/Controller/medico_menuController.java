package com.example.clinicabdt3.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class medico_menuController {
    @FXML
    private Text especialidade;

    @FXML
    private Text CRM;

    @FXML
    private Text nome;

    @FXML
    private Button logoutbutton;

    @FXML
    void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("login.fxml")));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}


