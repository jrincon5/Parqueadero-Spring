package co.com.ceiba.parqueadero.validation.entervalidation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;

public class ValidacionVehiculoYaParqueado implements ValidacionIngresoVehiculo{
	
	private static final Log LOG = LogFactory.getLog(ValidacionVehiculoYaParqueado.class);
	
	@Autowired
	@Qualifier("vehiculoRepository")
	VehiculoRepository vehiculoRepository;
	
	public ValidacionVehiculoYaParqueado(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(VehiculoModel vehiculoModel) {
		if(validarIsParqueado(vehiculoModel.getPlaca())) {
			throw new ParqueaderoException("LA PLACA YA SE ENCUENTRA PARQUEADA");
		}
	}
	
	public boolean validarIsParqueado(String placa) {
		LOG.info("CALL: validarIsParqueado()");
		if(vehiculoRepository.exists(placa)) {
			return vehiculoRepository.findOne(placa).isParqueado();	
		}
		return false;
	}
}
