package co.com.ceiba.parqueadero.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VehiculoModel {
	
	@Override
	public String toString() {
		return "Vehiculo [placa=" + placa + ", cc=" + cilindraje + "]";
	}
	
	@NotNull
	@Size(min=2,max=10)
	private String placa;
	
	@NotNull
	@Min(18)
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
