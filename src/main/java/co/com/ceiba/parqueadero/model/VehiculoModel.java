package co.com.ceiba.parqueadero.model;

import javax.validation.constraints.NotNull;

public abstract class VehiculoModel {

	@NotNull
	private String placa;
	@NotNull
	private boolean parqueado;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public boolean isParqueado() {
		return parqueado;
	}

	public void setParqueado(boolean parqueado) {
		this.parqueado = parqueado;
	}

	public VehiculoModel(String placa, boolean parqueado) {
		super();
		this.placa = placa;
		this.parqueado = parqueado;
	}
	
	public abstract String getTipoVehiculo();

}