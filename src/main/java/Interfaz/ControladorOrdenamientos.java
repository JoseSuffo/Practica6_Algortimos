package Interfaz;

import Ordenamientos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorOrdenamientos {

    private TableView<String[]> tabla;
    private BarChart<String, Number> grafica;
    private Archivo archivo = new Archivo();
    private String[][] datos;

    private CheckBox chkQuick, chkMerge, chkShell, chkSeleccion, chkRadix, chkSort, chkParallel;
    private ChoiceBox<String> columnaSelector;

    private final Ordenamiento ord = new Ordenamiento();

    //Método que inicia toda la interfaz junto a sus botones y su posterior funcionalidad
    public void iniciar(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        Button btnCargar = new Button("Cargar Dataset");
        Button btnSimular = new Button("Simular Ordenamiento");
        Button btnSalir = new Button("Salir");

        chkQuick = new CheckBox("QuickSort");
        chkMerge = new CheckBox("MergeSort");
        chkShell = new CheckBox("Shell Sort");
        chkSeleccion = new CheckBox("Selección Directa");
        chkRadix = new CheckBox("Radix Sort");
        chkSort = new CheckBox("Arrays.sort()");
        chkParallel = new CheckBox("Arrays.parallelSort()");

        HBox metodos = new HBox(10, chkQuick, chkMerge, chkShell, chkSeleccion, chkRadix, chkSort, chkParallel);
        metodos.setPadding(new Insets(10));
        metodos.setStyle("-fx-background-color: #eaf3fa; -fx-border-radius: 8; -fx-background-radius: 8;");

        columnaSelector = new ChoiceBox<>();
        columnaSelector.setPrefWidth(200);

        HBox controles = new HBox(15, btnCargar, new Label("Columna a ordenar:"), columnaSelector, btnSimular, btnSalir);
        controles.setPadding(new Insets(10));

        VBox top = new VBox(controles, metodos);
        root.setTop(top);

        tabla = new TableView<>();
        tabla.setPlaceholder(new Label("Cargue un dataset CSV para comenzar"));
        root.setCenter(tabla);

        CategoryAxis ejeX = new CategoryAxis();
        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Tiempo (nanosegundos)");

        grafica = new BarChart<>(ejeX, ejeY);
        grafica.setTitle("Comparativa de Métodos de Ordenamiento");
        grafica.setLegendVisible(false);
        grafica.setPrefHeight(300);
        root.setBottom(grafica);

        btnCargar.setOnAction(e -> cargarCSV());
        btnSimular.setOnAction(e -> simular());
        btnSalir.setOnAction(e -> {
            try {
                cerrar(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.setTitle("Simulador de Métodos de Ordenamiento");
        stage.show();
    }

    //Método que carga el archivo CSV con los datos
    private void cargarCSV() {
        archivo.leerArchivoCSV();
        datos = archivo.getDatos();

        if (datos == null || datos.length == 0) {
            mostrarAlerta("Error", "No se pudo cargar el archivo CSV.");
            return;
        }

        columnaSelector.getItems().clear();
        String[] encabezados = datos[0];
        columnaSelector.getItems().addAll(encabezados);
        columnaSelector.getItems().add("Todas las columnas");
        columnaSelector.setValue(encabezados[0]);

        mostrarTabla();
    }

    //Método que muestra la tabla con todos los datos extaídos del archivo CSV
    private void mostrarTabla() {
        tabla.getColumns().clear();

        if (datos == null || datos.length < 2) return;
        String[] encabezados = datos[0];

        for (int i = 0; i < encabezados.length; i++) {
            final int colIndex = i;
            TableColumn<String[], String> col = new TableColumn<>(encabezados[i]);
            col.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue()[colIndex]));
            col.setPrefWidth(150);
            tabla.getColumns().add(col);
        }

        ObservableList<String[]> items = FXCollections.observableArrayList();
        for (int i = 1; i < datos.length; i++) items.add(datos[i]);
        tabla.setItems(items);
    }

    //Método que realiza una simulación con todos los métodos seleccionados por el usuario
    private void simular() {
        if (datos == null || datos.length < 2) {
            mostrarAlerta("Aviso", "Primero cargue un dataset.");
            return;
        }

        String columna = columnaSelector.getValue();
        if (columna == null) {
            mostrarAlerta("Aviso", "Seleccione una columna a ordenar.");
            return;
        }

        List<String> metodosSeleccionados = new ArrayList<>();
        if (chkQuick.isSelected()) metodosSeleccionados.add("QuickSort");
        if (chkMerge.isSelected()) metodosSeleccionados.add("MergeSort");
        if (chkShell.isSelected()) metodosSeleccionados.add("Shell Sort");
        if (chkSeleccion.isSelected()) metodosSeleccionados.add("Selección Directa");
        if (chkRadix.isSelected()) metodosSeleccionados.add("Radix Sort");
        if (chkSort.isSelected()) metodosSeleccionados.add("ArraysSort");
        if (chkParallel.isSelected()) metodosSeleccionados.add("ParallelSort");

        if (metodosSeleccionados.isEmpty()) {
            mostrarAlerta("Aviso", "Seleccione al menos un método de ordenamiento.");
            return;
        }

        List<ResultRow> resultados = new ArrayList<>();
        resultados.clear();
        if (columna.equals("Todas las columnas")) {
            List<Integer> cols = columnasNumericas();
            for (int idx : cols) {
                String nombreCol = datos[0][idx];
                int[] valores = convertirColumnaAEnteros(idx);

                for (String metodo : metodosSeleccionados) {
                    long tiempo = ejecutarMetodo(metodo, valores.clone());
                    resultados.add(new ResultRow(nombreCol, metodo, tiempo));
                }
            }
        } else {
            int colIndex = columnaSelector.getItems().indexOf(columna);
            int[] valores = convertirColumnaAEnteros(colIndex);
            for (String metodo : metodosSeleccionados) {
                long tiempo = ejecutarMetodo(metodo, valores.clone());
                resultados.add(new ResultRow(columna, metodo, tiempo));
            }
        }
        mostrarResultados(resultados);
        mostrarGrafica(resultados);
    }

    //Método que convierte una columna que no sea de enteros a una columna de enteros
    private int[] convertirColumnaAEnteros(int colIndex) {
        List<Integer> lista = new ArrayList<>();
        for (int i = 1; i < datos.length; i++) {
            try {
                lista.add((int) Double.parseDouble(datos[i][colIndex]));
            } catch (Exception ignored) {
            }
        }
        return lista.stream().mapToInt(Integer::intValue).toArray();
    }

    //Método que mide el tiempo de ejecución de cada uno de los métodos
    private long medir(Runnable metodo) {
        long inicio = System.nanoTime();
        metodo.run();
        return System.nanoTime() - inicio;
    }

    //Método que muestra una alerta dentro del programa con mensaje y título recibidos como parámetros
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //Método que cierra la ventana actual y abre la ventana del menú inicial
    private void cerrar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/menu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sistema de ordenamiento Guasavito");
        stage.show();

        Node node = (Node) event.getSource();
        Stage actual = (Stage) node.getScene().getWindow();
        actual.close();
    }

    //Método que ejecuta cada uno de los métodos de ordenamientos
    private long ejecutarMetodo(String metodo, int[] valores) {
        switch (metodo) {
            case "QuickSort": return medir(() -> ord.quicksort(valores));
            case "MergeSort": return medir(() -> ord.mergeSort(valores, 0, valores.length - 1));
            case "Shell Sort": return medir(() -> ord.shellSort(valores));
            case "Selección Directa": return medir(() -> ord.seleccionDirecta(valores));
            case "Radix Sort": return medir(() -> ord.radixSort(valores));
            case "ArraysSort": return medir(() -> ord.sort(valores));
            case "ParallelSort": return medir(() -> ord.parallelSort(valores));
            default: return -1;
        }
    }

    //Clase auxiliar que permite obtener los resultados de una ejecución y guardarlos
    private static class ResultRow {
        String columna;
        String metodo;
        long tiempo;

        ResultRow(String columna, String metodo, long tiempo) {
            this.columna = columna;
            this.metodo = metodo;
            this.tiempo = tiempo;
        }
    }

    //Método que muestra una tabla externa con los resultados de los tiempos de ejecución de cada método utilizado
    //Dentro de este método iluminamos la fila que tomó menos tiempo de ejecutarse.
    private void mostrarResultados(List<ResultRow> resultados) {
        Stage stage = new Stage();
        TableView<ResultRow> tablaResultados = new TableView<>();

        TableColumn<ResultRow, String> colColumna = new TableColumn<>("Columna");
        colColumna.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().columna));

        TableColumn<ResultRow, String> colMetodo = new TableColumn<>("Método");
        colMetodo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().metodo));

        TableColumn<ResultRow, String> colTiempo = new TableColumn<>("Tiempo (ns)");
        colTiempo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().tiempo)));

        tablaResultados.getColumns().addAll(colColumna, colMetodo, colTiempo);
        tablaResultados.setItems(FXCollections.observableArrayList(resultados));

        long minTiempo = resultados.stream()
                .mapToLong(r -> r.tiempo)
                .min()
                .orElse(Long.MAX_VALUE);

        tablaResultados.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(ResultRow row, boolean empty) {
                super.updateItem(row, empty);
                if (row == null || empty) {
                    setStyle("");
                } else if (row.tiempo == minTiempo) {
                    setStyle("-fx-background-color:  #00FFFF; -fx-font-weight: bold; -fx-text-fill: black;");
                } else {
                    setStyle("");
                }
            }
        });

        VBox root = new VBox(new Label("Resultados de Simulación"), tablaResultados);
        root.setPadding(new Insets(10));

        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Tiempos de Ordenamiento");
        stage.show();
    }

    //Método que almacena todas las columnas númericas dentro de la base de datos utilizada.
    private List<Integer> columnasNumericas() {
        List<Integer> indices = new ArrayList<>();
        if (datos == null || datos.length < 2) return indices;

        for (int i = 0; i < datos[0].length; i++) {
            boolean esNumerica = true;
            for (int j = 1; j < datos.length; j++) {
                try {
                    Double.parseDouble(datos[j][i]);
                } catch (Exception e) {
                    esNumerica = false;
                    break;
                }
            }
            if (esNumerica) indices.add(i);
        }
        return indices;
    }

    //Método que muestra una gráfica con todos los tiempos de simulación de los métodos utilizados
    private void mostrarGrafica(List<ResultRow> resultados) {
        if (resultados.isEmpty()) return;

        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setLabel("Método de Ordenamiento");
        ejeX.setTickLabelRotation(20);

        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Tiempo (ms)");

        BarChart<String, Number> nuevaGrafica = new BarChart<>(ejeX, ejeY);
        nuevaGrafica.setTitle("Comparativa de Métodos de Ordenamiento");
        nuevaGrafica.setLegendVisible(true);
        nuevaGrafica.setPrefHeight(300);

        List<String> metodos = resultados.stream()
                .map(r -> r.metodo)
                .distinct()
                .toList();
        ejeX.setCategories(FXCollections.observableArrayList(metodos));

        for (String nombreColumna : resultados.stream().map(r -> r.columna).distinct().toList()) {
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(nombreColumna);
            for (ResultRow r : resultados) {
                if (r.columna.equals(nombreColumna)) {
                    double tiempoMs = r.tiempo / 1_000_000.0;
                    serie.getData().add(new XYChart.Data<>(r.metodo, tiempoMs));
                }
            }
            nuevaGrafica.getData().add(serie);
        }

        BorderPane parent = (BorderPane) grafica.getParent();
        parent.setBottom(nuevaGrafica);
        grafica = nuevaGrafica;

        grafica.layout();
    }
}