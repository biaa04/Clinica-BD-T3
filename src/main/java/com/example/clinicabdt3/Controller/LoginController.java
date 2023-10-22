package com.example.clinicabdt3.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class LoginController {
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
    private Usuario user;

    @FXML
    void login(ActionEvent event) throws IOException
    {
        boolean existingAccount = false;

    }

    @FXML
    void switchToSignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("registro.fxml"))));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
