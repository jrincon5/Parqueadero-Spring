package co.com.ceiba.parqueadero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/pet")
public class peticionesController {
	
	//Redireccion numero 1
	@GetMapping("/1")
	public String redirect() {
		int a = 1/0;
		return "redirect:/pet/get";
	}
	
	//Redireccion numero 2
		@GetMapping("/2")
		public RedirectView redirect2() {
			return new RedirectView("/pet/get");
		}
	
	//localhost:8090/pet/get?nm=Julian
	@GetMapping("/get")
	public ModelAndView render(@RequestParam(name="nm", required=false) String name) {
		return new ModelAndView("peticion","nm_in_model",name);
	}
	
	//localhost:8090/pet/getA/Julian
	@GetMapping("/getA/{nm}")
	public ModelAndView renderget(@PathVariable("nm") String name) {
		return new ModelAndView("peticion","nm_in_model",name);
	}
}
