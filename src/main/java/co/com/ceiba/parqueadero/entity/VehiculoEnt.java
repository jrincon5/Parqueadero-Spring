package co.com.ceiba.parqueadero.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehiculo")
public class VehiculoEnt {
	
	@Id
	@Column(name="placa")
	String placa;
	@Column(name="cilindraje")
	int cilindraje;
	@Column(name="parqueado")
	boolean parqueado;
	@Column(name="tipo_vehiculo")
	String tipo_vehiculo;	
	
	public VehiculoEnt() {		
	}
			
	public VehiculoEnt(String placa, int cilindraje, boolean parqueado, String tipo_vehiculo) {
		super();
		this.placa = placa;
		this.cilindraje = cilindraje;
		this.parqueado = parqueado;
		this.tipo_vehiculo = tipo_vehiculo;
	}
	
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
	public boolean isParqueado() {
		return parqueado;
	}
	public void setParqueado(boolean parqueado) {
		this.parqueado = parqueado;
	}
	public String getTipo_vehiculo() {
		return tipo_vehiculo;
	}
	public void setTipo_vehiculo(String tipo_vehiculo) {
		this.tipo_vehiculo = tipo_vehiculo;
	}
}
