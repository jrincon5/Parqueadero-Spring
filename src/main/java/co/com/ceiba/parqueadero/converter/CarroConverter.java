package co.com.ceiba.parqueadero.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.service.impl.VigilanteServiceImpl;

@Component("carroConverter")
public class CarroConverter {
	
	private static final Log LOG = LogFactory.getLog(VigilanteServiceImpl.class);
	
	//Model to Entity
	public VehiculoEntity model2Entity(CarroModel carroModel) {
		LOG.info("CALL: model2Entity()");
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(carroModel.getPlaca());
		vehiculoEntity.setParqueado(carroModel.isParqueado());
		return vehiculoEntity;
	}
}
