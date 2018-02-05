package co.com.ceiba.parqueadero.service;

import java.util.List;

import co.com.ceiba.parqueadero.entity.FacturaEnt;
import co.com.ceiba.parqueadero.entity.VehiculoEnt;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;

public interface ParqueaderoService {
	
	public abstract List<VehiculoEnt> listAllVehiculos();
	
	public abstract VehiculoEnt findByPlaca(String placa);
	
	public abstract VehiculoEnt addCarro(VehiculoModel carro);
	
	public abstract VehiculoEnt addMoto(VehiculoModel moto);
	
	public abstract FacturaEnt addFechaCarro();
	
	public abstract FacturaEnt addFechaMoto();
}
