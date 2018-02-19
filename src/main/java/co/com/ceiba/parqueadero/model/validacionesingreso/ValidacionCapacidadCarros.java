package co.com.ceiba.parqueadero.model.validacionesingreso;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

public class ValidacionCapacidadCarros implements ValidacionIngresoVehiculo{
	
	VehiculoRepository vehiculoRepository;
	
	public ValidacionCapacidadCarros(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(VehiculoModel vehiculoModel) {
		if (!validarEspacioCarros() && (vehiculoModel instanceof CarroModel)) { // Validar espacio
			throw new ParqueaderoException("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS CARROS");
		}		
	}
	
	public boolean validarEspacioCarros() {
		return vehiculoRepository.countByVehiculos("Carro", true) < ParqueaderoModel.LIMITECARROS;
	}

}
