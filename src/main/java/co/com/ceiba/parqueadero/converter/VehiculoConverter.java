package co.com.ceiba.parqueadero.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import co.com.ceiba.parqueadero.entity.VehiculoEnt;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.service.impl.ParqueaderoServiceImpl;

@Component("vehiculoConverter")
public class VehiculoConverter {
	
	private static final Log LOG = LogFactory.getLog(ParqueaderoServiceImpl.class);
	
	//Entity to Model
	public VehiculoModel entity2Model(VehiculoEnt vehiculoEnt) {
		LOG.info("CALL: entity2Model()");
		VehiculoModel vehiModel = new VehiculoModel();
		vehiModel.setPlaca(vehiculoEnt.getPlaca());
		vehiModel.setCilindraje(vehiculoEnt.getCilindraje());
		return vehiModel;
	}
	
	//Model to Entity
	public VehiculoEnt model2Entity(VehiculoModel vehiculoModel) {
		LOG.info("CALL: model2Entity()");
		VehiculoEnt vehiEnt = new VehiculoEnt();
		vehiEnt.setPlaca(vehiculoModel.getPlaca());
		vehiEnt.setCilindraje(vehiculoModel.getCilindraje());
		return vehiEnt;
	}
}
