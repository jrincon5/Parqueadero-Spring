package co.com.ceiba.parqueadero.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorsController {
	
	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView error500() {
		return new ModelAndView("error/500");
	}
}
