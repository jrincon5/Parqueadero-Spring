package co.com.ceiba.parqueadero.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.parqueadero.model.Carro;
import co.com.ceiba.parqueadero.model.ComprobantePago;
import co.com.ceiba.parqueadero.model.DatosEntrada;
import co.com.ceiba.parqueadero.model.Moto;
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
	public void agregarCarro(@RequestBody Carro carro) {
		LOG.info("CALL: agregarcarro()");
		vigilanteService.ingresarVehiculo(carro);
	}
	
	@PostMapping("/agregarmoto")
	public void agregarMoto(@RequestBody Moto moto) {
		LOG.info("CALL: agregarmoto()");
		vigilanteService.ingresarVehiculo(moto);
	}
	
	@PutMapping("/removervehiculo")
	public ComprobantePago removerVehiculo(@RequestBody String json){
		LOG.info("CALL: removerVehiculo()");
		return vigilanteService.removerVehiculo(json);
	}
	
	@GetMapping("/consultarvehiculo")
	public List<DatosEntrada> consultarVehiculos(){
		LOG.info("CALL: consultarVehiculo()");
		return vigilanteService.consultarVehiculos();
	}
}