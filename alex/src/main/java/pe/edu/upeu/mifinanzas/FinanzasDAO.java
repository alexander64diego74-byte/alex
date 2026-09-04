package pe.edu.upeu.mifinanzas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanzasDAO {

    private static final String URL = "jdbc:sqlite:mifinanzas.db";

    public FinanzasDAO() {
        crearTabla();
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private void crearTabla() {

        String sql = """
                CREATE TABLE IF NOT EXISTS movimientos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fecha TEXT NOT NULL,
                    descripcion TEXT NOT NULL,
                    tipo TEXT NOT NULL,
                    monto REAL NOT NULL
                )
                """;

        try (Connection conexion = conectar();
             Statement sentencia = conexion.createStatement()) {

            sentencia.execute(sql);

        } catch (SQLException e) {
            System.out.println("Error al crear la tabla: " + e.getMessage());
        }
    }

    public void guardar(Movimiento movimiento) {

        String sql = """
                INSERT INTO movimientos(fecha, descripcion, tipo, monto)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, movimiento.getFecha());
            sentencia.setString(2, movimiento.getDescripcion());
            sentencia.setString(3, movimiento.getTipo());
            sentencia.setDouble(4, movimiento.getMonto());

            sentencia.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public List<Movimiento> listar() {

        List<Movimiento> lista = new ArrayList<>();

        String sql = "SELECT fecha, descripcion, tipo, monto FROM movimientos";

        try (Connection conexion = conectar();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery(sql)) {

            while (resultado.next()) {

                Movimiento movimiento = new Movimiento(
                        resultado.getString("fecha"),
                        resultado.getString("descripcion"),
                        resultado.getString("tipo"),
                        resultado.getDouble("monto")
                );

                lista.add(movimiento);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }

        return lista;
    }

    public void eliminar(Movimiento movimiento) {

        String sql = """
                DELETE FROM movimientos
                WHERE fecha = ? AND descripcion = ? AND tipo = ? AND monto = ?
                """;

        try (Connection conexion = conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, movimiento.getFecha());
            sentencia.setString(2, movimiento.getDescripcion());
            sentencia.setString(3, movimiento.getTipo());
            sentencia.setDouble(4, movimiento.getMonto());

            sentencia.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}