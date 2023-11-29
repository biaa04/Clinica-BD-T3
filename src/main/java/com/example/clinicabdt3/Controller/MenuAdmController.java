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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MenuAdmController implements Initializable {
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
            carregarTableViewConsulta();
        }

    }

    @FXML
    public void handleConsultaExcluir(ActionEvent event) {
        System.out.println("22222222222");
        Consulta consulta = consultaDAO.listar().get(Integer.parseInt(txtIDConsulta.getText())-1);
        System.out.println("22222222222");
        if (consulta != null){
            System.out.println("11111111");
            consultaDAO.remover(consulta);
            System.out.println("11111111");
            carregarTableViewConsulta();
            System.out.println("11111111");

        }else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma consulta da Tabela!");
            alert.show();

        }
        System.out.println("22222222222");
    }

    @FXML
    public void handleButtonTelaPaciente(ActionEvent event) throws IOException {
        System.out.println("oi");
        Parent root = FXMLLoader.load(PagePacienteController.class.getResource("/com/example/clinicabdt3/admPacientes.fxml"));
        System.out.println("oi");
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
}