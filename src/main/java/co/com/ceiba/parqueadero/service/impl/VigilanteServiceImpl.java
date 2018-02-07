package co.com.ceiba.parqueadero.service.impl;

import java.util.ArrayList;
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
	private VehiculoEntity vehiculoAux=new VehiculoEntity();
	
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
	
	@SuppressWarnings("static-access")
	@Override
	public VehiculoEntity addCarro(CarroModel carro) {
		LOG.info("CALL: addCarro()");
		if(parqueaderoModel.getCeldasCarro().size()<=parqueaderoModel.LIMITECARROS) {
			VehiculoEntity vehiculoEntity = carroConverter.model2Entity(carro);
			vehiculoEntity.setPlaca(vehiculoEntity.getPlaca().toUpperCase());
			vehiculoEntity.setParqueado(true);
			vehiculoEntity.setTipoVehiculo(carro.getTipoVehiculo());
			CeldaModel celda = new CeldaModel(carro,getFechaActual());
			parqueaderoModel.setCeldasCarro(celda);
			this.vehiculoAux=vehiculoEntity;
			LOG.info("RETURNING: addCarro()");
			return vehiculoJpaRepository.save(vehiculoEntity);
		}
		return null;		
	}
	
	@Override
	public ComprobantePagoEntity addComprobantePagoCarro() {
		LOG.info("CALL: addComprobantePagoCarro()");
		ComprobantePagoEntity factura;
		int size=parqueaderoModel.getCeldasCarro().size();
		LOG.info("CALL: Estoy sirviendo " + size);
		Date fechaIngreso = parqueaderoModel.getCeldasCarro().get(size-1).getFecha().getTime();
		factura = new ComprobantePagoEntity(fechaIngreso,null,0,0,this.vehiculoAux);
		vehiculoAux=null;
		return comprobanteJpaRepository.save(factura);
	}
	
	@Override
	public VehiculoEntity removeVehiculo(String placa) {
		LOG.info("CALL: removeVehiculo()");
		if(vehiculoJpaRepository.exists(placa.toUpperCase())) {
			VehiculoEntity vehiculoEntity = vehiculoJpaRepository.findOne(placa);
			vehiculoEntity.setParqueado(false);
			LOG.info("RETURNING: removeCarro()");
			return vehiculoJpaRepository.save(vehiculoEntity);
		}
		LOG.info("RETURNING NULL FROM: removeVehiculo() -- IT DIDN'T FIND THE VEHICLE");
		return null;
	}
	
	@Override
	public ComprobantePagoEntity generarCobroCarro(String placa) {
		LOG.info("CALL: generarCobroCarro()");
		if(vehiculoJpaRepository.exists(placa.toUpperCase())) {
			LOG.info("CALL: comprobanteJpaRepository.findByPlaca(placa)");
			VehiculoEntity veh = new VehiculoEntity();
			veh.setPlaca(placa);
			ComprobantePagoEntity comprobanteEntity = comprobanteJpaRepository.findByPlaca(veh);
			//Date fechaSalida = new Date(getFechaActual());
			//comprobanteEntity.setFechaSalida();
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public VehiculoEntity addMoto(MotoModel moto) {
		LOG.info("CALL: addMoto()");
		if(parqueaderoModel.getCeldasMoto().size()<=parqueaderoModel.LIMITEMOTOS) {
			VehiculoEntity vehiculoEntity = motoConverter.model2Entity(moto);
			vehiculoEntity.setPlaca(vehiculoEntity.getPlaca().toUpperCase());
			vehiculoEntity.setParqueado(true);
			vehiculoEntity.setTipoVehiculo(moto.getTipoVehiculo());
			CeldaModel celda = new CeldaModel(moto,getFechaActual());
			parqueaderoModel.setCeldasMoto(celda);
			this.vehiculoAux=vehiculoEntity;
			LOG.info("RETURNING: addMoto()");
			return vehiculoJpaRepository.save(vehiculoEntity);
		}
		return null;
	}
	
	@Override
	public ComprobantePagoEntity addComprobantePagoMoto() {
		LOG.info("CALL: addFechaMoto()");
		ComprobantePagoEntity factura;
		int size=parqueaderoModel.getCeldasMoto().size();
		Date fechaIngreso = parqueaderoModel.getCeldasMoto().get(size-1).getFecha().getTime();
		factura = new ComprobantePagoEntity(fechaIngreso,null,0,0,this.vehiculoAux);
		vehiculoAux=null;
		return comprobanteJpaRepository.save(factura);
	}
	
	public FechaModel getFechaActual() {
    	Calendar calendar = Calendar.getInstance();
    	int year=calendar.get(Calendar.YEAR);
    	int mes=calendar.get(Calendar.MONTH);
    	int diaMes=calendar.get(Calendar.DAY_OF_MONTH);
    	int horaDia=calendar.get(Calendar.HOUR_OF_DAY);
    	int minuto=calendar.get(Calendar.MINUTE);
    	int second=calendar.get(Calendar.SECOND);
    	return new FechaModel(year,mes,diaMes,horaDia,minuto,second);
    }

	@Override
	public List<CarroModel> listAllCarros() {
		List<VehiculoEntity> vehiculos = vehiculoJpaRepository.findAll();
		List<CarroModel> listCarros = new ArrayList<>();
		for(VehiculoEntity vehiculo : vehiculos) {
			listCarros.add(carroConverter.entity2Model(vehiculo));
		}
		return listCarros;
	}
	
	@Override
	public List<MotoModel> listAllMotos() {
		List<VehiculoEntity> vehiculos = vehiculoJpaRepository.findAll();
		List<MotoModel> listMotos = new ArrayList<>();
		for(VehiculoEntity vehiculo : vehiculos) {
			listMotos.add(motoConverter.entity2Model(vehiculo));
		}
		return listMotos;
	}
}