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

	public boolean isParqueado() {
		return parqueado;
	}

	public VehiculoModel(String placa, boolean parqueado) {
		this.placa = placa;
		this.parqueado = parqueado;
	}

	public abstract String getTipoVehiculo();

}