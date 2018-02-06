package co.com.ceiba.parqueadero.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehiculo")
public class VehiculoEntity {
	
	@Id
	@Column(name = "placa")
	private String placa;
	@Column(name = "parqueado")
	private boolean parqueado;	
	@Column(name = "cilindraje")
	private int cilindraje;
	@Column(name = "tipo_vehiculo")
	private String tipoVehiculo;
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
	public int getCilindraje() {
		return cilindraje;
	}
	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	public VehiculoEntity(String placa, boolean parqueado, int cilindraje, String tipoVehiculo) {
		super();
		this.placa = placa;
		this.parqueado = parqueado;
		this.cilindraje = cilindraje;
		this.tipoVehiculo = tipoVehiculo;
	}
	public VehiculoEntity() {
		super();
	}	
}