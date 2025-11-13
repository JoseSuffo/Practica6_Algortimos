package Ordenamientos;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Archivo {
    String[][] datos;

    public String[][] getDatos() {
        return datos;
    }

    public void leerArchivoCSV(){
        ArrayList<String[]> datosLeidos = new ArrayList<>();
        String rutaArchivo="src/main/resources/Datos/weatherHistory.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            String linea;
            while ((linea = br.readLine()) != null){
                datosLeidos.add(linea.split(","));
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("No se encontro el archivo");
            alert.showAndWait();
        }
        datos=datosLeidos.toArray(new String[0][0]);
    }
}
