package co.com.ceiba.parqueadero.service.impl;

import java.util.List;

import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.model.validacionesingreso.ValidacionIngresoVehiculo;


public class VigilianteSevicePalmas extends VigilanteServiceImpl {
	public VigilianteSevicePalmas(List<ValidacionIngresoVehiculo> validacionesIngreso) {
		super(validacionesIngreso);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void ingresarVehiculo(VehiculoModel vehiculoModel) {
	}
}
