package co.com.ceiba.parqueadero.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="comprobante_pago")
public class ComprobantePagoEntity {
	
	@Id
	@GeneratedValue
	@Column(name="id_comprobante_pago")
	private int idComprobantePago;
	
	@Column(name="fecha_entrada", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntrada;
	
	@Column(name="fecha_salida", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaSalida;
	
	@Column(name="total_horas")
	private int totalHoras;
	
	@Column(name="total_pagar")
	private int totalPagar;
	
	@Column(name="estado")
	private boolean estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "placa_fk",referencedColumnName = "placa")
	private VehiculoEntity placaFk;

	public int getIdComprobantePago() {
		return idComprobantePago;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
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

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public ComprobantePagoEntity(Date fechaEntrada, Date fechaSalida, int totalHoras, int totalPagar, boolean estado,
			VehiculoEntity placaFk) {
		super();
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.totalHoras = totalHoras;
		this.totalPagar = totalPagar;
		this.estado = estado;
		this.placaFk = placaFk;
	}

	public ComprobantePagoEntity() {
		super();
	}	
}