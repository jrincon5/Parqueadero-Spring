package co.com.ceiba.parqueadero.repository.converter;

import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

@Repository("vehiculoRepository")
public abstract class VehiculoConverter implements VehiculoRepository{
	
	@Override
	public VehiculoEntity guardarVehiculo(VehiculoModel vehiculoModel) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(vehiculoModel.getPlaca());
		vehiculoEntity.setParqueado(vehiculoModel.isParqueado());
		vehiculoEntity.setTipoVehiculo(vehiculoModel.getTipoVehiculo());
		return save(vehiculoEntity);
		/*if(vehiculoModel instanceof CarroModel) {
			return carroModel2entity((CarroModel)vehiculoModel);
		}
		if(vehiculoModel instanceof MotoModel) {
			return motoModel2entity((MotoModel)vehiculoModel);
		}
		return null;*/
	}
	
	public VehiculoEntity carroModel2entity(CarroModel carroModel) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(carroModel.getPlaca());
		vehiculoEntity.setParqueado(carroModel.isParqueado());
		vehiculoEntity.setTipoVehiculo(carroModel.getTipoVehiculo());
		return save(vehiculoEntity);
	}
	
	public VehiculoEntity motoModel2entity(MotoModel motoModel) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(motoModel.getPlaca());
		vehiculoEntity.setParqueado(motoModel.isParqueado());
		vehiculoEntity.setTipoVehiculo(motoModel.getTipoVehiculo());
		vehiculoEntity.setCilindraje(motoModel.getCilindraje());
		return save(vehiculoEntity);
	}
}
