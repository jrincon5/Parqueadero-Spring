package co.com.ceiba.parqueadero.service;

import java.util.Date;
import java.util.List;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.MotoModel;

public interface VigilanteService {
	
	public abstract VehiculoEntity addCarro(CarroModel carro);
	
	public abstract VehiculoEntity addMoto(MotoModel moto);
	
	public abstract VehiculoEntity removeVehiculo(String placa);
	
	public abstract ComprobantePagoEntity addComprobantePago();
	
	public abstract ComprobantePagoEntity generarCobro(String placa);
	
	public abstract long calcularHorasTotales(Date entrada, FechaModel salida);
	
	public abstract long generarCobroCarros(Date entrada, FechaModel salida);
	
	public abstract long generarCobroMotos(Date entrada, FechaModel salida);
	
	public abstract long generarAumentoMotosAltoCilindraje(int cilindraje);
	
	public abstract boolean validarEspacioCarros();
	
	public abstract boolean validarEspacioMotos();
	
	public abstract boolean picoYPlaca(String placa, int diaSemana);
}
