package co.com.ceiba.parqueadero.repository.converter;

import org.springframework.stereotype.Component;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.Carro;
import co.com.ceiba.parqueadero.model.Moto;
import co.com.ceiba.parqueadero.model.Vehiculo;

@Component("vehiculoConverter")
public class VehiculoConverter {
	
	public VehiculoEntity establecerVehiculoAGuardar(Vehiculo vehiculo) {
		if(vehiculo instanceof Carro) {
			return carroModel2entity((Carro)vehiculo);
		}
		return motoModel2entity((Moto)vehiculo);
	}
	
	public VehiculoEntity carroModel2entity(Carro carro) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(carro.getPlaca().toUpperCase());
		vehiculoEntity.setParqueado(true);
		vehiculoEntity.setTipoVehiculo(carro.getTipoVehiculo());
		return vehiculoEntity;
	}
	
	public VehiculoEntity motoModel2entity(Moto moto) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(moto.getPlaca().toUpperCase());
		vehiculoEntity.setParqueado(true);
		vehiculoEntity.setTipoVehiculo(moto.getTipoVehiculo());
		vehiculoEntity.setCilindraje(moto.getCilindraje());
		return vehiculoEntity;
	}
}