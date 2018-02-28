package co.com.ceiba.parqueadero.validation.exitvalidation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

public class ValidacionPlacaValida implements ValidacionSalidaVehiculo{
	
	private static final Log LOG = LogFactory.getLog(ValidacionPlacaValida.class);
	
	VehiculoRepository vehiculoRepository;
	
	public ValidacionPlacaValida(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(String placa) {
		if(!placaExistente(placa.toUpperCase())) {
			throw new ParqueaderoException("LA PLACA INGRESADA NO SE ENCUENTRA UBICADA EN EL PARQUEADERO");
		}
	}
	
	public boolean placaExistente(String placa) {
		LOG.info("CALL: placaExistente()");
		if(vehiculoRepository.exists(placa)) {
			return vehiculoRepository.findOne(placa).isParqueado();	
		}
		return false;
	}
}
