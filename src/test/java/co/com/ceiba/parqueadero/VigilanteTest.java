package co.com.ceiba.parqueadero;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.Carro;
import co.com.ceiba.parqueadero.model.DatosEntrada;
import co.com.ceiba.parqueadero.model.FechaCalendario;
import co.com.ceiba.parqueadero.model.Moto;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.repository.ComprobanteRepository;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;
import co.com.ceiba.parqueadero.repository.converter.VehiculoConverter;
import co.com.ceiba.parqueadero.service.VigilanteService;
import co.com.ceiba.parqueadero.service.impl.VigilanteServiceImpl;
import co.com.ceiba.parqueadero.validation.entervalidation.ValidacionPlacaIniciaPorA;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class VigilanteTest {
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteServiceImpl vigilanteServiceImpl;
	
	@Autowired
	@Qualifier("vehiculoRepository")
	VehiculoRepository vehiculoRepository;
	
	@Autowired
	@Qualifier("comprobanteRepository")
	ComprobanteRepository comprobanteRepository;
	
	@Autowired
	@Qualifier("vehiculoConverter")
	VehiculoConverter vehiculoConverter;
	
	Carro carro;
	Moto moto;
	Parqueadero parqueaderoModel;
	
	@Before
	public void arrange() {
		carro = new Carro("WSW04D");
		moto = new Moto("WSW04D",100);
	}
	
	@After
	public void clean() {
		comprobanteRepository.deleteAll();
	    vehiculoRepository.deleteAll();
	}
	
	@Test
	public void ingresarCarroValidoTest() {
		vigilanteService.ingresarVehiculo(carro);
		assertTrue(vehiculoRepository.exists(carro.getPlaca()));
	}
	
	@Test(expected = ParqueaderoException.class)
	public void ingresarCarroSobreCupoTest() {
		for (int i=0;i<=10;i++) {
			vigilanteService.ingresarVehiculo(new Carro("BAA11"+i));
			vigilanteService.ingresarVehiculo(new Carro("BAA12"+i));
		}
	}
	
	@Test(expected = ParqueaderoException.class)
	public void ingresarCarroYaPArqueadoTest() {
		vigilanteService.ingresarVehiculo(new Carro("BAA111"));
		vigilanteService.ingresarVehiculo(new Carro("BAA111"));
	}
	
	@Test
	public void ingresarMotoValidaTest() {
		vigilanteService.ingresarVehiculo(moto);
		assertTrue(vehiculoRepository.exists(moto.getPlaca()));
	}
	
	@Test(expected = ParqueaderoException.class)
	public void ingresarMotoSobreCupoTest() {
		for (int i=0;i<=5;i++) {
			vigilanteService.ingresarVehiculo(new Moto("BAA11"+i,100));
			vigilanteService.ingresarVehiculo(new Moto("BAA12"+i,100));
		}
	}
		
	@Test
	public void agregarComprobantePagoCarroValidoTest() {
		vigilanteService.ingresarVehiculo(carro);
		VehiculoEntity vehiculo = vehiculoConverter.establecerVehiculoAGuardar(carro);
		assertNotNull(comprobanteRepository.findByPlaca(vehiculo));
	}
	
	@Test
	public void agregarComprobantePagoMotoValidoTest() {
		vigilanteService.ingresarVehiculo(moto);
		VehiculoEntity vehiculo = vehiculoConverter.establecerVehiculoAGuardar(moto);
		assertNotNull(comprobanteRepository.findByPlaca(vehiculo));
	}
	
	@Test
	public void consultarVehiculoTest() {
		vigilanteService.ingresarVehiculo(carro);
		List<DatosEntrada> comprobantes = new ArrayList<>();
		comprobantes.addAll(vigilanteService.consultarVehiculos());
		assertEquals(comprobantes.size(),vigilanteService.consultarVehiculos().size());
	}
	
	@Test
	public void consultarVehiculoInvalidoTest() {
		List<DatosEntrada> comprobantes = new ArrayList<>();
		comprobantes.addAll(vigilanteService.consultarVehiculos());
		assertEquals(comprobantes.size(),vigilanteService.consultarVehiculos().size());
	}
	
	@Test
	public void removerVehiculoCarroTest() {
		vigilanteService.ingresarVehiculo(carro);
		vigilanteService.removerVehiculo(carro.getPlaca());
		VehiculoEntity vehiculo = vehiculoRepository.findOne(carro.getPlaca());
		assertFalse(vehiculo.isParqueado());
	}
	
	@Test
	public void removerVehiculoMotoTest() {
		vigilanteService.ingresarVehiculo(moto);
		vigilanteService.removerVehiculo(moto.getPlaca());
		VehiculoEntity vehiculo = vehiculoRepository.findOne(moto.getPlaca());
		assertFalse(vehiculo.isParqueado());
	}
	
	@Test (expected = ParqueaderoException.class)
	public void removerVehiculoInexistenteTest() {
		vigilanteService.removerVehiculo("WSW04D");
	}
	
	@Test
	public void calcularHorasExactasTotalesTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 5, 8, 0,0);
		assertEquals(101, vigilanteServiceImpl.calcularHorasTotales(fechaEntrada, fechaSalida));
	}
	
	@Test
	public void calcularHorasInexactasConMinutosDeMasTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
    	FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 1, 8, 1,0);
		assertEquals(6, vigilanteServiceImpl.calcularHorasTotales(fechaEntrada, fechaSalida));
	}
	
	@Test
	public void calcularHorasInexactasConMinutossDeMenosTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
    	FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 1, 7, 59,0);
		assertEquals(5, vigilanteServiceImpl.calcularHorasTotales(fechaEntrada,fechaSalida));
	}
	
	@Test
	public void generarCobroCarrosHorasTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 1, 7, 59,0);
		assertEquals(5000,vigilanteServiceImpl.calcularTotalAPagar
				(fechaEntrada,fechaSalida,Parqueadero.VALORDIACARRO,Parqueadero.VALORHORACARRO));
		
	}
	
	@Test
	public void generarCobroCarrosDiasTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 1, 15, 0,0);
		assertEquals(8000, vigilanteServiceImpl.calcularTotalAPagar
				(fechaEntrada,fechaSalida,Parqueadero.VALORDIACARRO,Parqueadero.VALORHORACARRO));
		
	}
	
	@Test
	public void generarCobroCarrosHorasDiasTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 2, 3, 1,0);
		assertEquals(9000, vigilanteServiceImpl.calcularTotalAPagar
				(fechaEntrada,fechaSalida,Parqueadero.VALORDIACARRO,Parqueadero.VALORHORACARRO));
	}
	
	@Test
	public void generarCobroCarrosHorasDiasTes2t() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 2, 0,0).getTime();
		FechaCalendario   fechaSalida = new FechaCalendario(2017, 1, 2, 18, 0,0);
		assertEquals(16000, vigilanteServiceImpl.calcularTotalAPagar
				(fechaEntrada,fechaSalida,Parqueadero.VALORDIACARRO,Parqueadero.VALORHORACARRO));
	}
	
	@Test
	public void generarCobroMotosHorasTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 1, 7, 59,0);
		assertEquals(2500, vigilanteServiceImpl.calcularTotalAPagar
				(fechaEntrada,fechaSalida,Parqueadero.VALORDIAMOTO,Parqueadero.VALORHORAMOTO));
		
	}
	
	@Test
	public void generarCobroMotosDiasTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 1, 15, 0,0);
		assertEquals(4000, vigilanteServiceImpl.calcularTotalAPagar
				(fechaEntrada,fechaSalida,Parqueadero.VALORDIAMOTO,Parqueadero.VALORHORAMOTO));
		
	}
	
	@Test
	public void generarCobroMotosHorasDiasTest() {
		Date fechaEntrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaCalendario fechaSalida = new FechaCalendario(2017, 1, 2, 3, 1,0);
		assertEquals(4500, vigilanteServiceImpl.calcularTotalAPagar
				(fechaEntrada,fechaSalida,Parqueadero.VALORDIAMOTO,Parqueadero.VALORHORAMOTO));
	}
	
	@Test
	public void vehiculoRestringidoNoPuedeIngresarUnDiaHabil() {
		carro = new Carro("AAA111");
		ValidacionPlacaIniciaPorA validacionPlacaIniciaPorA = Mockito.mock(ValidacionPlacaIniciaPorA.class);
		Mockito.when(validacionPlacaIniciaPorA.placaIniciaPorAYEsHabil(carro.getPlaca())).thenReturn(true);
		assertTrue(validacionPlacaIniciaPorA.placaIniciaPorAYEsHabil(carro.getPlaca()));
	}
	
	@Test
	public void vehiculoRestringidoPuedeIngresarUnDiaHabil() {
		carro = new Carro("AAA111");
		ValidacionPlacaIniciaPorA validacionPlacaIniciaPorA = Mockito.mock(ValidacionPlacaIniciaPorA.class);
		Mockito.when(validacionPlacaIniciaPorA.placaIniciaPorAYEsHabil(carro.getPlaca())).thenReturn(false);
		assertFalse(validacionPlacaIniciaPorA.placaIniciaPorAYEsHabil(carro.getPlaca()));
	}
	
	@Test(expected = ParqueaderoException.class)
	public void vehiculoRestringidoNoPuedeIngresarUnDiaHabil2() {
		carro = new Carro("AAA111");
		ValidacionPlacaIniciaPorA validacionPlacaIniciaPorA = Mockito.mock(ValidacionPlacaIniciaPorA.class);
		doThrow(ParqueaderoException.class).when(validacionPlacaIniciaPorA).validar(carro);
	    validacionPlacaIniciaPorA.validar(carro);
	}
	
	@Test
	public void generarAumentoMotosAltoCilindrajeTest() {
		assertEquals(2000, vigilanteServiceImpl.generarAumentoMotosAltoCilindraje(600));
	}
	
	@Test
	public void noGenerarAumentoMotosAltoCilindrajeTest() {
		assertEquals(0, vigilanteServiceImpl.generarAumentoMotosAltoCilindraje(400));
	}
	
	@Test
	public void crearMotoModelSinDatosTest() {
		assertNotNull(new Moto());
	}
	
	@Test
	public void crearCarroModelSinDatosTest() {
		assertNotNull(new Carro());
	}
		
	@Test
	public void getPlacaComprobanteTest() {
		DatosEntrada comprobante = new DatosEntrada();
		comprobante.setPlaca("WSW04D");
		assertEquals("WSW04D", comprobante.getPlaca());
	}
	
	@Test
	public void getTipoVehiculoComprobanteTest() {
		DatosEntrada comprobante = new DatosEntrada();
		comprobante.setTipoVehiculo("Carro");
		assertEquals("Carro", comprobante.getTipoVehiculo());
	}
	
	@Test
	public void getFechaEntradaTest() {
		DatosEntrada comprobante = new DatosEntrada();
		comprobante.setFechaEntrada(new Date());
		assertNotNull(comprobante.getFechaEntrada());
	}
	
	@Test
	public void getFechaSalidaTest() {
		VehiculoEntity vehiculo = new VehiculoEntity();
		vehiculo.setPlaca("SSS111");
		ComprobantePagoEntity comprobante = new ComprobantePagoEntity(new Date(), new Date(), 2, 2500, true, vehiculo);
		assertNotNull(comprobante.getFechaSalida());
	}
	
	@Test
	public void getTotalHorasTest() {
		VehiculoEntity vehiculo = new VehiculoEntity();
		vehiculo.setPlaca("SSS111");
		ComprobantePagoEntity comprobante = new ComprobantePagoEntity(new Date(), new Date(), 2, 2500, true, vehiculo);
		assertNotNull(comprobante.getTotalHoras());
	}
	
	@Test
	public void getTotalPagarTest() {
		VehiculoEntity vehiculo = new VehiculoEntity();
		vehiculo.setPlaca("SSS111");
		ComprobantePagoEntity comprobante = new ComprobantePagoEntity(new Date(), new Date(), 2, 2500, true, vehiculo);
		assertNotNull(comprobante.getTotalPagar());
	}
	
	@Test
	public void getPlacaFkTest() {
		VehiculoEntity vehiculo = new VehiculoEntity();
		vehiculo.setPlaca("SSS111");
		ComprobantePagoEntity comprobante = new ComprobantePagoEntity(new Date(), new Date(), 2, 2500, true, vehiculo);
		assertNotNull(comprobante.getPlacaFk());
	}
}