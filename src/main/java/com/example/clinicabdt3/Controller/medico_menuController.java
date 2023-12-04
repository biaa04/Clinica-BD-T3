package com.example.clinicabdt3.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consultaDAO.setConnection(connection);
        medicoDAO.setConnection(connection);
        pacienteDAO.setConnection(connection);
        especialidadeDAO.setConnection(connection);

        listMedico = medicoDAO.listar();
        for (Medico medico : listMedico) {
            if (medico.getCRM().equals(loginController.getCrmMedico())) {
                System.out.println(loginController.getCrmMedico());
                System.out.println(medico.getCRM());
                labelCRM.setText(medico.getCRM());
                labelNome.setText(medico.getNome());
                labelEspecialidade.setText(especialidadeDAO.buscarNomePorId(medico.getEspecialidade()));
                break;
            }
        }
       carregarTableViewConsulta();
    }


    @FXML
    void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(LoginController.class.getResource("/com/example/clinicabdt3/login.fxml"));
        String css = PagePacienteController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        //database.desconectar(connection);
    }

//    void editarDados(ActionEvent event) throws IOException {
//        System.out.println("oi");
//        Parent root = FXMLLoader.load(AlterarDadosController.class.getResource("/com/example/clinicabdt3/alterar_dados.fxml"));
//        System.out.println("oi");
//        String css = AlterarDadosController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(css);
//
//        Stage stage = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.centerOnScreen();
//        stage.show();
//    }


    @FXML
    void handleButtonEditar(ActionEvent event) throws IOException {

        // Medico medico = tableviewMedicoConsulta.getSelectionModel().getSelectedItem();
        for (Medico medico : listMedico) {
            if (medico.getCRM().equals(loginController.getCrmMedico())){
                showAnchorPaneCadastrosClientesDialog(medico);
                medicoDAO.alterar(medico);
            }
        }

//        if (medico != null) {
//
//            boolean buttonConfirmarClicked = showAnchorPaneCadastrosClientesDialog(medico);
//
//            if (buttonConfirmarClicked) {
//                medicoDAO.alterar(medico);
//            }
//            System.out.println("Fim Alterar");
//
//        } else {
//
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Por favor, escolha um médico da Tabela!");
//            alert.show();
//
//        }
        System.out.println("fim Alterar2");

    }

    public void showAnchorPaneCadastrosClientesDialog(Medico medico) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AlterarDadosController.class.getResource("com/example/clinicabdt3/alterar_dados.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Médico");
        stage.setScene(scene);
        stage.showAndWait();
        System.out.println("inicio");

        FXMLLoader loader = new FXMLLoader();
        System.out.println(loader);
        loader.setLocation(AlterarDadosController.class.getResource("/com/example/clinicabdt3/alterar_dados.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

//
//        FXMLLoader loader = new FXMLLoader();
//        System.out.println(loader);
//        loader.setLocation(AlterarDadosController.class.getResource("/com/example/clinicabdt3/alterar_dados.fxml"));
//        System.out.println("kkkkkk");
//        AnchorPane page = (AnchorPane) loader.load();
//        System.out.println("kkkkkk");
//        // pega a tela e bota na variavel page em vez de ter só o endereço
//
//        //Criando um estágio de diálogo (Stage Dialog)
//        Stage dialogStage = new Stage();
//        dialogStage.setTitle("Alterar Dados Médico");
//        Scene scene = new Scene(page);
//        dialogStage.setScene(scene);
//
//        //Setando o cliente no Controller
//        AlterarDadosController controller = loader.getController();
//        controller.setDialogStage(dialogStage);
//        controller.setMedico(medico);
//
//        //Mostra o dialog e espera até que o usuário o feche
//        dialogStage.showAndWait();
//
//        return controller.isButtonConfirmarClicked();
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
                        System.out.println("ooooo1");
                        redirecionarParaTelaDeLogin(event);
                        medicoDAO.remover(medico);
                        System.out.println("ooooo2");
                    }
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("ERROR");
                    alert.show();

                }
            }
        }
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

    private void carregarTableViewConsulta() {
        columnConsultaData.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
        columnConsultaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        columnConsultaPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));

        listConsulta = consultaDAO.listar();
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
