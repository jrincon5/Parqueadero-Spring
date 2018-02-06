package co.com.ceiba.parqueadero.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.service.VigilanteService;

@RestController
@RequestMapping("/parqueadero")
public class ParqueaderoController {
	
	private static final Log LOG = LogFactory.getLog(ParqueaderoController.class);
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
	
	@PostMapping("/addcarro")
	public void addCarro(@RequestBody CarroModel carroModel) {
		LOG.info("CALL: addCarro()");
		vigilanteService.addCarro(carroModel);
	}
	
	@PostMapping("/addmoto")
	public void addMoto(@RequestBody MotoModel motoModel) {
		LOG.info("CALL: addMoto()");
		vigilanteService.addMoto(motoModel);
	}
	
	@PostMapping("/removecarro")
	public void removeCarro(@RequestBody String placa) {
		LOG.info("CALL: addCarro()");
		vigilanteService.removeCarro(placa);
	}

}