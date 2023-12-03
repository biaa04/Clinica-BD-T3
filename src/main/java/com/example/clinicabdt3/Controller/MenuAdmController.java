package com.example.clinicabdt3.Controller;

import com.example.clinicabdt3.Model.DAO.ConsultaDAO;
import com.example.clinicabdt3.Model.DAO.MedicoDAO;
import com.example.clinicabdt3.Model.DAO.PacienteDAO;
import com.example.clinicabdt3.Model.Database.DatabaseFactory;
import com.example.clinicabdt3.Model.Database.DatabaseSQLite;
import com.example.clinicabdt3.Model.Domain.Consulta;
import com.example.clinicabdt3.Model.Domain.Medico;
import com.example.clinicabdt3.Model.Domain.Paciente;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MenuAdmController implements Initializable {

    @FXML
    private Button logoutButton;

    @FXML
    TextField nomeTextField;

    @FXML
    TextField cpfTextField;

    @FXML
    TextField idadeTextField;

    @FXML
    private TableColumn<Consulta, LocalDate> columnConsultaData;

    @FXML
    private TableColumn<Consulta, Time> columnConsultaHorario;

    @FXML
    private TableColumn<Medico, String> columnConsultaMedico;

    @FXML
    private TableColumn<Paciente, String> columnConsultaPaciente;

    @FXML
    private ComboBox<String> comboBoxPaciente;

    @FXML
    private ComboBox<String> comboBoxMedico;

    @FXML
    private TableView<Consulta> tableViewConsulta;

    @FXML
    private DatePicker DPDataConsulta;

    @FXML
    private TextField txtHorarioConsulta;

    @FXML
    private TextField txtIDConsulta;

    @FXML
    private TextField destinationTextField;



    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();
    private List<Consulta> listConsulta;
    private ObservableList<Consulta> observableListConsulta;
    private List<String> listPaciente, listMedico;
    private ObservableList<String> observableListPaciente, observableListMedico;
    private final PacienteDAO pacienteDAO = new PacienteDAO();

    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final ConsultaDAO consultaDAO = new ConsultaDAO();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consultaDAO.setConnection(connection);
        pacienteDAO.setConnection(connection);
        medicoDAO.setConnection(connection);

        listPaciente = pacienteDAO.pegarPacienteConsulta();
        observableListPaciente = FXCollections.observableArrayList(listPaciente);
        comboBoxPaciente.setItems(observableListPaciente);

        listMedico = medicoDAO.pegarMedicoConsulta();
        observableListMedico = FXCollections.observableArrayList(listMedico);
        comboBoxMedico.setItems(observableListMedico);

        carregarTableViewConsulta();

    }

    @FXML
    public void handleConsultaInserir(ActionEvent event) {

        Consulta consulta = new Consulta();


        if (validarEntradaDeDados()){
            System.out.println("aqui1");
            consulta.setDataConsulta(DPDataConsulta.getValue());
            System.out.println("1");
            consulta.setHorario(txtHorarioConsulta.getText());
            System.out.println("2");
            consulta.setPaciente(comboBoxPaciente.getSelectionModel().getSelectedItem());
            System.out.println("3");
            consulta.setMedico(comboBoxMedico.getSelectionModel().getSelectedItem());
            System.out.println("4");
            int idPac = listPaciente.size();
            idPac++;
            consulta.setIdConsulta(idPac);
            System.out.println(idPac);
            System.out.println(consulta.getIdConsulta());
            System.out.println("aqui2");
            consultaDAO.inserir(consulta);
            System.out.println("aqui3");
            limparTextField();
            carregarTableViewConsulta();
        }

    }

    @FXML
    void handlePacienteEditar() {

        System.out.println(txtIDConsulta.getText());

        for( Consulta consulta : listConsulta){
            if (txtIDConsulta.getText().equals(String.valueOf(consulta.getIdConsulta()))){
                System.out.println(String.valueOf(consulta.getIdConsulta()));
                if (consulta != null){

                    consulta.setPaciente(comboBoxPaciente.getValue());
                    consulta.setMedico(comboBoxMedico.getValue());
                    consulta.setDataConsulta(DPDataConsulta.getValue());
                    consulta.setHorario(txtHorarioConsulta.getText());

                    consultaDAO.alterar(consulta);
                    carregarTableViewConsulta();
                    limparTextField();


                }else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Por favor, escolha uma consulta da Tabela!");
                    alert.show();
                }
            }
        }
    }

    @FXML
    public void handleConsultaExcluir(ActionEvent event) {
        for( Consulta consulta : listConsulta){
            if (txtIDConsulta.getText().equals(String.valueOf(consulta.getIdConsulta()))){

                if (consulta != null){
                    consultaDAO.remover(consulta);
                    carregarTableViewConsulta();
                    limparTextField();


                }else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Por favor, escolha uma consulta da Tabela!");
                    alert.show();

                }

            }
        }
    }

    @FXML
    public void pesquisarConsulta(javafx.scene.input.KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER){
            System.out.println("PESQUISANDO");


            columnConsultaPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
            columnConsultaMedico.setCellValueFactory(new PropertyValueFactory<>("medico"));
            columnConsultaData.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
            columnConsultaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

            listConsulta = consultaDAO.buscar(destinationTextField.getText());
            observableListConsulta= FXCollections.observableArrayList(listConsulta);
            tableViewConsulta.setItems(observableListConsulta);
        }
    }



    @FXML
    public void handleButtonTelaPaciente(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(PagePacienteController.class.getResource("/com/example/clinicabdt3/admPacientes.fxml"));
        String css = PagePacienteController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    void sair(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(LoginController.class.getResource("/com/example/clinicabdt3/login.fxml"));
        String css = PagePacienteController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void carregarTableViewConsulta() {
        columnConsultaData.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
        columnConsultaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        columnConsultaPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        columnConsultaMedico.setCellValueFactory(new PropertyValueFactory<>("medico"));

        listConsulta = consultaDAO.listar();
        observableListConsulta = FXCollections.observableArrayList(listConsulta);
        tableViewConsulta.setItems(observableListConsulta);

    }

    private boolean validarEntradaDeDados(){

        String erroMessage = "";

        if (DPDataConsulta.getValue() == null){
            erroMessage += "Data inválida!\n";
        }

        if (txtHorarioConsulta.getText() == null || txtHorarioConsulta.getText().length() == 0){
            erroMessage += "Horário inválido!\n";
        }

        if (comboBoxPaciente.getValue() == null){
            erroMessage += "Nome do paciente inválido!\n";
        }

        if (comboBoxMedico.getValue() == null){
            erroMessage += "Nome do médico inválido!\n";
        }

        if (erroMessage.length() == 0){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO NO CADASTRO DA CONSULTA");
            alert.setHeaderText("Campos inválidos, por favor corrija");
            alert.setContentText(erroMessage);
            alert.show();
            return false;
        }

    }

    public void selecionarLinhaConsulta(javafx.scene.input.MouseEvent mouseEvent) {

        Consulta consulta = tableViewConsulta.getSelectionModel().getSelectedItem();

        txtIDConsulta.setText(String.valueOf(consulta.getIdConsulta()));
        DPDataConsulta.setValue(consulta.getDataConsulta());
        txtHorarioConsulta.setText(consulta.getHorario());
        comboBoxPaciente.setValue(consulta.getPaciente());
        comboBoxMedico.setValue(consulta.getMedico());
        System.out.println("Selecionou a consulta");

    }

    public void limparTextField() {

        txtIDConsulta.setText(null);
        DPDataConsulta.setValue(null);
        txtHorarioConsulta.setText(null);
        comboBoxPaciente.setValue(null);
        comboBoxMedico.setValue(null);


    }


}