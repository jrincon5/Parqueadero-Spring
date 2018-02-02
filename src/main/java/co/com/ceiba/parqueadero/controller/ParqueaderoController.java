package co.com.ceiba.parqueadero.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.com.ceiba.parqueadero.entity.VehiculoEnt;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.service.ParqueaderoService;

@Controller
@RequestMapping("/parqueadero")
public class ParqueaderoController {
	
	private static final Log LOG = LogFactory.getLog(ParqueaderoController.class);
	
	@Autowired
	@Qualifier("parqueaderoServiceImpl")
	private ParqueaderoService parqueaderoService;	
	
	@GetMapping("/listvehiculos")
	public ModelAndView listAllVehiculos() {
		LOG.info("CALL: listAllVehiculos()");
		ModelAndView mav= new ModelAndView("vehiculo");
		mav.addObject("vehiculos",parqueaderoService.listAllVehiculos());
		mav.addObject("vehiculo", new VehiculoEnt());
		return mav;
	}
	
	@PostMapping("/addvehiculo")
	public String addVehiculo(@ModelAttribute("vehiculo") VehiculoModel vehiculo, BindingResult result) {
		LOG.info("CALL: addVehiculo()");
		if(result.hasErrors()) {
			return "redirect:/parqueadero/listvehiculos";
		}else {
			parqueaderoService.addCarro(vehiculo);
			return "redirect:/parqueadero/listvehiculos";
		}		
	}

}