package co.com.ceiba.parqueadero.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.converter.VehiculoConverter;
import co.com.ceiba.parqueadero.entity.VehiculoEnt;
import co.com.ceiba.parqueadero.model.CeldaModel;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.repository.FacturaJpaRepository;
import co.com.ceiba.parqueadero.repository.ParqueaderoJpaRepository;
import co.com.ceiba.parqueadero.entity.FacturaEnt;
import co.com.ceiba.parqueadero.service.ParqueaderoService;

@Service("parqueaderoServiceImpl")
public class ParqueaderoServiceImpl implements ParqueaderoService{
	
	private Parqueadero parqueaderoModel= new Parqueadero();
	
	private static final Log LOG = LogFactory.getLog(ParqueaderoServiceImpl.class);
	private static final int HORACARRO=1000;
    private static final int HORAMOTO=500;
    private static final int DIACARRO=8000;
    private static final int DIAMOTO=4000;
    private static final int MOTOALTOCILINDRAJE=2000;
	
	@Autowired
	@Qualifier("vehiculoConverter")
	private VehiculoConverter vehiculoConverter;
	
	@Autowired
	@Qualifier("parqueaderoJpaRepository")
	private ParqueaderoJpaRepository parqueaderoJpaRepository;
	
	@Autowired
	@Qualifier("facturaJpaRepository")
	private FacturaJpaRepository facturaJpaRepository;

	@Override
	public List<VehiculoEnt> listAllVehiculos() {
		return parqueaderoJpaRepository.findAll();
	}

	@Override
	public VehiculoEnt findByPlaca(String placa) {
		return parqueaderoJpaRepository.findByPlaca(placa);
	}

	@Override
	public VehiculoEnt addCarro(VehiculoModel vehiculo) {
		LOG.info("CALL: addCarro()");
		
		if(parqueaderoModel.getCeldasCarro().size()<=20) {
			if(picoYPlaca(vehiculo.getPlaca())) {
				VehiculoEnt vehiculoEnt = vehiculoConverter.model2Entity(vehiculo);
				vehiculoEnt.setPlaca(vehiculoEnt.getPlaca().toUpperCase());
				vehiculoEnt.setParqueado(true);
				vehiculoEnt.setTipo_vehiculo("Carro");
				FechaModel fechaIngreso = getFechaActual();
				CeldaModel celda = new CeldaModel(vehiculo,fechaIngreso);
				parqueaderoModel.setCeldasCarro(celda);
				return parqueaderoJpaRepository.save(vehiculoEnt);
			}
		}
		return null;		
	}
	
	public boolean picoYPlaca(String placa) {
    	if(placa.startsWith("A")) {
    		if(Calendar.DAY_OF_WEEK!=0 && Calendar.DAY_OF_WEEK!=1){
    			return true;
    		}
    	}
    	return false;
    }
	
	public FechaModel getFechaActual() {
    	Calendar Cal = Calendar.getInstance();
    	int year=Cal.get(Calendar.YEAR);
    	int mes=Cal.get(Calendar.MONTH);
    	int diaMes=Cal.get(Calendar.DAY_OF_MONTH);
    	int horaDia=Cal.get(Calendar.HOUR_OF_DAY);
    	int minuto=Cal.get(Calendar.MINUTE);
    	return new FechaModel(year,mes,diaMes,horaDia,minuto);
    }

	@Override
	public FacturaEnt addFecha(FechaModel fecha, String placa) {
		//FacturaEnt factura = new FacturaEnt(fecha, null, 0, 0, placa);
		return null;
	}
}
