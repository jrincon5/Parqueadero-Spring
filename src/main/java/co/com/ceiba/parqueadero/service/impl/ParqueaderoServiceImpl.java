package co.com.ceiba.parqueadero.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.entity.VehiculoEnt;
import co.com.ceiba.parqueadero.repository.ParqueaderoJpaRepository;
import co.com.ceiba.parqueadero.service.ParqueaderoService;

@Service("parqueaderoServiceImpl")
public class ParqueaderoServiceImpl implements ParqueaderoService{
	
	private static final Log LOG = LogFactory.getLog(ParqueaderoServiceImpl.class);
	
	@Autowired
	@Qualifier("parqueaderoJpaRepository")
	private ParqueaderoJpaRepository parqueaderoJpaRepository;

	@Override
	public List<VehiculoEnt> listAllVehiculos() {
		LOG.info("CALL: listAllVehiculos()");
		return parqueaderoJpaRepository.findAll();
	}

	@Override
	public VehiculoEnt findByPlaca(String placa) {
		return parqueaderoJpaRepository.findByPlaca(placa);
	}

	@Override
	public VehiculoEnt addVehiculo(VehiculoEnt vehiculo) {
		LOG.info("CALL: addVehiculo()");
		return parqueaderoJpaRepository.save(vehiculo);
	}

}
