package co.com.ceiba.parqueadero.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.component.helloComponent;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.service.helloService;

@Service("helloService")
public class helloServiceImpl implements helloService{
	private static final Log LOG = LogFactory.getLog(helloComponent.class);

	@Override
	public List<Vehiculo> getListVehiculos() {
		List<Vehiculo> cars= new ArrayList<>();
		cars.add(new Vehiculo("Tom",35));
		cars.add(new Vehiculo("Martha",40));
		cars.add(new Vehiculo("Samantha",32));
		cars.add(new Vehiculo("Will",19));
		LOG.info("HELLO FROM SERVICE");
		return cars;
	}

}
