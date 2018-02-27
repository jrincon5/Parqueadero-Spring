package co.com.ceiba.parqueadero.validation.entervalidation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

public class ValidacionCapacidadMotos implements ValidacionIngresoVehiculo {
	
	private static final Log LOG = LogFactory.getLog(ValidacionCapacidadMotos.class);

	VehiculoRepository vehiculoRepository;

	public ValidacionCapacidadMotos(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(VehiculoModel vehiculoModel) {
		if (!validarEspacioMotos() && (vehiculoModel instanceof MotoModel)) { // Validar espacio
			throw new ParqueaderoException("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS MOTOS");
		}
	}

	public boolean validarEspacioMotos() {
		LOG.info("CALL: validarEspacioMotos()");
		return vehiculoRepository.countByVehiculos("Moto", true) < ParqueaderoModel.LIMITEMOTOS;
	}
}
