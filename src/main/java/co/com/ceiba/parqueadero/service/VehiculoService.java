package co.com.ceiba.parqueadero.service;

import java.util.List;

import co.com.ceiba.parqueadero.entity.VehiculoEnt;

public interface VehiculoService {
	
	public abstract List<VehiculoEnt> listAllVehiculos();
	
	public abstract VehiculoEnt findByPlaca(String placa);
	
	public abstract VehiculoEnt addVehiculo(VehiculoEnt vehiculo);
}
