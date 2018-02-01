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
	@Column(name="tipo_vehiculo")
	String tipo_vehiculo;
	@Column(name="parqueado")
	boolean parqueado;
	
	public VehiculoEnt() {		
	}
			
	public VehiculoEnt(String placa, int cilindraje, String tipo_vehiculo, boolean parqueado) {
		super();
		this.placa = placa;
		this.cilindraje = cilindraje;
		this.tipo_vehiculo = tipo_vehiculo;
		this.parqueado = parqueado;
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
	public String getTipo_vehiculo() {
		return tipo_vehiculo;
	}
	public void setTipo_vehiculo(String tipo_vehiculo) {
		this.tipo_vehiculo = tipo_vehiculo;
	}
	public boolean isParqueado() {
		return parqueado;
	}
	public void setParqueado(boolean parqueado) {
		this.parqueado = parqueado;
	}
	
	
}
