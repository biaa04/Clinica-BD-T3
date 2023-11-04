package com.example.clinicabdt3.Controller;

import com.example.clinicabdt3.Model.Domain.Paciente;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PacienteDialogController implements Initializable {

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonConfimar;

    @FXML
    private DatePicker dPNascimento;

    @FXML
    private TextField txtCPFPaciente;

    @FXML
    private TextField txtIDPaciente;

    @FXML
    private TextField txtNomePaciente;
    private boolean buttonConfirmarClicked = false;
    private Paciente paciente;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean isButtonConfirmarClicked(){
        return buttonConfirmarClicked;
    }

    public void setButtonConfimarClicked(){
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    @FXML
    void handleButtonCancelar(ActionEvent event) {
        stage.close();
    }

    @FXML
    void handleButtonConfirmar(ActionEvent event) {
        if (validarEntradaDeDados()){
            paciente.setCPF(txtCPFPaciente.getText());
            paciente.setNome((txtNomePaciente.getText()));
            paciente.setData_nascimento(dPNascimento.getValue());
        }

    }

    private boolean validarEntradaDeDados(){

        String erroMessage = "";

        if (txtCPFPaciente.getText() == null || txtCPFPaciente.getText().length() == 0){
            erroMessage += "CPF inválido!\n";
        }
        if (txtNomePaciente.getText() == null || txtNomePaciente.getText().length() == 0){
            erroMessage += "Nome inválida!\n";
        }
        if (dPNascimento.getValue() == null){
            erroMessage += "Data inválida!\n";
        }

        if (erroMessage.length() == 0){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO NO CADASTRO DO PACIENTE");
            alert.setHeaderText("Campos inválidos, por favor corrija");
            alert.setContentText(erroMessage);
            alert.show();
            return false;
        }

    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
