package co.com.ceiba.parqueadero.model;

import java.util.Date;

public class ComprobantePagoModel {
	private String placa;
	private String tipoVehiculo;
	private Date fechaEntrada;
	
	public ComprobantePagoModel() {
		super();
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getPlaca() {
		return placa;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}
}