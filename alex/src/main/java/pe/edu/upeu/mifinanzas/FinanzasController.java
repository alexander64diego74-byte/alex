package pe.edu.upeu.mifinanzas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class FinanzasController {

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtMonto;

    @FXML
    private ComboBox<String> cmbTipo;

    @FXML
    private TableView<Movimiento> tablaMovimientos;

    @FXML
    private TableColumn<Movimiento, String> colFecha;

    @FXML
    private TableColumn<Movimiento, String> colDescripcion;

    @FXML
    private TableColumn<Movimiento, String> colTipo;

    @FXML
    private TableColumn<Movimiento, Double> colMonto;

    @FXML
    private Label lblSaldo;

    private ObservableList<Movimiento> movimientos =
            FXCollections.observableArrayList();

    private FinanzasDAO dao = new FinanzasDAO();

    @FXML
    public void initialize() {

        cmbTipo.setItems(
                FXCollections.observableArrayList(
                        "Ingreso",
                        "Gasto"
                )
        );

        colFecha.setCellValueFactory(
                dato -> new javafx.beans.property.SimpleStringProperty(
                        dato.getValue().getFecha()
                )
        );

        colDescripcion.setCellValueFactory(
                dato -> new javafx.beans.property.SimpleStringProperty(
                        dato.getValue().getDescripcion()
                )
        );

        colTipo.setCellValueFactory(
                dato -> new javafx.beans.property.SimpleStringProperty(
                        dato.getValue().getTipo()
                )
        );

        colMonto.setCellValueFactory(
                dato -> new javafx.beans.property.SimpleObjectProperty<>(
                        dato.getValue().getMonto()
                )
        );

        movimientos.addAll(dao.listar());

        tablaMovimientos.setItems(movimientos);

        actualizarSaldo();
    }

    @FXML
    private void registrarMovimiento() {

        String descripcion = txtDescripcion.getText();
        String tipo = cmbTipo.getValue();
        String montoTexto = txtMonto.getText();

        if (descripcion.isEmpty() || tipo == null || montoTexto.isEmpty()) {
            mostrarMensaje("Completa todos los campos.");
            return;
        }

        try {

            double monto = Double.parseDouble(montoTexto);

            if (monto <= 0) {
                mostrarMensaje("El monto debe ser mayor que 0.");
                return;
            }

            Movimiento movimiento = new Movimiento(
                    LocalDate.now().toString(),
                    descripcion,
                    tipo,
                    monto
            );

            dao.guardar(movimiento);

            movimientos.add(movimiento);

            txtDescripcion.clear();
            txtMonto.clear();
            cmbTipo.setValue(null);

            actualizarSaldo();

        } catch (NumberFormatException e) {

            mostrarMensaje("Ingresa un monto válido.");
        }
    }

    @FXML
    private void eliminarMovimiento() {

        Movimiento seleccionado =
                tablaMovimientos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarMensaje("Selecciona un movimiento para eliminar.");
            return;
        }

        dao.eliminar(seleccionado);

        movimientos.remove(seleccionado);

        actualizarSaldo();
    }

    private void actualizarSaldo() {

        double saldo = 0;

        for (Movimiento movimiento : movimientos) {

            if (movimiento.getTipo().equals("Ingreso")) {
                saldo += movimiento.getMonto();
            } else {
                saldo -= movimiento.getMonto();
            }
        }

        lblSaldo.setText(String.format("S/ %.2f", saldo));
    }

    private void mostrarMensaje(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Mi Finanzas");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}