package com.example.clinicabdt3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admPacientes.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Consultas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}