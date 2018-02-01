package co.com.ceiba.parqueadero.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.component.helloComponent;
import co.com.ceiba.parqueadero.service.checkPoint1Service;

@Service("checkPointService")
public class checkPoint1ServiceImpl implements checkPoint1Service{
	private static final Log LOG = LogFactory.getLog(helloComponent.class);

	@Override
	public String getLog() {
		LOG.info("HELLO FROM SERVICE CHECKPOINT");
		return "Mensaje";
	}
}
