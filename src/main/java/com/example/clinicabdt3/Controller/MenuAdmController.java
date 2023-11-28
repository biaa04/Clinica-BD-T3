package com.example.clinicabdt3.Controller;

import com.example.clinicabdt3.Model.DAO.ConsultaDAO;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
    private ComboBox<String> comboBoxPaciente;

    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();
    private List<String> listPaciente;
    private ObservableList<String> observableListPaciente;
    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final ConsultaDAO consultaDAO = new ConsultaDAO();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        consultaDAO.setConnection(connection);
        pacienteDAO.setConnection(connection);

        listPaciente = pacienteDAO.pegarPacienteConsulta();
        observableListPaciente = FXCollections.observableArrayList(listPaciente);
        comboBoxPaciente.setItems(observableListPaciente);
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
}