package co.com.ceiba.parqueadero.repository.converter;

import org.springframework.stereotype.Component;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;

@Component("vehiculoConverter")
public class VehiculoConverter{
	
	public VehiculoEntity establecerVehiculoAGuardar(VehiculoModel vehiculoModel) {
		if(vehiculoModel instanceof CarroModel) {
			return carroModel2entity((CarroModel)vehiculoModel);
		}
	return motoModel2entity((MotoModel)vehiculoModel);
	}
	
	public VehiculoEntity carroModel2entity(CarroModel carroModel) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(carroModel.getPlaca());
		vehiculoEntity.setParqueado(true);
		vehiculoEntity.setTipoVehiculo(carroModel.getTipoVehiculo());
		return vehiculoEntity;
	}
	
	public VehiculoEntity motoModel2entity(MotoModel motoModel) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(motoModel.getPlaca());
		vehiculoEntity.setParqueado(true);
		vehiculoEntity.setTipoVehiculo(motoModel.getTipoVehiculo());
		vehiculoEntity.setCilindraje(motoModel.getCilindraje());
		return vehiculoEntity;
	}
}
