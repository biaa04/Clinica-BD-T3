package com.example.clinicabdt3.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class medico_menuController {

    @FXML
    private Text CRM;

    @FXML
    private TextField destinationTextField;

    @FXML
    private Text especialidade;

    @FXML
    private ImageView especialidade1;

    @FXML
    private Button logoutbutton;

    @FXML
    private Button editButton;

    @FXML
    private Text nome;

    @FXML
    void logout(ActionEvent event) throws IOException {
        System.out.println("oi");
        Parent root = FXMLLoader.load(LoginController.class.getResource("/com/example/clinicabdt3/login.fxml"));
        System.out.println("oi");
        String css = PagePacienteController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    void editarDados(ActionEvent event) throws IOException {
        System.out.println("oi");
        Parent root = FXMLLoader.load(LoginController.class.getResource("/com/example/clinicabdt3/login.fxml"));
        System.out.println("oi");
        String css = PagePacienteController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
