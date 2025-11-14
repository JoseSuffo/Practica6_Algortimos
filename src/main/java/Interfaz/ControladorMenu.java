package Interfaz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControladorMenu {

    //Creación del evento al presionar el boton Jugar
    @FXML
    public void botonIniciar(ActionEvent actionEvent) {
        Stage stage = new Stage();
        ControladorOrdenamientos controlador = new ControladorOrdenamientos();

        controlador.iniciar(stage);
        stage.setTitle("Sistema de Ordenamientos: Guasavito");
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.show();

        // Cerrar la ventana actual del menú
        Node node = (Node) actionEvent.getSource();
        Stage actual = (Stage) node.getScene().getWindow();
        actual.close();
    }

    //Creación del evento al presionar el boton Creditos
    @FXML
    public void botonCreditos(ActionEvent actionEvent){
        //Se envia una alerta de información que muestra mi nombre
        Alert creditos = new Alert(Alert.AlertType.INFORMATION);
        creditos.setTitle("Creditos del programa");
        creditos.setHeaderText("Creditos del programa");
        creditos.setContentText("Hecho por José Ramón Suffo Peimbert.");
        creditos.show();
    }

    //Creación del evento al presionar el boton Salir
    @FXML
    public void botonSalir(javafx.event.ActionEvent actionEvent) {
        //Salida del programa
        System.exit(0);
    }
}
