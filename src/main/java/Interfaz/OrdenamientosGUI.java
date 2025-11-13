package Interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrdenamientosGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Creación del menú principal y llamada del mismo.
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/menu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema de ordenamiento Guasavito");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
