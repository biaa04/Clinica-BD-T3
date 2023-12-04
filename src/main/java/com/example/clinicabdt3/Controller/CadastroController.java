package com.example.clinicabdt3.Controller;
import com.example.clinicabdt3.Model.DAO.EspecialidadeDAO;
import com.example.clinicabdt3.Model.DAO.MedicoDAO;
import com.example.clinicabdt3.Model.Database.DatabaseFactory;
import com.example.clinicabdt3.Model.Database.DatabaseSQLite;
import com.example.clinicabdt3.Model.Domain.Consulta;
import com.example.clinicabdt3.Model.Domain.Especialidade;
import com.example.clinicabdt3.Model.Domain.Medico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {

    @FXML
    private TextField TextFieldNome;

    @FXML
    private Button RegistroButton;

    @FXML
    private PasswordField TextFieldSenha;

    @FXML
    private TextField TextFieldCRM;

    @FXML
    private Button LoginButton;

    @FXML
    private ComboBox<String> comboBoxEspecialidade;

    private final EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    private List<String> listEspecialidade;
    private List<Especialidade> listEspecialidadeAll;
    private List<Medico> listMedico;
    private ObservableList<String> observableListEspecialidade;

    private final MedicoDAO medicoDAO = new MedicoDAO();
    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(connection);
        System.out.println("cadastro");
        medicoDAO.setConnection(connection);
        especialidadeDAO.setConnection(connection);


        listEspecialidadeAll = especialidadeDAO.listar();
        listEspecialidade = especialidadeDAO.pegarEspecialidade();
        observableListEspecialidade = FXCollections.observableArrayList(listEspecialidade);
        comboBoxEspecialidade.setItems(observableListEspecialidade);
    }

    @FXML
    public void handleButtonCadastro(ActionEvent event) {
        Medico medico = new Medico();
        Especialidade especialidade = new Especialidade();

        if (validarEntradaDeDados()){

            System.out.println("Tamanho id:"+medico.getId());
            medico.setNome(TextFieldNome.getText());
            medico.setSenha(TextFieldSenha.getText());
            medico.setCRM(TextFieldCRM.getText());
            especialidade.setNome(comboBoxEspecialidade.getSelectionModel().getSelectedItem());


            System.out.println("Tamanho id:"+medico.getId());
            for (Especialidade esp: listEspecialidadeAll){
                if (especialidade.getNome().equals(esp.getNome())){
                    medico.setEspecialidade(esp.getId());
                }
            }
            //medico.setEspecialidade(int sql = "SELECT id FROM Especialidade WHERE nome= comboBoxEspecialidade.getSelectionModel().getSelectedItem()");

            medicoDAO.inserir(medico);
            System.out.println("Cadatro realizado com sucesso");
        }

    }

    @FXML
    public void LoginButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((LoginController.class.getResource("/com/example/clinicabdt3/login.fxml")));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("login");
        stage.centerOnScreen();
        stage.show();
    }

    private boolean validarEntradaDeDados(){

        String erroMessage = "";

        if (TextFieldNome.getText() == null || TextFieldNome.getText().length() == 0){
            erroMessage += "Nome inválido!\n";
        }
        if (TextFieldSenha.getText() == null || TextFieldSenha.getText().length() == 0){
            erroMessage += "Senha inválida!\n";
        }
        if (TextFieldCRM.getText() == null || TextFieldCRM.getText().length() == 0){
            erroMessage += "CRM inválida!\n";
        }
        if (comboBoxEspecialidade.getValue() == null || comboBoxEspecialidade.getValue().length() == 0){
            erroMessage += "Especialidade inválida!\n";
        }

        if (erroMessage.length() == 0){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO NO CADASTRO");
            alert.setHeaderText("Campos inválidos, por favor corrija");
            alert.setContentText(erroMessage);
            alert.show();
            return false;
        }

    }

}

