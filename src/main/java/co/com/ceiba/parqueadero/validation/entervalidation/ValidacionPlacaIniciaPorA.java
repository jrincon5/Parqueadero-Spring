package co.com.ceiba.parqueadero.validation.entervalidation;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Vehiculo;

public class ValidacionPlacaIniciaPorA implements ValidacionIngresoVehiculo {
	
	private static final Log LOG = LogFactory.getLog(ValidacionPlacaIniciaPorA.class);

	@Override
	public void validar(Vehiculo vehiculoModel) {
		if (placaIniciaPorAYEsHabil(vehiculoModel.getPlaca())) { // Validar placa inicia con A
			throw new ParqueaderoException("EL DIA DE HOY LE TOCA PICO Y PLACA, NO ES POSIBLE INGRESAR");
		}

	}

	public boolean placaIniciaPorAYEsHabil(String placa) {
		boolean diaHabil=true;
		int dia = Calendar.DAY_OF_WEEK;
		if(dia==Calendar.SUNDAY) {
			diaHabil = false;
		}else if(dia==Calendar.MONDAY) {
			diaHabil = false;
		}
		LOG.info("CALL: placaIniciaPorAYEsHabil()");
		return ( (placa.toUpperCase().startsWith(Parqueadero.VALIDACIONLETRAA)) && diaHabil);
	}
}
