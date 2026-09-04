package pe.edu.upeu.mifinanzas;

public class Movimiento {

    private String fecha;
    private String descripcion;
    private String tipo;
    private double monto;

    public Movimiento(String fecha, String descripcion, String tipo, double monto) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }
}