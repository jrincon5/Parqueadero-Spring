package co.com.ceiba.parqueadero.service;

import java.util.Date;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;

public interface VigilanteService {
	
	public abstract VehiculoEntity agregarCarro(CarroModel carro);
	
	public abstract VehiculoEntity agregarMoto(MotoModel moto);
	
	public abstract VehiculoEntity removerVehiculo(String placa);
	
	public abstract ComprobantePagoEntity agregarComprobantePago();
	
	public abstract ComprobantePagoEntity generarCobro(String placa);
	
	public abstract long calcularHorasTotales(Date entrada, FechaModel salida);
	
	public abstract long calcularTotalAPagar(Date entrada, FechaModel salida, int valorDia, int valorHora);
	
	public abstract long generarAumentoMotosAltoCilindraje(int cilindraje);
	
	public abstract boolean validarEspacioCarros();
	
	public abstract boolean validarEspacioMotos();
	
	public abstract boolean picoYPlaca(String placa, int diaSemana);
	
	public abstract VehiculoEntity mapearModelAEntidad(VehiculoModel vehiculo);
}
