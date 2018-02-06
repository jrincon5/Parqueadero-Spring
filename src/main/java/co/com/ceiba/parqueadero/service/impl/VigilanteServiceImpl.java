package co.com.ceiba.parqueadero.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.converter.CarroConverter;
import co.com.ceiba.parqueadero.converter.MotoConverter;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.CeldaModel;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.repository.ComprobanteJpaRepository;
import co.com.ceiba.parqueadero.repository.VehiculoJpaRepository;
import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.service.VigilanteService;

@Service("vigilanteServiceImpl")
public class VigilanteServiceImpl implements VigilanteService{
	
	private Parqueadero parqueaderoModel= new Parqueadero();
	private VehiculoEntity vehiculoEntity=new VehiculoEntity();
	
	private static final Log LOG = LogFactory.getLog(VigilanteServiceImpl.class);	
	
	@Autowired
	@Qualifier("carroConverter")
	private CarroConverter carroConverter;
	
	@Autowired
	@Qualifier("motoConverter")
	private MotoConverter motoConverter;
	
	@Autowired
	@Qualifier("vehiculoJpaRepository")
	private VehiculoJpaRepository vehiculoJpaRepository;
	
	@Autowired
	@Qualifier("comprobanteJpaRepository")
	private ComprobanteJpaRepository comprobanteJpaRepository;

	@Override
	public List<VehiculoEntity> listAllVehiculos() {
		return vehiculoJpaRepository.findAll();
	}

	@Override
	public VehiculoEntity addCarro(CarroModel carro) {
		
		LOG.info("CALL: addCarro()");
		if(parqueaderoModel.getCeldasCarro().size()<=parqueaderoModel.LIMITECARROS) {
			if(picoYPlaca(carro.getPlaca())) {
				VehiculoEntity vehiculoEntity = carroConverter.model2Entity(carro);
				vehiculoEntity.setPlaca(vehiculoEntity.getPlaca().toUpperCase());
				vehiculoEntity.setParqueado(true);
				vehiculoEntity.setTipoVehiculo(carro.getTipoVehiculo());
				CeldaModel celda = new CeldaModel(carro,getFechaActual());
				parqueaderoModel.setCeldasCarro(celda);
				//this.vehiculoEntity=vehiculoEntity;
				LOG.info("RETURNING: addCarro()");
				return vehiculoJpaRepository.save(vehiculoEntity);
			}
		}
		return null;		
	}
	
	@Override
	public VehiculoEntity removeCarro(String placa) {
		/*VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity = vehiculoJpaRepository.findOne(placa);
		vehiculoEntity.setParqueado(false);*/
		return vehiculoJpaRepository.save(vehiculoEntity);
	}
	
	@Override
	public VehiculoEntity addMoto(MotoModel moto) {
		LOG.info("CALL: addMoto()");
		if(parqueaderoModel.getCeldasMoto().size()<=10) {
			if(picoYPlaca(moto.getPlaca())) {
				VehiculoEntity vehiculoEntity = motoConverter.model2Entity(moto);
				vehiculoEntity.setPlaca(vehiculoEntity.getPlaca().toUpperCase());
				vehiculoEntity.setParqueado(true);
				vehiculoEntity.setTipoVehiculo(moto.getTipoVehiculo());
				CeldaModel celda = new CeldaModel(moto,getFechaActual());
				parqueaderoModel.setCeldasMoto(celda);
				//this.vehiculoEntity=vehiculoEntity;
				LOG.info("RETURNING: addMoto()");
				return vehiculoJpaRepository.save(vehiculoEntity);
			}
		}
		return null;
	}	

	@Override
	public ComprobantePagoEntity addFechaCarro() {
		LOG.info("CALL: addFechaCarro()");
		ComprobantePagoEntity factura;
		int size=parqueaderoModel.getCeldasCarro().size();
		Date fechaIngreso = parqueaderoModel.getCeldasCarro().get(size-1).getFecha().getTime();
		factura = new ComprobantePagoEntity(fechaIngreso,null,0,0,this.vehiculoEntity);
		return comprobanteJpaRepository.save(factura);
	}
	
	@Override
	public ComprobantePagoEntity addFechaMoto() {
		LOG.info("CALL: addFechaMoto()");
		ComprobantePagoEntity factura;
		int size=parqueaderoModel.getCeldasMoto().size();
		Date fechaIngreso = parqueaderoModel.getCeldasMoto().get(size-1).getFecha().getTime();
		factura = new ComprobantePagoEntity(fechaIngreso,null,0,0,this.vehiculoEntity);
		return comprobanteJpaRepository.save(factura);
	}
	
	public boolean picoYPlaca(String placa) {
    	if(placa.startsWith("A")) {
    		if(Calendar.DAY_OF_WEEK!=0 && Calendar.DAY_OF_WEEK!=1){
    			return false;
    		}
    	}
    	return true;
    }
	
	public FechaModel getFechaActual() {
    	Calendar Cal = Calendar.getInstance();
    	int year=Cal.get(Calendar.YEAR);
    	int mes=Cal.get(Calendar.MONTH);
    	int diaMes=Cal.get(Calendar.DAY_OF_MONTH);
    	int horaDia=Cal.get(Calendar.HOUR_OF_DAY);
    	int minuto=Cal.get(Calendar.MINUTE);
    	int second=Cal.get(Calendar.SECOND);
    	return new FechaModel(year,mes,diaMes,horaDia,minuto,second);
    }

	@Override
	public VehiculoEntity findByPlaca(String placa) {
		// TODO Auto-generated method stub
		return null;
	}	
}