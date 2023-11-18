package com.example.clinicabdt3.Controller;

import com.example.clinicabdt3.Model.DAO.PacienteDAO;
import com.example.clinicabdt3.Model.Database.DatabaseFactory;
import com.example.clinicabdt3.Model.Database.DatabaseSQLite;
import com.example.clinicabdt3.Model.Domain.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.events.MouseEvent;

public class PagePacienteController implements Initializable {

    @FXML
    private TableColumn<Paciente, String> columnPacienteCpf;

    @FXML
    private TableColumn<Paciente, String> columnPacienteNascimento;

    @FXML
    private TableColumn<Paciente, String> columnPacienteNome;
    @FXML
    private TableColumn<Paciente, String> columnPacienteID;

    @FXML
    private Button pacienteAlterarButton;

    @FXML
    private Button pacienteDeletarButton;

    @FXML
    private Button pacienteInserirButton;

    @FXML
    private TableView<Paciente> tableViewPaciente;

    @FXML
    private Button logoutbutton;

    @FXML
    private TextField destinationTextField;

    @FXML
    private ImageView especialidadeImage;

    @FXML
    private ImageView especialidade;

    @FXML
    private Text CRM;

    @FXML
    private DatePicker dPNascimento;

    @FXML
    private TextField txtCPFPaciente;

    @FXML
    private TextField txtIDPaciente;

    @FXML
    private TextField txtNomePaciente;

    @FXML
    private AnchorPane anchorPaneEditar;
    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private List<Paciente> listPaciente;
    private ObservableList<Paciente> observableListPaciente;
    private Paciente paciente;
    private boolean editado = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pacienteDAO.setConnection(connection);
        System.out.println("aaaaaaaaaaaaa");
        carregarTableViewPaciente();



    }

    public void carregarTableViewPaciente() {
        columnPacienteID.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));
        columnPacienteCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));
        columnPacienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnPacienteNascimento.setCellValueFactory(new PropertyValueFactory<>("data_nascimento"));

        listPaciente = pacienteDAO.listar();
        observableListPaciente = FXCollections.observableArrayList(listPaciente);
        tableViewPaciente.setItems(observableListPaciente);
    }

    @FXML
    public void handlePacienteInserir() throws IOException {
        Paciente paciente = new Paciente();


        if (validarEntradaDeDados()){

            paciente.setCPF(txtCPFPaciente.getText());
            paciente.setNome((txtNomePaciente.getText()));
            paciente.setData_nascimento(dPNascimento.getValue());
            int idPac = listPaciente.size();
            idPac++;
            paciente.setIdPaciente(idPac);
            System.out.println(idPac);
            System.out.println(paciente.getIdPaciente());
            pacienteDAO.inserir(paciente);
            carregarTableViewPaciente();
        }

    }

    @FXML
    public void handlePacienteEditar() throws IOException{
        System.out.println("klnjncjsd");
        System.out.println(txtIDPaciente.getText());

        Paciente paciente = pacienteDAO.listar().get(Integer.parseInt(txtIDPaciente.getText())-1);
        System.out.println(paciente);

        if (paciente != null){

            paciente.setNome(txtNomePaciente.getText());
            paciente.setCPF(txtCPFPaciente.getText());
            paciente.setData_nascimento(dPNascimento.getValue());

            pacienteDAO.alterar(paciente);
            carregarTableViewPaciente();


        }else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um paciente da Tabela!");
            alert.show();
        }

    }

    @FXML
    public void handlePacienteExcluir() throws IOException{

        Paciente paciente = pacienteDAO.listar().get(Integer.parseInt(txtIDPaciente.getText())-1);

        if (paciente != null){

            pacienteDAO.remover(paciente);
            carregarTableViewPaciente();

        }else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um paciente da Tabela!");
            alert.show();

        }
    }


    @FXML
    void logout(ActionEvent event) {

    }

    private boolean validarEntradaDeDados(){

        String erroMessage = "";

        if (txtNomePaciente.getText() == null || txtNomePaciente.getText().length() == 0){
            erroMessage += "Nome inválida!\n";
        }

        if (txtCPFPaciente.getText() == null || txtCPFPaciente.getText().length() == 0){
            erroMessage += "CPF inválido!\n";
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

    @FXML
    public void limparTextField(javafx.scene.input.MouseEvent mouseEvent) {
        txtIDPaciente.setText(null);
        txtNomePaciente.setText(null);
        txtCPFPaciente.setText(null);
        dPNascimento.setValue(null);

    }


    public void selecionarLinhaPaciente(javafx.scene.input.MouseEvent mouseEvent) {

        Paciente paciente = tableViewPaciente.getSelectionModel().getSelectedItem();

        txtIDPaciente.setText(String.valueOf(paciente.getIdPaciente()));
        txtNomePaciente.setText(paciente.getNome());
        txtCPFPaciente.setText(paciente.getCPF());
        dPNascimento.setValue(paciente.getData_nascimento());
        System.out.println("Selecionou paciente");

    }

    public void pesquisarPaciente(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER){

        }
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public boolean isEditado() {
        return editado;
    }

    public void setEditado(boolean editado) {
        this.editado = editado;
    }

}
