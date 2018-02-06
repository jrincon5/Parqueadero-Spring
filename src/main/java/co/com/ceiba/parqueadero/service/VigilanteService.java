package co.com.ceiba.parqueadero.service;

import java.util.List;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.MotoModel;

public interface VigilanteService {
	
	public abstract List<VehiculoEntity> listAllVehiculos();
	
	public abstract VehiculoEntity findByPlaca(String placa);
	
	public abstract VehiculoEntity addCarro(CarroModel carro);
	
	public abstract VehiculoEntity addMoto(MotoModel moto);
	
	public abstract VehiculoEntity removeCarro(String placa);
	
	public abstract ComprobantePagoEntity addFechaCarro();
	
	public abstract ComprobantePagoEntity addFechaMoto();
}
