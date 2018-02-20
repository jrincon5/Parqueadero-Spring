package co.com.ceiba.parqueadero.validation.exitvalidation;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

public class ValidacionPlacaValida implements ValidacionSalidaVehiculo{
	
	VehiculoRepository vehiculoRepository;
	
	public ValidacionPlacaValida(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(String placa) {
		if(!placaExistente(placa)) {
			throw new ParqueaderoException("LA PLACA INGRESADA NO SE ENCUENTRA UBICADA EN EL PARQUEADERO");
		}
	}
	
	public boolean placaExistente(String placa) {
		return vehiculoRepository.exists(placa.toUpperCase());
	}
}
