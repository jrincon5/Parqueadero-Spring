package co.com.ceiba.parqueadero.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;
import co.com.ceiba.parqueadero.validation.entervalidation.ValidacionCapacidadCarros;
import co.com.ceiba.parqueadero.validation.entervalidation.ValidacionCapacidadMotos;
import co.com.ceiba.parqueadero.validation.entervalidation.ValidacionIngresoVehiculo;
import co.com.ceiba.parqueadero.validation.entervalidation.ValidacionPlacaIniciaPorA;

@org.springframework.context.annotation.Configuration
public class ConfigurationBean {
	
	@Bean
	public List<ValidacionIngresoVehiculo> configurarValidacionesVigilante(VehiculoRepository vehiculoRepository){
		List<ValidacionIngresoVehiculo> validacionIngresoVehiculos = new ArrayList<>();
		validacionIngresoVehiculos.add(new ValidacionCapacidadMotos(vehiculoRepository));
		validacionIngresoVehiculos.add(new ValidacionCapacidadCarros(vehiculoRepository));
		validacionIngresoVehiculos.add(new ValidacionPlacaIniciaPorA());
		return validacionIngresoVehiculos;
	}
}
