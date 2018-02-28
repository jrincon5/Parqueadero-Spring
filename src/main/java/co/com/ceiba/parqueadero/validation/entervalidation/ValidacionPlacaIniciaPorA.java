package co.com.ceiba.parqueadero.validation.entervalidation;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;

public class ValidacionPlacaIniciaPorA implements ValidacionIngresoVehiculo {
	
	private static final Log LOG = LogFactory.getLog(ValidacionPlacaIniciaPorA.class);

	@Override
	public void validar(VehiculoModel vehiculoModel) {
		if (placaIniciaPorAYEsHabil(vehiculoModel.getPlaca(), Calendar.DAY_OF_WEEK)) { // Validar placa inicia con A
			throw new ParqueaderoException("EL DIA DE HOY LE TOCA PICO Y PLACA, NO ES POSIBLE INGRESAR");
		}

	}

	public boolean placaIniciaPorAYEsHabil(String placa, int diaSemana) {
		boolean diaHabil=true;
		if(diaSemana==Calendar.SUNDAY) diaHabil = false;
		if(diaSemana==Calendar.MONDAY) diaHabil = false;
		LOG.info("CALL: placaIniciaPorAYEsHabil()");
		return ( (placa.toUpperCase().startsWith(ParqueaderoModel.VALIDACIONLETRAA)) && diaHabil);
	}
}
