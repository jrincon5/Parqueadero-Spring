package co.com.ceiba.parqueadero.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.ComprobantePagoModel;
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
	public void agregarCarro(@RequestBody CarroModel carroModel) {
		LOG.info("CALL: agregarcarro()");
		vigilanteService.agregarCarro(carroModel);
		vigilanteService.agregarComprobantePago();
	}
	
	@PostMapping("/agregarmoto")
	public void agregarMoto(@RequestBody MotoModel motoModel) {
		LOG.info("CALL: agregarmoto()");
		vigilanteService.agregarMoto(motoModel);
		vigilanteService.agregarComprobantePago();
	}
	
	@PostMapping("/removervehiculo")
	public void removerVehiculo(@RequestBody String json){
		LOG.info("CALL: removerCarro()");
		JsonElement jsonObj = new JsonParser().parse(json);
		String placa = jsonObj.getAsJsonObject().get("placa").getAsString();
		vigilanteService.removerVehiculo(placa);
		vigilanteService.generarCobro(placa);
	}
	
	@GetMapping("/consultarvehiculo")
	public List<ComprobantePagoModel> consultarVehiculos(){
		LOG.info("CALL: consultarVehiculo()");
		return vigilanteService.consultarVehiculos();
	}
}