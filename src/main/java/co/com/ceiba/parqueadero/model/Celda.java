package co.com.ceiba.parqueadero.model;

public class Celda {
    private VehiculoModel vehiculo;
    private Fecha fecha;

    public Celda(VehiculoModel vehiculo, Fecha fecha) {
        this.vehiculo = vehiculo;
        this.fecha = fecha;
    }

    public VehiculoModel getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehiculoModel vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }
}