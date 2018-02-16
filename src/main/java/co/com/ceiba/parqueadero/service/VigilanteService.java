package co.com.ceiba.parqueadero.service;

import java.util.List;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.ComprobantePagoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;

public interface VigilanteService {
	
	public abstract void ingresarVehiculo(VehiculoModel vehiculo);
	
	public abstract VehiculoEntity removerVehiculo(String placa);
	
	public abstract List<ComprobantePagoModel> consultarVehiculos();
}
