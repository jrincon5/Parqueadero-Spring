package co.com.ceiba.parqueadero;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.repository.ComprobanteJpaRepository;
import co.com.ceiba.parqueadero.repository.VehiculoJpaRepository;
import co.com.ceiba.parqueadero.service.VigilanteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VigilanteTest {
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
	
	@Autowired
	@Qualifier("vehiculoJpaRepository")
	VehiculoJpaRepository vehiculoJpaRepository;
	
	@Autowired
	@Qualifier("comprobanteJpaRepository")
	ComprobanteJpaRepository comprobanteJpaRepository;
	
	VehiculoEntity vehiculoCarro;
	VehiculoEntity vehiculoMoto;
	CarroModel carro;
	MotoModel moto;
	Parqueadero parqueaderoModel;
	ComprobantePagoEntity factura;
	
	@Before
	public void arrange() {
		carro = new CarroModel("WSW04D",true);
		moto = new MotoModel("WSW04D",true,100);
		vehiculoCarro = new VehiculoEntity("WSW04D", true, 0, "Carro");
		vehiculoMoto = new VehiculoEntity("WSW04D", true, 100, "Moto");
	}
	
	@Test
	public void addCarroValidoTest() {
		assertNotNull(vigilanteService.addCarro(carro));
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void addCarroSobreCupoTest() {
		vigilanteService.addCarro(new CarroModel("AAA111",true));
		vigilanteService.addCarro(new CarroModel("AAA112",true));
		vigilanteService.addCarro(new CarroModel("AAA113",true));
		vigilanteService.addCarro(new CarroModel("AAA114",true));
		vigilanteService.addCarro(new CarroModel("AAA115",true));
		vigilanteService.addCarro(new CarroModel("AAA116",true));
		vigilanteService.addCarro(new CarroModel("AAA117",true));
		vigilanteService.addCarro(new CarroModel("AAA118",true));
		vigilanteService.addCarro(new CarroModel("AAA119",true));
		vigilanteService.addCarro(new CarroModel("AAA110",true));
		vigilanteService.addCarro(new CarroModel("AAA121",true));
		vigilanteService.addCarro(new CarroModel("AAA122",true));
		vigilanteService.addCarro(new CarroModel("AAA123",true));
		vigilanteService.addCarro(new CarroModel("AAA124",true));
		vigilanteService.addCarro(new CarroModel("AAA125",true));
		vigilanteService.addCarro(new CarroModel("AAA126",true));
		vigilanteService.addCarro(new CarroModel("AAA127",true));
		vigilanteService.addCarro(new CarroModel("AAA128",true));
		vigilanteService.addCarro(new CarroModel("AAA129",true));
		vigilanteService.addCarro(new CarroModel("AAA120",true));
		vigilanteService.addCarro(new CarroModel("AAA130",true));
		assertNull(vigilanteService.addCarro(carro));
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void addMotoValidaTest() {
		assertNotNull(vigilanteService.addMoto(moto));
		vehiculoJpaRepository.deleteAll();;
	}
	
	@Test
	public void addMotoSobreCupoTest() {
		vigilanteService.addMoto(new MotoModel("AAA111",true,100));
		vigilanteService.addMoto(new MotoModel("AAA112",true,100));
		vigilanteService.addMoto(new MotoModel("AAA113",true,100));
		vigilanteService.addMoto(new MotoModel("AAA114",true,100));
		vigilanteService.addMoto(new MotoModel("AAA115",true,100));
		vigilanteService.addMoto(new MotoModel("AAA116",true,100));
		vigilanteService.addMoto(new MotoModel("AAA117",true,100));
		vigilanteService.addMoto(new MotoModel("AAA118",true,100));
		vigilanteService.addMoto(new MotoModel("AAA119",true,100));
		vigilanteService.addMoto(new MotoModel("AAA110",true,100));
		vigilanteService.addMoto(new MotoModel("AAA120",true,100));
		assertNull(vigilanteService.addMoto(moto));
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void addComprobantePagoCarroValidoTest() {
		vigilanteService.addCarro(carro);		
		assertNotNull(vigilanteService.addComprobantePago());
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void removeVehiculoTest() {
		vigilanteService.addCarro(carro);
		assertNotNull(vigilanteService.removeVehiculo("WSW04D"));
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void calcularHorasExactasTotalesTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 5, 8, 0,0);
		assertEquals(101, vigilanteService.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularHorasInexactasConMinutosDeMasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
    	FechaModel salida = new FechaModel(2017, 1, 1, 8, 1,0);
		assertEquals(6, vigilanteService.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularHorasInexactasConMinutossDeMenosTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
    	FechaModel salida = new FechaModel(2017, 1, 1, 7, 59,0);
		assertEquals(5, vigilanteService.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void generarCobroCarrosHorasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 7, 59,0);
		assertEquals(5000, vigilanteService.generarCobroCarros(entrada, salida));
		
	}
	
	@Test
	public void generarCobroCarrosDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 15, 0,0);
		assertEquals(8000, vigilanteService.generarCobroCarros(entrada, salida));
		
	}
	
	@Test
	public void generarCobroCarrosHorasDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 2, 3, 1,0);
		assertEquals(9000, vigilanteService.generarCobroCarros(entrada, salida));
	}
	
	@Test
	public void generarCobroMotosHorasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 7, 59,0);
		assertEquals(2500, vigilanteService.generarCobroMotos(entrada, salida));
		
	}
	
	@Test
	public void generarCobroMotosDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 15, 0,0);
		assertEquals(4000, vigilanteService.generarCobroMotos(entrada, salida));
		
	}
	
	@Test
	public void generarCobroMotosHorasDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 2, 3, 1,0);
		assertEquals(4500, vigilanteService.generarCobroMotos(entrada, salida));
	}
	
	@Test
	public void picoYPlacaDomingo() {
		assertTrue(vigilanteService.picoYPlaca("AAA111", 1));
	}
	
	@Test
	public void picoYPlacaLunes() {
		assertTrue(vigilanteService.picoYPlaca("AAA111", 2));
	}
	
	@Test
	public void picoYPlacaDiaHabil() {
		assertFalse(vigilanteService.picoYPlaca("AAA111", 4));
	}
	
	@Test
	public void generarAumentoMotosAltoCilindrajeTest() {
		assertEquals(2000, vigilanteService.generarAumentoMotosAltoCilindraje(600));
	}
	
	@Test
	public void noGenerarAumentoMotosAltoCilindrajeTest() {
		assertEquals(0, vigilanteService.generarAumentoMotosAltoCilindraje(400));
	}
	
	@Test
	public void generarCobroVehiculosCarroTest() {
		vigilanteService.addCarro(carro);
		vigilanteService.addComprobantePago();
		vigilanteService.removeVehiculo("WSW04D");
		assertNotNull(vigilanteService.generarCobro("WSW04D"));
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void generarCobroVehiculosMotoTest() {
		vigilanteService.addMoto(moto);
		vigilanteService.addComprobantePago();
		vigilanteService.removeVehiculo("WSW04D");
		assertNotNull(vigilanteService.generarCobro("WSW04D"));
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
}
