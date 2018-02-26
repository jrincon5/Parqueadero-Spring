package co.com.ceiba.parqueadero.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.ComprobantePagoModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.repository.ComprobanteRepository;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;
import co.com.ceiba.parqueadero.service.VigilanteService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/parqueadero")
public class VigilanteController {
	
	private static final Log LOG = LogFactory.getLog(VigilanteController.class);
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
	
	@Autowired
	@Qualifier("vehiculoRepository")
	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	@Qualifier("comprobanteRepository")
	ComprobanteRepository comprobanteRepository;
	
		
	@PostMapping("/agregarcarro")
	public void agregarCarro(@RequestBody CarroModel carroModel) {
		LOG.info("CALL: agregarcarro()");
		vigilanteService.ingresarVehiculo(carroModel);
	}
	
	@PostMapping("/agregarmoto")
	public void agregarMoto(@RequestBody MotoModel motoModel) {
		LOG.info("CALL: agregarmoto()");
		vigilanteService.ingresarVehiculo(motoModel);
	}
	
	@PostMapping("/removervehiculo")
	public void removerVehiculo(@RequestBody String placa){
		LOG.info("CALL: removerCarro()");
		vigilanteService.removerVehiculo(placa);
	}
	
	@GetMapping("/consultarvehiculo")
	public List<ComprobantePagoModel> consultarVehiculos(){
		LOG.info("CALL: consultarVehiculo()");
		return vigilanteService.consultarVehiculos();
	}
	
	@GetMapping("/consultarcomprobantes")
	public List<ComprobantePagoEntity> consultarComprobantes(){
		LOG.info("CALL: consultarComprobantes()");
		return comprobanteRepository.findAll();
	}
	
	@PostMapping("/consultarcomprobante")
	public ComprobantePagoEntity consultarComprobante(@RequestBody String json){
		LOG.info("CALL: consultarComprobante()");
		VehiculoEntity placaVehiculo = vehiculoRepository.findOne(json);
		return comprobanteRepository.findByPlaca(placaVehiculo);
	}
}