package co.com.ceiba.parqueadero.converter;

import org.springframework.stereotype.Component;

import co.com.ceiba.parqueadero.entity.VehiculoEnt;
import co.com.ceiba.parqueadero.model.VehiculoModel;

@Component("vehiculoConverter")
public class VehiculoConverter {
	
	//Entity to Model
	public VehiculoModel entity2Model(VehiculoEnt vehiculoEnt) {
		VehiculoModel vehiModel = new VehiculoModel();
		vehiModel.setPlaca(vehiculoEnt.getPlaca());
		vehiModel.setCilindraje(vehiculoEnt.getCilindraje());
		return vehiModel;
	}
	
	//Model to Entity
	public VehiculoEnt model2Entity(VehiculoModel vehiculoModel) {
		VehiculoEnt vehiEnt = new VehiculoEnt();
		vehiEnt.setPlaca(vehiculoModel.getPlaca());
		vehiEnt.setCilindraje(vehiculoModel.getCilindraje());
		return vehiEnt;
	}
}
