package co.com.ceiba.parqueadero.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.com.ceiba.parqueadero.component.helloComponent;
import co.com.ceiba.parqueadero.model.Vehiculo;

@Controller
@RequestMapping("/pet")
public class peticionesPostController {
	private static final Log LOGGER = LogFactory.getLog(peticionesPostController.class);
	
	@Autowired  //Anotacion para inyeccion de dependencia
	@Qualifier("helloComponent") // Componente a inyectar
	private helloComponent helloComponent;
	
	@GetMapping("/post")	
	public String get(Model m) {
		helloComponent.sayHello();
		LOGGER.info("INFO TRACE");
		LOGGER.warn("WARNING TRACE");
		LOGGER.error("ERROR TRACE");
		LOGGER.debug("DEBUG TRACE");
		m.addAttribute("vehiculosend",new Vehiculo());
		return "peticionpost";
	}
	
	@PostMapping("/post")
	public ModelAndView post(@Valid @ModelAttribute("vehiculosend") Vehiculo veh, BindingResult result) {
		LOGGER.info("METHOD: 'post' -- PARAMS: '"+veh+"'");
		LOGGER.info("TEMPLATE: 'peticionpostresultado' -- DATA: '"+veh+"'");
		if(result.hasErrors()) {
			return new ModelAndView("peticionpost","vehiculorecive",veh);
		}
		return new ModelAndView("peticionpostresultado","vehiculorecive",veh);
	}
}