package co.com.ceiba.parqueadero.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.service.helloService;

//import co.com.ceiba.parqueadero.model.persona;

@Controller
@RequestMapping("/say")
public class helloController {
	
	@Autowired
	@Qualifier("helloService")
	private helloService helloService; // Declarar la interface

	//Llamar una vista (REDIRECCION)
	@GetMapping("/hello1")
	public String helloWorld(/*Model model*/) {
		//model.addAttribute("name", "Asi paso parametros 1");
		return "hello";
	}
	
	//Insertar datos en plantillas
	@GetMapping("/hello2")
	public ModelAndView exampleMav() {
		return new ModelAndView("hello","name","Asi paso parametros 2");
	}
	
	
	//Insertar varios datos
	@GetMapping("/vehiculo1")
	public String personaDatos(Model model) {
		model.addAttribute("vehiculo",new Vehiculo("WSW04D",22));
		return "vehiculo";
	}
	
	//Insertar varios datos 2
	@GetMapping("/vehiculo2")
	public ModelAndView personaDatosMav() {
		ModelAndView mav=new ModelAndView("vehiculo");
		mav.addObject("vehiculo",new Vehiculo("ABA95D",22));
		return mav;
	}
	
	
	//Insertar lista de datos
	@GetMapping("/vehiculos1")
	public String genteDatos(Model model) {
		model.addAttribute("vehiculos",helloService.getListVehiculos());
		return "vehiculos";
	}
		
	//Insertar lista de datos 2
	@GetMapping("/vehiculos2")
	public ModelAndView genteDatosMav() {
		ModelAndView mav=new ModelAndView("vehiculos");
		mav.addObject("vehiculos",helloService.getListVehiculos());
		return mav;
	}
	
}
