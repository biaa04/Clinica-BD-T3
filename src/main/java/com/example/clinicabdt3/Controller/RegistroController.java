package com.example.clinicabdt3.Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {

    @FXML
    private TextField TextFieldNome;

    @FXML
    private Button RegistroButton;

    @FXML
    private PasswordField TextFieldSenha;

    @FXML
    private TextField TextFieldEspecialidade;

    @FXML
    private TextField TextFieldCRM;

    @FXML
    private Button LoginButton;

    String nome;
    String senha;
    String especialidade;
    String crm;

    public void registrar(ActionEvent e) throws IOExcepition {
        nome = TextFieldNome.getText();
        senha = TextFieldSenha.getText();
        especialidade = TextFieldEspecialidade.getText();
        crm = TextFieldCRM.getText();

        //implementar banco de dados
    }
}

