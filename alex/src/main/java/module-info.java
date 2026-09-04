module pe.edu.upeu.mifinanzas {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;

    exports pe.edu.upeu.mifinanzas;
    opens pe.edu.upeu.mifinanzas to javafx.fxml;
}