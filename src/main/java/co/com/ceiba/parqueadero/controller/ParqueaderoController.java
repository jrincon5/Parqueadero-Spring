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

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.repository.ComprobanteJpaRepository;
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
		VehiculoEntity vehiculo = vigilanteService.addCarro(carroModel);
		try{
			if(vehiculo != null) { // Verifica si el vehiculo ingreso correctamente
				vigilanteService.addComprobantePago();
			}
		}catch(Exception e) {
			LOG.info("ERROR");
		}
	}
	
	@PostMapping("/addmoto")
	public void addMoto(@RequestBody MotoModel motoModel) {
		LOG.info("CALL: addMoto()");
		VehiculoEntity vehiculo = vigilanteService.addMoto(motoModel);
		try{
			if(vehiculo != null) { // Verifica si el vehiculo ingreso correctamente
				vigilanteService.addComprobantePago();
			}
		}catch(Exception e) {
			LOG.info("ERROR");
		}
	}
	
	@PostMapping("/removecarro")
	public void removeCarro(@RequestBody String json){
		LOG.info("CALL: removeCarro()");
		jsonObj = new JsonParser().parse(json);
		String placa = jsonObj.getAsJsonObject().get("placa").getAsString();
		try {
			vigilanteService.generarCobro(placa);
		}catch(Exception e) {
			LOG.info("ERROR");
		}
	}
	
	@PostMapping("/removemoto")
	public void removeMoto(@RequestBody String json){
		LOG.info("CALL: removeMoto()");
		jsonObj = new JsonParser().parse(json);
		String placa = jsonObj.getAsJsonObject().get("placa").getAsString();
		try {
			vigilanteService.generarCobro(placa);
		}catch(Exception e) {
			LOG.info("ERROR");
		}
	}
}