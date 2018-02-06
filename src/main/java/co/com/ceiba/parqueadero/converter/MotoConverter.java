package co.com.ceiba.parqueadero.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.service.impl.VigilanteServiceImpl;

@Component("motoConverter")
public class MotoConverter {
	
	private static final Log LOG = LogFactory.getLog(VigilanteServiceImpl.class);
	
	//Entity to Model
	public MotoModel entity2Model(VehiculoEntity vehiculoEntity) {
		LOG.info("CALL: entity2Model()");
		MotoModel motoModel = new MotoModel();
		motoModel.setPlaca(vehiculoEntity.getPlaca());
		motoModel.setParqueado(vehiculoEntity.isParqueado());
		motoModel.setCilindraje(vehiculoEntity.getCilindraje());
		return motoModel;
	}
	
	//Model to Entity
	public VehiculoEntity model2Entity(MotoModel motoModel) {
		LOG.info("CALL: model2Entity()");
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(motoModel.getPlaca());
		vehiculoEntity.setParqueado(motoModel.isParqueado());
		vehiculoEntity.setCilindraje(motoModel.getCilindraje());
		return vehiculoEntity;
	}
}
