package co.com.ceiba.parqueadero.validation.entervalidation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.Moto;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

public class ValidacionCapacidadMotos implements ValidacionIngresoVehiculo {
	
	private static final Log LOG = LogFactory.getLog(ValidacionCapacidadMotos.class);

	VehiculoRepository vehiculoRepository;

	public ValidacionCapacidadMotos(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(Vehiculo vehiculoModel) {
		if (!validarEspacioMotos() && (vehiculoModel instanceof Moto)) { // Validar espacio
			throw new ParqueaderoException("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS MOTOS");
		}
	}

	public boolean validarEspacioMotos() {
		LOG.info("CALL: validarEspacioMotos()");
		return vehiculoRepository.countByVehiculos("Moto", true) < Parqueadero.LIMITEMOTOS;
	}
}
