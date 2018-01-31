package co.com.ceiba.parqueadero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class Example4Controller {
	
	@GetMapping("/404")
	public ModelAndView error404() {
		return new ModelAndView("404");
	}
	
	@GetMapping("/500")
	public ModelAndView error500() {
		return new ModelAndView("500");
	}
}
