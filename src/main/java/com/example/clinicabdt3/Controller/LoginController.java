package com.example.clinicabdt3.Controller;
import com.example.clinicabdt3.Controller.CadastroController;
import com.example.clinicabdt3.Model.Database.DatabaseFactory;
import com.example.clinicabdt3.Model.Database.DatabaseSQLite;
import com.example.clinicabdt3.Model.Domain.Medico;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javafx.beans.value.ChangeListener;


public class LoginController {
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField senhaTextField;

    @FXML
    private TextField CPFTextField;

    @FXML
    private Button cadastrarButton;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private final DatabaseSQLite database = DatabaseFactory.getDatabase("clinicabd");
    private final Connection connection = database.conectar();
    public String crm;
   static String crmMedico;


    @FXML
    public void handleButtonCadastrar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((CadastroController.class.getResource("/com/example/clinicabdt3/cadastro.fxml")));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cadastro");
        stage.centerOnScreen();
        stage.show();
    }

    public void irAdminMenu(ActionEvent event) throws IOException{
        //função para ir para menu adm
        //FXMLLoader loader = new FXMLLoader((getClass().getResource("nome da pagina")));
        //Parent root = loader.load();
        Parent root = FXMLLoader.load(MenuAdmController.class.getResource("/com/example/clinicabdt3/adm_menu.fxml"));

        String css = MenuAdmController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        // controller = loader.getController();
        //controller._initialize(); -> vai ter que inicializar a tabela do admin assim que abre
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void irMedicoMenu(ActionEvent event) throws IOException, SQLException {
        crmMedico = CPFTextField.getText();

        root = FXMLLoader.load(medico_menuController.class.getResource("/com/example/clinicabdt3/menuMedico.fxml"));
        medico_menuController controller = new medico_menuController();
        controller.setCrm(crm);
        String css = medico_menuController.class.getResource("/com/example/clinicabdt3/style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        connection.close();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    public void login(ActionEvent event) throws IOException {

        //precisa procurar o nome digitado no banco(ainda precisa implementar) e se for achado terá condições que levam a diferentes telas

        // se o texto digitado por admin admin abre a tela adm_menu
        System.out.println(CPFTextField.getText());
        if(CPFTextField.getText().equals("admin") || senhaTextField.getText().equals("admin")) {

            irAdminMenu(event);
        }else {
            //precisa procurar o nome digitado no banco e se for achado terá condições que levam a diferentes telas
            String sql = "SELECT crm, senha FROM medico WHERE crm = ? and senha = ?";
            //connect = database.conectar();

            try {

                Alert alert;
                if (CPFTextField.getText().isEmpty() || senhaTextField.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro Message");
                    alert.setHeaderText(null);
                    alert.setContentText("por favor preencha todos os campos em branco");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, CPFTextField.getText());
                    prepare.setString(2, senhaTextField.getText());

                    result = prepare.executeQuery();

                    if (result.next()) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        crmMedico = CPFTextField.getText();
                        irMedicoMenu(event);

                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro Message");
                        alert.setHeaderText(null);
                        alert.setContentText("CRM ou Senha incorretos");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public String getCrmMedico() {
        return crmMedico;
    }
}
