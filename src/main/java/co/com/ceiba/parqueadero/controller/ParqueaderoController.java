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
	private JsonElement jsonObj;
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
		
	@PostMapping("/addcarro")
	public void addCarro(@RequestBody CarroModel carroModel) {
		LOG.info("CALL: addCarro()");
		vigilanteService.addCarro(carroModel);
		vigilanteService.addComprobantePago();
	}
	
	@PostMapping("/addmoto")
	public void addMoto(@RequestBody MotoModel motoModel) {
		LOG.info("CALL: addMoto()");
		vigilanteService.addMoto(motoModel);
		vigilanteService.addComprobantePago();
	}
	
	@PostMapping("/removevehiculo")
	public void removeCarro(@RequestBody String json){
		LOG.info("CALL: removeCarro()");
		jsonObj = new JsonParser().parse(json);
		String placa = jsonObj.getAsJsonObject().get("placa").getAsString();
		vigilanteService.removeVehiculo(placa);
		vigilanteService.generarCobro(placa);
	}
}