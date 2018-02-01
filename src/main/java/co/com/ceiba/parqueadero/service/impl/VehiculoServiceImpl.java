package co.com.ceiba.parqueadero.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.entity.VehiculoEnt;
import co.com.ceiba.parqueadero.repository.VehiculoJpaRepository;
import co.com.ceiba.parqueadero.service.VehiculoService;

@Service("vehiculoServiceImpl")
public class VehiculoServiceImpl implements VehiculoService{
	
	private static final Log LOG = LogFactory.getLog(VehiculoServiceImpl.class);
	
	@Autowired
	@Qualifier("vehiculoJpaRepository")
	private VehiculoJpaRepository vehiculoJpaRepository;

	@Override
	public List<VehiculoEnt> listAllVehiculos() {
		LOG.info("CALL: listAllVehiculos()");
		return vehiculoJpaRepository.findAll();
	}

	@Override
	public VehiculoEnt findByPlaca(String placa) {
		return vehiculoJpaRepository.findByPlaca(placa);
	}

	@Override
	public VehiculoEnt addVehiculo(VehiculoEnt vehiculo) {
		LOG.info("CALL: addVehiculo()");
		return vehiculoJpaRepository.save(vehiculo);
	}

}
