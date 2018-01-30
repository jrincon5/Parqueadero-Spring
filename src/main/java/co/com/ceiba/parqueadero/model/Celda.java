package co.com.ceiba.parqueadero.model;

public class Celda {
    private Vehiculo vehiculo;
    private Fecha fecha;

    public Celda(Vehiculo vehiculo, Fecha fecha) {
        this.vehiculo = vehiculo;
        this.fecha = fecha;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }
}