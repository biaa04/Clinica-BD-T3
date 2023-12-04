package com.example.clinicabdt3.Controller;

import com.example.clinicabdt3.Model.DAO.EspecialidadeDAO;
import com.example.clinicabdt3.Model.DAO.MedicoDAO;
import com.example.clinicabdt3.Model.DAO.PacienteDAO;
import com.example.clinicabdt3.Model.Database.DatabaseFactory;
import com.example.clinicabdt3.Model.Database.DatabaseSQLite;
import com.example.clinicabdt3.Model.Domain.Consulta;
import com.example.clinicabdt3.Model.Domain.Especialidade;
import com.example.clinicabdt3.Model.Domain.Medico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class AlterarDadosController implements Initializable {
    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField crmTextField;

    @FXML
    private ComboBox<String> espeialidadeComboBox;

    @FXML
    private PasswordField senhaPasswordField;

    @FXML
    private Button SalvarButton;

    private Stage dialogStage;

    private Medico medico;

    private boolean buttonConfirmarClicked = false;

    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();
    private List<Medico> listMedico;
    private ObservableList<String> observableListMedico;
    private final MedicoDAO medicoDAO = new MedicoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        medicoDAO.setConnection(connection);

        listMedico = medicoDAO.listar();
        //observableListMedico = FXCollections.observableArrayList(listMedico);
        //tableViewPaciente.setItems(observableListPaciente);

    }

    @FXML
    void editarMedico(ActionEvent event) {

        if (validarEntradaDeDados()){
            medico.setNome(nomeTextField.getText());
            medico.setCRM(crmTextField.getText());
            medico.setEspecialidade(Integer.parseInt(espeialidadeComboBox.getValue()));
            medico.setSenha(senhaPasswordField.getText());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }

//        for( Medico medico : listMedico){
//            if (crmTextField.getText().equals(medico.getCRM())){
//                System.out.println(medico.getCRM());
//                if (medico != null){
//
//                    medico.setNome(nomeTextField.getText());
//                    medico.setCRM(crmTextField.getText());
//                    medico.setEspecialidade(Integer.parseInt(espeialidadeComboBox.getValue()));
//                    medico.setSenha(senhaPasswordField.getText());
//
//                    medicoDAO.alterar(medico);
//
//
//
//                }else {
//
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setContentText("Por favor, escolha uma consulta da Tabela!");
//                    alert.show();
//                }
//            }
//        }

    }

    private boolean validarEntradaDeDados(){
        String errorMessage = "";

        if (nomeTextField.getText() == null || nomeTextField.getText().length() == 0){
            errorMessage += "Nome inválido!\n";
        }
        if (crmTextField.getText() == null || crmTextField.getText().length() == 0){
            errorMessage += "CRM inválido!\n";
        }
        if (espeialidadeComboBox.getValue() == null || espeialidadeComboBox.getValue().length() == 0){
            errorMessage += "Especialidade inválido!\n";
        }
        if (senhaPasswordField.getText() == null || senhaPasswordField.getText().length() == 0){
            errorMessage += "Senha inválido!\n";
        }

        if (errorMessage.length() == 0){
            return true;
        }else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO NO ALTERAR MÉDICO");
            alert.setHeaderText("Campos inválidos, por favor, corrija");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
        this.nomeTextField.setText(medico.getNome());
        this.crmTextField.setText(medico.getCRM());
        //this.espeialidadeComboBox.setValue(medico.getEspecialidade());
        this.senhaPasswordField.setText(medico.getSenha());
    }
    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }
}
