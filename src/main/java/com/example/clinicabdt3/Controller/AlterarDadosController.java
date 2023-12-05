package com.example.clinicabdt3.Controller;

import com.example.clinicabdt3.Model.DAO.EspecialidadeDAO;
import com.example.clinicabdt3.Model.DAO.MedicoDAO;
import com.example.clinicabdt3.Model.Database.DatabaseFactory;
import com.example.clinicabdt3.Model.Database.DatabaseSQLite;
import com.example.clinicabdt3.Model.Domain.Especialidade;
import com.example.clinicabdt3.Model.Domain.Medico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private boolean buttonCancenlarClicked = false;

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
    private final LoginController loginController = new LoginController();

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

        if (nomeTextField.getText() == null || nomeTextField.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (espeialidadeComboBox.getValue() == null || espeialidadeComboBox.getValue().length() == 0) {
            errorMessage += "Especialidade inválido!\n";
        }
        if (senhaPasswordField.getText() == null || senhaPasswordField.getText().length() == 0) {
            errorMessage += "Senha inválido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {

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

    public boolean isButtonCancelarClicked() {
        return buttonCancenlarClicked;
    }

    public String getCrmMed() {
        return crmMed;
    }

    public void setCrmMed(String crmMed) {
        this.crmMed = crmMed;
    }

    public void povoar() {
        List<Medico> medicos = new ArrayList<>();
        MedicoDAO med = new MedicoDAO();
        medicos = med.buscar(crmMed);
        medicooo = medicos.get(0);

        nomeTextField.setText(medicooo.getNome());
        crmTextField.setText(medicooo.getCRM());
        EspecialidadeDAO esp = new EspecialidadeDAO();
        String especialidade = esp.buscarNomePorId(medicooo.getEspecialidade());
        espeialidadeComboBox.setValue(especialidade);
        senhaPasswordField.setText(medicooo.getSenha());
    }

    @FXML
    void handleButtonExcluir(ActionEvent event) throws IOException {

        for (Medico medico : listMedico) {
            if (medico.getCRM().equals(loginController.getCrmMedico())) {

                if (medico != null) {
                    Alert alert  = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmação");
                    alert.setContentText("Tem certeza de que deseja excluir sua conta?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        redirecionarParaTelaDeLogin(event);
                        medicoDAO.remover(medico);
                        buttonCancenlarClicked = true;
                        dialogStage.close();

                    }
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("ERROR");
                    alert.show();

                }
            }
        }
    }

    private void redirecionarParaTelaDeLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(LoginController.class.getResource("/com/example/clinicabdt3/login.fxml"));
            String css = PagePacienteController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(css);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            // database.desconectar(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}