package pe.edu.upeu.mifinanzas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void registrarGasto(ActionEvent actionEvent) {
    }

    public void registrarIngreso(ActionEvent actionEvent) {
    }

    public void verResumen(ActionEvent actionEvent) {
    }

    public void verMetas(ActionEvent actionEvent) {

    }

    public void ver(ActionEvent actionEvent) {
    }
}
