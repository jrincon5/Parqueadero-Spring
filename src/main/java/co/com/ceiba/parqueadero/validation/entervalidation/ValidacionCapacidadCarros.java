package co.com.ceiba.parqueadero.validation.entervalidation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.Carro;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

public class ValidacionCapacidadCarros implements ValidacionIngresoVehiculo{
	
	private static final Log LOG = LogFactory.getLog(ValidacionCapacidadCarros.class);
	
	@Autowired
	@Qualifier("vehiculoRepository")
	VehiculoRepository vehiculoRepository;
	
	public ValidacionCapacidadCarros(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(Vehiculo vehiculoModel) {
		if (!validarEspacioCarros() && (vehiculoModel instanceof Carro)) { // Validar espacio
			throw new ParqueaderoException("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS CARROS");
		}		
	}
	
	public boolean validarEspacioCarros() {
		LOG.info("CALL: validarEspacioCarros()");
		return vehiculoRepository.countByVehiculos("Carro", true) < Parqueadero.LIMITECARROS;
	}

}
