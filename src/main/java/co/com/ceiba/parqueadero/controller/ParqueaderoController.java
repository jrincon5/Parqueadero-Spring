package co.com.ceiba.parqueadero.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
		
	@PostMapping("/agregarcarro")
	public void addCarro(@RequestBody CarroModel carroModel) {
		LOG.info("CALL: agregarcarro()");
		vigilanteService.agregarCarro(carroModel);
		vigilanteService.agregarComprobantePago();
	}
	
	@PostMapping("/agregarmoto")
	public void addMoto(@RequestBody MotoModel motoModel) {
		LOG.info("CALL: agregarmoto()");
		vigilanteService.agregarMoto(motoModel);
		vigilanteService.agregarComprobantePago();
	}
	
	@PostMapping("/removervehiculo")
	public void removeCarro(@RequestBody String json){
		LOG.info("CALL: removeCarro()");
		JsonElement jsonObj = new JsonParser().parse(json);
		String placa = jsonObj.getAsJsonObject().get("placa").getAsString();
		vigilanteService.removerVehiculo(placa);
		vigilanteService.generarCobro(placa);
	}
}