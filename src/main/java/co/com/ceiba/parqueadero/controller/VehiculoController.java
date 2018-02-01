package co.com.ceiba.parqueadero.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.com.ceiba.parqueadero.entity.VehiculoEnt;
import co.com.ceiba.parqueadero.service.VehiculoService;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {
	
	private static final Log LOG = LogFactory.getLog(VehiculoController.class);
	
	@Autowired
	@Qualifier("vehiculoServiceImpl")
	private VehiculoService vehiculoService;
	
	
	@GetMapping("/listvehiculos")
	public ModelAndView listAllVehiculos() {
		LOG.info("CALL: listAllVehiculos()");
		return new ModelAndView("vehiculos","vehiculos",vehiculoService.listAllVehiculos());
	}
	
	@PostMapping("addvehiculo")
	public String addVehiculo(@ModelAttribute("vehiculo") VehiculoEnt vehiculo) {
		return "redirect:/vehiculos/listvehiculos";
	}

}
