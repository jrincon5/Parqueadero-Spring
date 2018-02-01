package co.com.ceiba.parqueadero.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import co.com.ceiba.parqueadero.controller.peticionesPostController;

@Component("helloComponent")
public class helloComponent {
	private static final Log LOG = LogFactory.getLog(helloComponent.class);
	
	public void sayHello() {
		LOG.info("Hello from example component");
	}
}
