package co.com.ceiba.parqueadero.service;

import java.util.List;

import co.com.ceiba.parqueadero.model.ComprobantePago;
import co.com.ceiba.parqueadero.model.DatosEntrada;
import co.com.ceiba.parqueadero.model.Vehiculo;

public interface VigilanteService {
	
	public abstract void ingresarVehiculo(Vehiculo vehiculo);
	
	public abstract ComprobantePago removerVehiculo(String placa);
	
	public abstract List<DatosEntrada> consultarVehiculos();
}
