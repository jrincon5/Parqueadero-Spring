package co.com.ceiba.parqueadero.model;

public class CeldaModel {
    private VehiculoModel vehiculo;
    private FechaModel fecha;

    public CeldaModel(VehiculoModel vehiculo, FechaModel fecha) {
        this.vehiculo = vehiculo;
        this.fecha = fecha;
    }

    public VehiculoModel getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehiculoModel vehiculo) {
        this.vehiculo = vehiculo;
    }

    public FechaModel getFecha() {
        return fecha;
    }

    public void setFecha(FechaModel fecha) {
        this.fecha = fecha;
    }
}