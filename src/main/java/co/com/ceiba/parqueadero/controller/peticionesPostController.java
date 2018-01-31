package co.com.ceiba.parqueadero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.com.ceiba.parqueadero.model.Vehiculo;

@Controller
@RequestMapping("/pet")
public class peticionesPostController {
	
	@GetMapping("/post")
	public String get(Model m) {
		m.addAttribute("vehiculosend",new Vehiculo());
		return "peticionpost";
	}
	
	@PostMapping("/post")
	public ModelAndView post(@ModelAttribute("vehiculosend") Vehiculo veh) {
		return new ModelAndView("peticionpostresultado","vehiculorecive",veh);
	}
}
