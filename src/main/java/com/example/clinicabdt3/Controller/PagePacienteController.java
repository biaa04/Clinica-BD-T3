package com.example.clinicabdt3.Controller;

import com.example.clinicabdt3.Model.DAO.PacienteDAO;
import com.example.clinicabdt3.Model.Database.DatabaseFactory;
import com.example.clinicabdt3.Model.Database.DatabaseSQLite;
import com.example.clinicabdt3.Model.Domain.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class PagePacienteController implements Initializable {

    @FXML
    private TableColumn<Paciente, String> columnPacienteCpf;

    @FXML
    private TableColumn<Paciente, String> columnPacienteNascimento;

    @FXML
    private TableColumn<Paciente, String> columnPacienteNome;

    @FXML
    private Button pacienteAlterarButton;

    @FXML
    private Button pacienteDeletarButton;

    @FXML
    private Button pacienteInserirButton;

    @FXML
    private TableView<Paciente> tableViewPaciente;
    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private List<Paciente> listPaciente;
    private ObservableList<Paciente> observableListPaciente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pacienteDAO.setConnection(connection);
        System.out.println("aaaaaaaaaaaaa");
        carregarTableViewPaciente();


    }

    public void carregarTableViewPaciente(){
        columnPacienteCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        columnPacienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnPacienteNascimento.setCellValueFactory(new PropertyValueFactory<>("data_nascimento"));

        listPaciente = pacienteDAO.listar();
        observableListPaciente = FXCollections.observableArrayList(listPaciente);
        tableViewPaciente.setItems(observableListPaciente);
    }

    @FXML
    public void handlePacienteInserir() throws IOException {
        Paciente paciente = new Paciente();
        showPacienteDialog(paciente);
        //precisa fazer isso

    }

    public void showPacienteDialog(Paciente paciente) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PacienteDialogController.class.getResource("/com/example/clinicabdt3/pacienteDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Cadastro Paciente");
        stage.setScene(scene);
        stage.showAndWait();

        return;
    }
}
