package co.com.ceiba.parqueadero.model;

import java.util.Date;

public class ComprobantePago {
	private String placa;
	private Date fechaEntrada;
	private Date fechaSalida;
	private int totalHoras;
	private int totalPagar;
	
	public ComprobantePago(String placa, Date fechaEntrada, Date fechaSalida, int totalHoras, int totalPagar) {
		this.placa = placa;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.totalHoras = totalHoras;
		this.totalPagar = totalPagar;
	}

	public ComprobantePago() {
		super();
	}

	public String getPlaca() {
		return placa;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public int getTotalHoras() {
		return totalHoras;
	}

	public int getTotalPagar() {
		return totalPagar;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public void setTotalHoras(int totalHoras) {
		this.totalHoras = totalHoras;
	}

	public void setTotalPagar(int totalPagar) {
		this.totalPagar = totalPagar;
	}
	
	
}
