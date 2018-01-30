package co.com.ceiba.parqueadero;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import co.com.ceiba.parqueadero.model.Carro;
import co.com.ceiba.parqueadero.model.Fecha;
import co.com.ceiba.parqueadero.model.Parqueadero;



public class ParqueaderoTest {
	 private Parqueadero park;
	 private Carro car;
	 
	@Before
	public void arrange() {
		park = new Parqueadero();
		car = new Carro("BSW04D",1000);
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
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 1, 8, 0);
		assertEquals(5, park.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularHorasConMinutosMax() {
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 1, 8, 1);
		assertEquals(6, park.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularHorasConMinutosMin() {
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 1, 7, 59);
		assertEquals(5, park.calcularHorasTotales(entrada, salida));
	}
	
	@Test
	public void calcularCobroCarrosHoras() {
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 1, 7, 59);
		assertEquals(5000, park.generarCobroCarros(entrada, salida));
	}
	
	@Test
	public void calcularCobroCarrosDias() {
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 1, 15, 00);
		assertEquals(8000, park.generarCobroCarros(entrada, salida));
	}
	
	@Test
	public void calcularCobroCarrosHorasDias() {
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 2, 3, 1);
		assertEquals(9000, park.generarCobroCarros(entrada, salida));
	}
	
	@Test
	public void calcularCobroMotosHoras() {
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 1, 7, 59);
		assertEquals(2500, park.generarCobroMotos(entrada, salida));
	}
	
	@Test
	public void calcularCobroMotosDias() {
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 1, 15, 00);
		assertEquals(4000, park.generarCobroMotos(entrada, salida));
	}
	
	@Test
	public void calcularCobroMotosHorasDias() {
		Fecha entrada = new Fecha(2017, 1, 1, 3, 0);
    	Fecha salida = new Fecha(2017, 1, 2, 3, 1);
		assertEquals(4500, park.generarCobroMotos(entrada, salida));
	}
}
