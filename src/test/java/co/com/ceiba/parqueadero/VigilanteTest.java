package co.com.ceiba.parqueadero;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.service.VigilanteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebMvcTest(VigilanteService.class)
public class VigilanteTest {
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
	
	VehiculoEntity vehiculoCarro;
	CarroModel carro;
	private final JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	@Transactional
	public void addVehiculo(String placa,int cilindraje,boolean parqueado,String tipoVehiculo) {
		jdbcTemplate.update("INSERT INTO vehiculo(placa,cilindraje,parqueado,tipo_vehiculo) VALUES (?,?,?,?)",placa,cilindraje,parqueado,tipoVehiculo);		
	}
	
	@Override
	public void run() {
		
	}
	
	@Before
	public void arrange() {
		carro=new CarroModel();
		//vehiculoCarro=new VehiculoEntity("WSW04D",true,0,"Carro");
		vehiculoCarro=mock(VehiculoEntity.class);
	}

	@Test
	public void addVehiculoTest() {
		carro.setPlaca("WSW04D");
		carro.setParqueado(true);
		//carro.setTipoVehiculo("Carro");
		//DataService dataServiceMock = mock(DataService.class);
		//when(vigilanteService.addCarro(carro))
		assertEquals(vehiculoCarro,vigilanteService.addCarro(carro));
	}
}
