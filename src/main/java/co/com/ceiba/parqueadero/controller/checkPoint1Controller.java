package co.com.ceiba.parqueadero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import co.com.ceiba.parqueadero.service.checkPoint1Service;

@Controller
@RequestMapping("/ejercicio")
public class checkPoint1Controller {
	
	@Autowired
	@Qualifier("checkPointService")
	private checkPoint1Service service;
	
	@GetMapping("/metodo1")
	public RedirectView metodo1() {
		return new RedirectView("/ejercicio/metodo2");
	}
	
	@GetMapping("/metodo2")
	public ModelAndView metodo2() {
		return new ModelAndView("checkPoint","mensaje",service.getLog());
	}
}
