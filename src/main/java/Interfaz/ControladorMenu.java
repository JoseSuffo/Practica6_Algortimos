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
        //Se crea la ventana y una instancia del controlador del tablero
        BorderPane ventana = new BorderPane();
        ControladorOrdenamientos controlador = new ControladorOrdenamientos(ventana);

        //Se crea la escena del tablero y se muestra
        Scene simulador = new Scene(ventana, 800, 800);
        Stage stage = new Stage();
        stage.setScene(simulador);
        stage.setResizable(false);
        stage.setTitle("Sistema de Ordenamientos: Guasavito");
        stage.show();

        //Se cierra la ventana actual del menú
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
