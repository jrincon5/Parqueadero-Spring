package co.com.ceiba.parqueadero.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="factura")
public class FacturaEnt {
	
	@Id
	@Column(name="id_factura")
	private int id_factura;
	
	@Column(name="fecha_entrada", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_entrada;
	
	@Column(name="fecha_salida", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_salida;
	
	@Column(name="total_horas")
	private int total_horas;
	
	@Column(name="total_pagar")
	private int total_pagar;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "placa",referencedColumnName = "placa")
	private VehiculoEnt placa;
	

	public int getId_factura() {
		return id_factura;
	}

	public void setId_factura(int id_factura) {
		this.id_factura = id_factura;
	}

	public Date getFecha_entrada() {
		return fecha_entrada;
	}

	public void setFecha_entrada(Date fecha_entrada) {
		this.fecha_entrada = fecha_entrada;
	}

	public Date getFecha_salida() {
		return fecha_salida;
	}

	public void setFecha_salida(Date fecha_salida) {
		this.fecha_salida = fecha_salida;
	}

	public int getTotal_horas() {
		return total_horas;
	}

	public void setTotal_horas(int total_horas) {
		this.total_horas = total_horas;
	}

	public int getTotal_pagar() {
		return total_pagar;
	}

	public void setTotal_pagar(int total_pagar) {
		this.total_pagar = total_pagar;
	}	

	public VehiculoEnt getPlaca() {
		return placa;
	}

	public void setPlaca(VehiculoEnt placa) {
		this.placa = placa;
	}	

	public FacturaEnt(int id_factura, Date fecha_entrada, Date fecha_salida, int total_horas, int total_pagar,
			VehiculoEnt placa) {
		super();
		this.id_factura = id_factura;
		this.fecha_entrada = fecha_entrada;
		this.fecha_salida = fecha_salida;
		this.total_horas = total_horas;
		this.total_pagar = total_pagar;
		this.placa = placa;
	}

	public FacturaEnt() {
		super();
	}
}
