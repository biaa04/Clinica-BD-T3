package com.example.clinicabdt3.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import com.example.clinicabdt3.Model.DAO.ConsultaDAO;
import com.example.clinicabdt3.Model.DAO.MedicoDAO;
import com.example.clinicabdt3.Model.DAO.PacienteDAO;
import com.example.clinicabdt3.Model.DAO.EspecialidadeDAO;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class medico_menuController implements Initializable {

    @FXML
    private Text CRM;

    @FXML
    private TableColumn<Consulta, Date> columnConsultaData;

    @FXML
    private TextField destinationTextField;

    @FXML
    private Button editButton;

    @FXML
    private Text especialidade;

    @FXML
    private TableColumn<Consulta, Time> columnConsultaHorario;

    @FXML
    private Button logoutbutton;

    @FXML
    private Text nome;

    @FXML
    private Label medicoLabel;

    @FXML
    private Label labelCRM;

    @FXML
    private Label labelEspecialidade;

    @FXML
    private Label labelNome;

    @FXML
    private TableColumn<Paciente, String> columnConsultaPaciente;

    private String crm;

    @FXML
    private TableView<Consulta> tableviewMedicoConsulta;
    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final Medico medico = new Medico();
    private List<Medico> listMedico;
    private List<Consulta> listConsulta;
    private ObservableList<Consulta> observableListConsulta;

    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();
    private final LoginController loginController = new LoginController();
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final ConsultaDAO consultaDAO = new ConsultaDAO();

    private final EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        consultaDAO.setConnection(connection);
        medicoDAO.setConnection(connection);
        pacienteDAO.setConnection(connection);
        especialidadeDAO.setConnection(connection);
        listMedico = medicoDAO.listar();
        String CRM = loginController.getCrmMedico();
        
        for (Medico medico : listMedico) {
            if (medico.getCRM().equals(loginController.getCrmMedico())) {
                labelCRM.setText(medico.getCRM());
                labelNome.setText(medico.getNome());
                labelEspecialidade.setText(especialidadeDAO.buscarNomePorId(medico.getEspecialidade()));
                break;
            }
        }
       carregarTableViewConsulta(CRM);
    }


    @FXML
    void logout(ActionEvent event) throws IOException, SQLException {
        connection.close();
        Parent root = FXMLLoader.load(LoginController.class.getResource("/com/example/clinicabdt3/login.fxml"));
        String css = PagePacienteController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    void handleButtonEditar(ActionEvent event) throws IOException{

        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();

        // Set the location of the FXML file
        fxmlLoader.setLocation(AlterarDadosController.class.getResource("/com/example/clinicabdt3/alterar_dados.fxml"));
        Group root = new Group();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Alterar Dados do Médico");
        stage.setScene(scene);

        AlterarDadosController controller = fxmlLoader.getController();
        controller.setDialogStage(stage);
        stage.showAndWait();
        if (controller.isButtonConfirmarClicked()){
            stage.close();
        }


    }
    public void closeStage(){
        stage.close();
    }


    @FXML
    public void pesquisarConsulta(javafx.scene.input.KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {
            columnConsultaPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
            columnConsultaData.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
            columnConsultaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

            listConsulta = consultaDAO.buscar(destinationTextField.getText());
            observableListConsulta = FXCollections.observableArrayList(listConsulta);
            tableviewMedicoConsulta.setItems(observableListConsulta);
        }
    }

    private void carregarTableViewConsulta(String crmMedico) {
        System.out.println(crmMedico);
        columnConsultaData.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
        columnConsultaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        columnConsultaPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        listConsulta = consultaDAO.listar(crmMedico);
        System.out.println(crmMedico);
        for (Consulta consulta : listConsulta) {
            String cpf = consulta.getPaciente();
            List<Paciente> pacientes = pacienteDAO.buscar(cpf);
            Paciente p = pacientes.get(0);
            String nome = p.getNome();
            consulta.setPaciente(nome);
        }
        observableListConsulta = FXCollections.observableArrayList(listConsulta);
        tableviewMedicoConsulta.setItems(observableListConsulta);

    }

    public void setCrm(String crm) {
        this.crm = crm;
    }


}

