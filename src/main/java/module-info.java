module com.example.practica6_algoritmos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires javafx.base;

    opens com.example.practica6_algoritmos to javafx.fxml;
    exports com.example.practica6_algoritmos;
    exports Interfaz;
    opens Interfaz to javafx.fxml;
}