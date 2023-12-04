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
import java.sql.SQLException;
import java.util.ArrayList;
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
    private TextField senhaPasswordField;

    @FXML
    private Button SalvarButton;

    private Stage dialogStage;

    private Medico medico;

    private String crmMed;

    private Medico medicooo;

    private boolean buttonConfirmarClicked = false;

    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();
    private List<Medico> listMedico;
    private ObservableList<String> observableListMedico;
    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    private List<Especialidade> listEspecialidade;
    private List<String> listEspecialidadeNome;
    private ObservableList<String> observableListEspecialidade;
    static boolean clicked = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        medicoDAO.setConnection(connection);
        especialidadeDAO.setConnection(connection);
        listMedico = medicoDAO.listar();
        listEspecialidade = especialidadeDAO.listar();
        listEspecialidadeNome = especialidadeDAO.pegarEspecialidade();
        observableListEspecialidade = FXCollections.observableArrayList(listEspecialidadeNome);
        espeialidadeComboBox.setItems(observableListEspecialidade);

    }

    @FXML
    void editarMedico(ActionEvent event) {

        Medico medico = new Medico();
        Especialidade especialidade = new Especialidade();

        if (validarEntradaDeDados()) {
            medico.setNome(nomeTextField.getText());
            medico.setSenha(senhaPasswordField.getText());
            String CRM = loginController.getCrmMedico();
            medico.setCRM(CRM);

            especialidade.setNome(espeialidadeComboBox.getSelectionModel().getSelectedItem());

            for (Especialidade esp : listEspecialidade) {
                if (especialidade.getNome().equals(esp.getNome())) {
                    medico.setEspecialidade(esp.getId());
                    System.out.println("setando especialidade");
                    medicoDAO.alterar(medico);
                    buttonConfirmarClicked = true;
                    dialogStage.close();
                }
            }
        }


    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (nomeTextField.getText() == null || nomeTextField.getText().length() == 0){
            errorMessage += "Nome inválido!\n";
        }
        if (espeialidadeComboBox.getValue() == null || espeialidadeComboBox.getValue().length() == 0) {
            errorMessage += "Especialidade inválido!\n";
        }
        if (senhaPasswordField.getText() == null || senhaPasswordField.getText().length() == 0) {
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
