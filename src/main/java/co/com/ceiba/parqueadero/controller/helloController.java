package co.com.ceiba.parqueadero.controller;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import co.com.ceiba.parqueadero.model.persona;

@Controller
@RequestMapping("say")
public class helloController {

	//Llamar una vista (REDIRECCION)
	@GetMapping("/hello1")
	public String helloWorld(Model model) {
		model.addAttribute("name", "Así paso parametros 1");
		return "hello";
	}
	
	//Insertar datos en plantillas
	@GetMapping("/hello2")
	public ModelAndView exampleMav() {
		return new ModelAndView("hello","name","Así paso parametros 2");
	}
	
	/*
	//Insertar varios datos
	@GetMapping("/persona1")
	public String personaDatos(Model model) {
		model.addAttribute("persona",new persona("Julian",22));
		return "persona";
	}
	
	//Insertar varios datos 2
	@GetMapping("/persona2")
	public ModelAndView personaDatosMav() {
		ModelAndView mav=new ModelAndView("persona");
		mav.addObject("persona",new persona("Sara",34));
		return mav;
	}
	
	//Insertar lista de datos
	@GetMapping("/gente1")
	public String genteDatos(Model model) {
		model.addAttribute("gente",getPeople());
		return "gente";
	}
		
	//Insertar lista de datos 2
	@GetMapping("/gente2")
	public ModelAndView genteDatosMav() {
		ModelAndView mav=new ModelAndView("gente");
		mav.addObject("gente",getPeople());
		return mav;
	}
	
	private List<persona> getPeople(){
		List<persona> people= new ArrayList<>();
		people.add(new persona("Tom",35));
		people.add(new persona("Martha",40));
		people.add(new persona("Samantha",32));
		people.add(new persona("Will",19));
		return people;
	}*/
}
