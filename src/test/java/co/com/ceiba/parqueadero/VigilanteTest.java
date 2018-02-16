package co.com.ceiba.parqueadero;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.ComprobantePagoModel;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
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
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteMock;
	
	@Autowired
	@Qualifier("vehiculoJpaRepository")
	VehiculoJpaRepository vehiculoJpaRepository;
	
	@Autowired
	@Qualifier("comprobanteJpaRepository")
	ComprobanteJpaRepository comprobanteJpaRepository;
	
	CarroModel carro;
	MotoModel moto;
	ParqueaderoModel parqueaderoModel;
	
	@Before
	public void arrange() {
		MockitoAnnotations.initMocks(this);
		carro = new CarroModel("WSW04D",true);
		moto = new MotoModel("WSW04D",true,100);
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
	
	@After
	public void clean() {
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void agregarCarroValidoTest() {
		assertNotNull(vigilanteService.agregarCarro(carro));
		vehiculoJpaRepository.deleteAll();
	}
	
	/*@Test
	public void agregarCarroEnPicoYPlacaTest() {
		carro = new CarroModel("ASW04D",true);
		when(vigilanteMock.picoYPlaca("ASW04D", Calendar.MONDAY)).thenReturn(true);
		assertNull(vigilanteService.agregarCarro(carro));
		vehiculoJpaRepository.deleteAll();
	}*/
	
	@Test(expected = ParqueaderoException.class)
	public void agregarCarroSobreCupoTest() {
		for (int i=0;i<=10;i++) {
			vigilanteService.agregarCarro(new CarroModel("AAA11"+i,true));
			vigilanteService.agregarCarro(new CarroModel("AAA12"+i,true));
		}
	}
	
	@Test
	public void consultarVehiculoTest() {
		vigilanteService.agregarCarro(carro);
		vigilanteService.agregarComprobantePago();
		List<ComprobantePagoModel> comprobantes = new ArrayList<>();
		comprobantes.addAll(vigilanteService.consultarVehiculos());
		assertEquals(comprobantes.size(),vigilanteService.consultarVehiculos().size());
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void agregarMotoValidaTest() {
		assertNotNull(vigilanteService.agregarMoto(moto));
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test(expected = ParqueaderoException.class)
	public void agregarMotoSobreCupoTest() {
		for (int i=0;i<=5;i++) {
			vigilanteService.agregarMoto(new MotoModel("AAA11"+i,true,100));
			vigilanteService.agregarMoto(new MotoModel("AAA12"+i,true,100));
		}
	}
	
	@Test
	public void agregarComprobantePagoCarroValidoTest() {
		vigilanteService.agregarCarro(carro);		
		assertNotNull(vigilanteService.agregarComprobantePago());
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void removerVehiculoTest() {
		vigilanteService.agregarCarro(carro);
		assertNotNull(vigilanteService.removerVehiculo("WSW04D"));
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test (expected = ParqueaderoException.class)
	public void removerVehiculoInexistenteTest() {
		vigilanteService.removerVehiculo("WSW04D");
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
		assertEquals(5, vigilanteService.calcularHorasTotales(entrada,salida));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void generarCobroCarrosHorasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 7, 59,0);
		assertEquals(5000,vigilanteService.calcularTotalAPagar
				(entrada,salida,parqueaderoModel.VALORDIACARRO,parqueaderoModel.VALORHORACARRO));
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void generarCobroCarrosDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 15, 0,0);
		assertEquals(8000, vigilanteService.calcularTotalAPagar
				(entrada,salida,parqueaderoModel.VALORDIACARRO,parqueaderoModel.VALORHORACARRO));
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void generarCobroCarrosHorasDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 2, 3, 1,0);
		assertEquals(9000, vigilanteService.calcularTotalAPagar
				(entrada,salida,parqueaderoModel.VALORDIACARRO,parqueaderoModel.VALORHORACARRO));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void generarCobroCarrosHorasDiasTes2t() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 2, 0,0).getTime();
		FechaModel   salida = new FechaModel(2017, 1, 2, 18, 0,0);
		assertEquals(16000, vigilanteService.calcularTotalAPagar
				(entrada,salida,parqueaderoModel.VALORDIACARRO,parqueaderoModel.VALORHORACARRO));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void generarCobroMotosHorasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 7, 59,0);
		assertEquals(2500, vigilanteService.calcularTotalAPagar
				(entrada,salida,parqueaderoModel.VALORDIAMOTO,parqueaderoModel.VALORHORAMOTO));
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void generarCobroMotosDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 15, 0,0);
		assertEquals(4000, vigilanteService.calcularTotalAPagar
				(entrada,salida,parqueaderoModel.VALORDIAMOTO,parqueaderoModel.VALORHORAMOTO));
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void generarCobroMotosHorasDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 2, 3, 1,0);
		assertEquals(4500, vigilanteService.calcularTotalAPagar
				(entrada,salida,parqueaderoModel.VALORDIAMOTO,parqueaderoModel.VALORHORAMOTO));
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
		vigilanteService.agregarCarro(carro);
		vigilanteService.agregarComprobantePago();
		vigilanteService.removerVehiculo("WSW04D");
		assertNotNull(vigilanteService.generarCobro("WSW04D"));
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void generarCobroVehiculosMotoTest() {
		vigilanteService.agregarMoto(moto);
		vigilanteService.agregarComprobantePago();
		vigilanteService.removerVehiculo("WSW04D");
		assertNotNull(vigilanteService.generarCobro("WSW04D"));
		comprobanteJpaRepository.deleteAll();
		vehiculoJpaRepository.deleteAll();
	}
	
	@Test
	public void crearMotoModelSinDatosTest() {
		assertNotNull(new MotoModel());
	}
	
	@Test
	public void crearCarroModelSinDatosTest() {
		assertNotNull(new CarroModel());
	}
	
	@Test
	public void getPlacaComprobanteTest() {
		ComprobantePagoModel comprobante = new ComprobantePagoModel();
		comprobante.setPlaca("WSW04D");
		assertEquals("WSW04D", comprobante.getPlaca());
	}
	
	@Test
	public void getTipoVehiculoComprobanteTest() {
		ComprobantePagoModel comprobante = new ComprobantePagoModel();
		comprobante.setTipoVehiculo("Carro");
		assertEquals("Carro", comprobante.getTipoVehiculo());
	}
	
	@Test
	public void getFechaEntradaTest() {
		ComprobantePagoModel comprobante = new ComprobantePagoModel();
		comprobante.setFechaEntrada(new Date());
		assertNotNull(comprobante.getFechaEntrada());;
	}
}
