package co.com.ceiba.parqueadero.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Vehiculo {
	
	@Override
	public String toString() {
		return "Vehiculo [placa=" + placa + ", cc=" + cc + "]";
	}
	
	@NotNull
	@Size(min=2,max=10)
	private String placa;
	
	@NotNull
	@Min(18)
    private int cc;
    
    public Vehiculo(String placa, int cc) {
        this.placa = placa;
        this.cc = cc;
    }
    
    public Vehiculo() {}

	public String getPlaca() {
	    return placa;
	}
	
	public void setPlaca(String placa) {
	    this.placa = placa;
	}
	
	public int getCc() {
	    return cc;
	}
	
	public void setCc(int cc) {
	    this.cc = cc;
	}

}
