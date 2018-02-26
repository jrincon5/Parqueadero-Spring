package co.com.ceiba.parqueadero.validation.entervalidation;

import java.util.Calendar;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;

public class ValidacionPlacaIniciaPorA implements ValidacionIngresoVehiculo {

	@Override
	public void validar(VehiculoModel vehiculoModel) {
		if (placaIniciaPorAYEsHabil(vehiculoModel.getPlaca(), Calendar.DAY_OF_WEEK)) { // Validar placa inicia con A
			throw new ParqueaderoException("EL DIA DE HOY LE TOCA PICO Y PLACA, NO ES POSIBLE INGRESAR");
		}

	}

	public boolean placaIniciaPorAYEsHabil(String placa, int diaSemana) {
		boolean diaNoHabil=(diaSemana == Calendar.SUNDAY || diaSemana == Calendar.MONDAY);
		return ( (placa.toUpperCase().startsWith(ParqueaderoModel.VALIDACIONLETRAA)) && diaNoHabil );
	}
}
