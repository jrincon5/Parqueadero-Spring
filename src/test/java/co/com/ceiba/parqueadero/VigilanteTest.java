package co.com.ceiba.parqueadero;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.ComprobantePagoModel;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
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
	
	CarroModel carro;
	MotoModel moto;
	ParqueaderoModel parqueaderoModel;
	
	@Before
	public void arrange() {
		carro = new CarroModel("WSW04D",true);
		moto = new MotoModel("WSW04D",true,100);
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
			vigilanteService.ingresarVehiculo(new CarroModel("BAA11"+i,true));
			vigilanteService.ingresarVehiculo(new CarroModel("BAA12"+i,true));
		}
	}
	
	@Test(expected = ParqueaderoException.class)
	public void ingresarCarroYaPArqueadoTest() {
		vigilanteService.ingresarVehiculo(new CarroModel("BAA111",true));
		vigilanteService.ingresarVehiculo(new CarroModel("BAA111",true));
	}
	
	@Test
	public void ingresarMotoValidaTest() {
		vigilanteService.ingresarVehiculo(moto);
		assertTrue(vehiculoRepository.exists(moto.getPlaca()));
	}
	
	@Test(expected = ParqueaderoException.class)
	public void ingresarMotoSobreCupoTest() {
		for (int i=0;i<=5;i++) {
			vigilanteService.ingresarVehiculo(new MotoModel("BAA11"+i,true,100));
			vigilanteService.ingresarVehiculo(new MotoModel("BAA12"+i,true,100));
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
		List<ComprobantePagoModel> comprobantes = new ArrayList<>();
		comprobantes.addAll(vigilanteService.consultarVehiculos());
		assertEquals(comprobantes.size(),vigilanteService.consultarVehiculos().size());
	}
	
	@Test
	public void consultarVehiculoInvalidoTest() {
		List<ComprobantePagoModel> comprobantes = new ArrayList<>();
		comprobantes.addAll(vigilanteService.consultarVehiculos());
		assertEquals(comprobantes.size(),vigilanteService.consultarVehiculos().size());
	}
	
	@Test
	public void removerVehiculoCarroTest() {
		vigilanteService.ingresarVehiculo(carro);
		vigilanteService.removerVehiculo(carro.getPlaca());
		VehiculoEntity vehiculo = vehiculoConverter.establecerVehiculoAGuardar(carro);
		assertFalse(comprobanteRepository.findByPlaca(vehiculo).isEstado());
	}
	
	@Test
	public void removerVehiculoMotoTest() {
		vigilanteService.ingresarVehiculo(moto);
		vigilanteService.removerVehiculo(moto.getPlaca());
		VehiculoEntity vehiculo = vehiculoConverter.establecerVehiculoAGuardar(moto);
		assertFalse(comprobanteRepository.findByPlaca(vehiculo).isEstado());
	}
	
	@Test (expected = ParqueaderoException.class)
	public void removerVehiculoInexistenteTest() {
		vigilanteService.removerVehiculo("WSW04D");
	}
	
	@Test
	public void calcularHorasExactasTotalesTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 5, 8, 0,0);
		assertEquals(101, vigilanteServiceImpl.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularHorasInexactasConMinutosDeMasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
    	FechaModel salida = new FechaModel(2017, 1, 1, 8, 1,0);
		assertEquals(6, vigilanteServiceImpl.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularHorasInexactasConMinutossDeMenosTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
    	FechaModel salida = new FechaModel(2017, 1, 1, 7, 59,0);
		assertEquals(5, vigilanteServiceImpl.calcularHorasTotales(entrada,salida));
	}
	
	@Test
	public void generarCobroCarrosHorasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 7, 59,0);
		assertEquals(5000,vigilanteServiceImpl.calcularTotalAPagar
				(entrada,salida,ParqueaderoModel.VALORDIACARRO,ParqueaderoModel.VALORHORACARRO));
		
	}
	
	@Test
	public void generarCobroCarrosDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 15, 0,0);
		assertEquals(8000, vigilanteServiceImpl.calcularTotalAPagar
				(entrada,salida,ParqueaderoModel.VALORDIACARRO,ParqueaderoModel.VALORHORACARRO));
		
	}
	
	@Test
	public void generarCobroCarrosHorasDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 2, 3, 1,0);
		assertEquals(9000, vigilanteServiceImpl.calcularTotalAPagar
				(entrada,salida,ParqueaderoModel.VALORDIACARRO,ParqueaderoModel.VALORHORACARRO));
	}
	
	@Test
	public void generarCobroCarrosHorasDiasTes2t() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 2, 0,0).getTime();
		FechaModel   salida = new FechaModel(2017, 1, 2, 18, 0,0);
		assertEquals(16000, vigilanteServiceImpl.calcularTotalAPagar
				(entrada,salida,ParqueaderoModel.VALORDIACARRO,ParqueaderoModel.VALORHORACARRO));
	}
	
	@Test
	public void generarCobroMotosHorasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 7, 59,0);
		assertEquals(2500, vigilanteServiceImpl.calcularTotalAPagar
				(entrada,salida,ParqueaderoModel.VALORDIAMOTO,ParqueaderoModel.VALORHORAMOTO));
		
	}
	
	@Test
	public void generarCobroMotosDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 1, 15, 0,0);
		assertEquals(4000, vigilanteServiceImpl.calcularTotalAPagar
				(entrada,salida,ParqueaderoModel.VALORDIAMOTO,ParqueaderoModel.VALORHORAMOTO));
		
	}
	
	@Test
	public void generarCobroMotosHorasDiasTest() {
		Date entrada = new GregorianCalendar(2017, 1, 1, 3, 0,0).getTime();
		FechaModel salida = new FechaModel(2017, 1, 2, 3, 1,0);
		assertEquals(4500, vigilanteServiceImpl.calcularTotalAPagar
				(entrada,salida,ParqueaderoModel.VALORDIAMOTO,ParqueaderoModel.VALORHORAMOTO));
	}
	
	@Test(expected=ParqueaderoException.class)
	public void picoYPlacaDomingo() {
		ValidacionPlacaIniciaPorA validacionPlacaIniciaPorA = new ValidacionPlacaIniciaPorA();
		carro = new CarroModel("AAA111", true);
		validacionPlacaIniciaPorA.validar(carro);
	}
	
	@Test(expected=ParqueaderoException.class)
	public void picoYPlacaLunes() {
		ValidacionPlacaIniciaPorA validacionPlacaIniciaPorA = new ValidacionPlacaIniciaPorA();
		carro = new CarroModel("AAA111", true);
		validacionPlacaIniciaPorA.validar(carro);
	}
	
	@Test
	public void picoYPlacaDiaHabil() {
		ValidacionPlacaIniciaPorA validacionPlacaIniciaPorA = new ValidacionPlacaIniciaPorA();
		assertTrue(validacionPlacaIniciaPorA.placaIniciaPorAYEsHabil("AAA111", 4));
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