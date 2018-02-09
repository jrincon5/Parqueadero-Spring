package co.com.ceiba.parqueadero;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.GregorianCalendar;

import org.aspectj.lang.annotation.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
	
	@Mock
	VehiculoJpaRepository vehiculoJpaRepository;
	
	@Mock
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
		//factura = new ComprobantePagoEntity(parqueaderoModel.getFechaActual().getTime(),null,0,0,true,this.vehiculoCarro);
		MockitoAnnotations.initMocks(this); 
	}
	
	@Test
	public void addCarroValidoTest() {
		when(vehiculoJpaRepository.save(vehiculoCarro)).thenReturn(vehiculoCarro);
		assertNotNull(vigilanteService.addCarro(carro));
		vehiculoJpaRepository.delete(vehiculoCarro);
	}
	
	@Test
	public void addMotoSobreCupoTest() {
		when(vehiculoJpaRepository.save(vehiculoCarro)).thenReturn(vehiculoCarro);
		vigilanteService.addMoto(moto);
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
	}
	
	@Test
	public void addMotoValidaTest() {
		when(vehiculoJpaRepository.save(vehiculoMoto)).thenReturn(vehiculoMoto);
		assertNotNull(vigilanteService.addMoto(moto));
	}
	
	/*@Test   Arreglar esta prueba que sale nula
	public void addComprobantePagoCarroValidoTest() {
		vigilanteService.addCarro(carro);
		factura = new ComprobantePagoEntity(parqueaderoModel.getFechaActual().getTime(),null,0,0,true,this.vehiculoCarro);
		when(new ComprobantePagoEntity()).thenReturn(factura);
		when(comprobanteJpaRepository.save(factura)).thenReturn(factura);
		assertNull(vigilanteService.addComprobantePago());
	}*/
	
	@Test
	public void validarPlacaExistente() {
		when(vehiculoJpaRepository.exists("WSW04D")).thenReturn(true);
		assertFalse(vigilanteService.validarPlacaExistente("WSW04D"));
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
}
