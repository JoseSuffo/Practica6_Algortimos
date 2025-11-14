package Ordenamientos;

import javafx.scene.control.Alert;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Archivo {
    private String[][] datos;

    public String[][] getDatos() {
        return datos;
    }

    public void leerArchivoCSV() {
        ArrayList<String[]> datosLeidos = new ArrayList<>();

        String rutaArchivo = "src/main/resources/Datos/weatherHistory.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                datosLeidos.add(linea.split(","));
            }
            datos = datosLeidos.toArray(new String[0][0]);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al leer CSV");
            alert.setHeaderText("No se pudo cargar el archivo por defecto");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}