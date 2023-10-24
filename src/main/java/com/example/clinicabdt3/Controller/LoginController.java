package com.example.clinicabdt3.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;


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

    String cpf;
    String senha;

    @FXML
    void cadastrar(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("registro.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        cpf = CPFTextField.getText();
        senha = senhaTextField.getText();

        //precisa procurar o nome digitado no banco(ainda precisa implementar) e se for achado terá condições que levam a diferentes telas
        if(CPFTextField.getText().equals("admin")) {
            irAdminMenu(event);
            //se o texto digitado for igual o nome 'admin' ele vai executar a função para ir para menu adm
        }


    }

    private void irAdminMenu(ActionEvent event) throws IOException
    {
        //função para ir para menu adm
        //FXMLLoader loader = new FXMLLoader((getClass().getResource("nome da pagina")));
        //Parent root = loader.load();

        String css = this.getClass().getResource("style2.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);

        // controller = loader.getController();
       //controller._initialize(); -> vai ter que inicializar a tabela do admin assim que abre
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
