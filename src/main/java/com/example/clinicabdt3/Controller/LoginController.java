package com.example.clinicabdt3.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField senhaTextField;

    @FXML
    private TextField CPFTextField;

    @FXML
    private Button cadastrarButton;

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void login(ActionEvent event) throws IOException {

        //Implementar Banco de Dados

        FXMLLoader loader = new FXMLLoader(getClass().getResource("paciente_menu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void switchToSignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registro.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
