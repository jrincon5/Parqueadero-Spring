package co.com.ceiba.parqueadero.repository.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.model.ComprobantePago;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

@Component("comprobanteCoverter")
public class ComprobanteCoverter {
	
	@Autowired
	VehiculoRepository vehiculoRepository;
	
	public ComprobantePago comprobanteEntity2Model(ComprobantePagoEntity comprobanteEntity) {
		ComprobantePago comprobante = new ComprobantePago();
		comprobante.setFechaEntrada(comprobanteEntity.getFechaEntrada());
		comprobante.setFechaSalida(comprobanteEntity.getFechaSalida());
		comprobante.setTotalHoras(comprobanteEntity.getTotalHoras());
		comprobante.setTotalPagar(comprobanteEntity.getTotalPagar());
		comprobante.setPlaca(comprobanteEntity.getPlacaFk());
		return comprobante;
	}
	
	public ComprobantePagoEntity comprobanteModel2Entity(ComprobantePago comprobante, boolean parqueado) {
		ComprobantePagoEntity comprobanteEntity = new ComprobantePagoEntity();
		comprobanteEntity.setFechaEntrada(comprobante.getFechaEntrada());
		comprobanteEntity.setFechaSalida(comprobante.getFechaSalida());
		comprobanteEntity.setTotalHoras(comprobante.getTotalHoras());
		comprobanteEntity.setTotalPagar(comprobante.getTotalPagar());
		comprobanteEntity.setPlacaFk(vehiculoRepository.findOne(comprobante.getPlaca()));
		comprobanteEntity.setEstado(parqueado);
		return comprobanteEntity;
	}
}
