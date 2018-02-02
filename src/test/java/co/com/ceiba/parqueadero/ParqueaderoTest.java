package co.com.ceiba.parqueadero;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.Parqueadero;



public class ParqueaderoTest {
	 private Parqueadero park;
	 private CarroModel car;
	 
	@Before
	public void arrange() {
		park = new Parqueadero();
		car = new CarroModel("BSW04D",1000);
	}
	
	@Test
	public void ingresarCarroTest() {
        assertTrue(park.ingresarCarro("BSW04D",1000));
    }
	
	@Test
	public void ingresarMotoTest() {
        assertTrue(park.ingresarMoto("BSW04D",1000));
    }
	
	@Test
	public void ingresarCarroConReglaMaravillosaTest() {
        assertFalse(park.ingresarCarro("ASW04D",1000));
    }
	
	@Test
	public void ingresarMotoConReglaMaravillosaTest() {
        assertFalse(park.ingresarMoto("ASW04D",1000));
    }
	
	@Test
    public void ingresarCarrosMaximosTest() {
        for(int i = 0; i< 30 ; i++) {
            park.ingresarCarro("WSW04D", 1000);
        }
        assertFalse(park.ingresarCarro("WSW04D",1000));
    }
	
	@Test
    public void ingresarMotosMaximasTest() {
        for(int i = 0; i< 20 ; i++) {
            park.ingresarMoto("WSW04D", 1000);
        }
        assertFalse(park.ingresarMoto("WSW04D",1000));
    }
	
	@Test
	public void sacarCarroTest() {
		assertFalse(park.sacarCarro("WSW04D"));
	}
	
	@Test
	public void sacarMotoTest() {
		assertFalse(park.sacarMoto("WSW04D"));
	}
	
	@Test
	public void calcularHoras() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 5, 8, 0);
		assertEquals(101, park.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularHorasConMinutosMax() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 1, 8, 1);
		assertEquals(6, park.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularHorasConMinutosMin() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 1, 7, 59);
		assertEquals(5, park.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularCobroCarrosHoras() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 1, 7, 59);
		assertEquals(5000, park.generarCobroCarros(entrada, salida));
	}
	
	@Test
	public void calcularCobroCarrosDias() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 1, 15, 00);
		assertEquals(8000, park.generarCobroCarros(entrada, salida));
	}
	
	@Test
	public void calcularCobroCarrosHorasDias() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 2, 3, 1);
		assertEquals(9000, park.generarCobroCarros(entrada, salida));
	}
	
	@Test
	public void calcularCobroMotosHoras() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 1, 7, 59);
		assertEquals(2500, park.generarCobroMotos(entrada, salida));
	}
	
	@Test
	public void calcularCobroMotosDias() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 1, 15, 00);
		assertEquals(4000, park.generarCobroMotos(entrada, salida));
	}
	
	@Test
	public void calcularCobroMotosHorasDias() {
		FechaModel entrada = new FechaModel(2017, 1, 1, 3, 0);
    	FechaModel salida = new FechaModel(2017, 1, 2, 3, 1);
		assertEquals(4500, park.generarCobroMotos(entrada, salida));
	}
}
