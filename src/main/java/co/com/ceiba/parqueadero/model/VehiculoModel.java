package co.com.ceiba.parqueadero.model;

import javax.validation.constraints.NotNull;

public class VehiculoModel {
	
	@Override
	public String toString() {
		return "Vehiculo [placa=" + placa + ", cc=" + cilindraje + "]";
	}
	
	@NotNull
	private String placa;
	
	@NotNull
    private int cilindraje;
    
    public VehiculoModel(String placa, int cilindraje) {
        this.placa = placa;
        this.cilindraje = cilindraje;
    }
    
    public VehiculoModel() {}

	public String getPlaca() {
	    return placa;
	}
	
	public void setPlaca(String placa) {
	    this.placa = placa;
	}
	
	public int getCilindraje() {
	    return cilindraje;
	}
	
	public void setCilindraje(int cilindraje) {
	    this.cilindraje = cilindraje;
	}

}
