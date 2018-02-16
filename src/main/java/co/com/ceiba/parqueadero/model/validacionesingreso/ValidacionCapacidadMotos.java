package co.com.ceiba.parqueadero.model.validacionesingreso;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

public class ValidacionCapacidadMotos implements ValidacionIngresoVehiculo {

	VehiculoRepository vehiculoRepository;

	public ValidacionCapacidadMotos(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(VehiculoModel vehiculoModel) {
		if (!validarEspacioMotos()) { // Validar espacio
			throw new ParqueaderoException("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS MOTOS");
		}
	}

	public boolean validarEspacioMotos() {
		return vehiculoRepository.countByVehiculos("Moto", true) < ParqueaderoModel.LIMITEMOTOS;
	}
}
