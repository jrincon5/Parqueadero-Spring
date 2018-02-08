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
		if(!validarPlacaExistente(carro.getPlaca())) { // Validar placa existente
			if(validarEspacioCarros()) { // Validar espacio
				if(!picoYPlaca(carro.getPlaca(), Calendar.DAY_OF_WEEK)) { // Validar placa inicia con A
					VehiculoEntity vehiculoEntity = carroConverter.model2Entity(carro);
					vehiculoEntity.setPlaca(vehiculoEntity.getPlaca().toUpperCase());
					vehiculoEntity.setParqueado(true);
					vehiculoEntity.setTipoVehiculo(carro.getTipoVehiculo());
					this.vehiculoAux=vehiculoEntity;
					LOG.info("RETURNING: addCarro()");
					return vehiculoJpaRepository.save(vehiculoEntity);
				}
				LOG.info("EL DIA DE HOY LE TOCA PICO Y PLACA, NO ES POSIBLE INGRESAR");
				return null;
			}
			LOG.info("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS CARROS");
			return null;
		}
		LOG.info("LA PLACA QUE ESTA INTENTANDO INGRESAR YA SE ENCUENTRA PRESENTE EN EL PARQUEADERO");
		return null;
	}
	
	@Override
	public ComprobantePagoEntity addComprobantePagoCarro() {
		LOG.info("CALL: addComprobantePagoCarro()");
		ComprobantePagoEntity factura;
		int size=parqueaderoModel.getCeldasCarro().size();
		LOG.info("CALL: Estoy sirviendo " + size);
		factura = new ComprobantePagoEntity(parqueaderoModel.getFechaActual().getTime(),null,0,0,this.vehiculoAux);
		vehiculoAux=null;
		LOG.info("RETURNING: addComprobantePagoCarro()");
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
			ComprobantePagoEntity comprobanteEntity = comprobanteJpaRepository.findByPlaca(veh);//Busca el comprobante en la base de datos
			FechaModel fechaSalida = parqueaderoModel.getFechaActual();
			comprobanteEntity.setFechaSalida(fechaSalida.getTime());
			long horasTotales=calcularHorasTotales(comprobanteEntity.getFechaEntrada(),fechaSalida);
			comprobanteEntity.setTotalHoras((int)horasTotales);
			long totalPagar=generarCobroCarros(comprobanteEntity.getFechaEntrada(), fechaSalida);
			comprobanteEntity.setTotalPagar((int)totalPagar);
			LOG.info("RETURNING: generarCobroCarro()");
			return comprobanteJpaRepository.save(comprobanteEntity);
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public VehiculoEntity addMoto(MotoModel moto) {
		LOG.info("CALL: addMoto()");
		if(vehiculoJpaRepository.countByCarros("Moto",true)<=parqueaderoModel.LIMITEMOTOS) {
			VehiculoEntity vehiculoEntity = motoConverter.model2Entity(moto);
			vehiculoEntity.setPlaca(vehiculoEntity.getPlaca().toUpperCase());
			vehiculoEntity.setParqueado(true);
			vehiculoEntity.setTipoVehiculo(moto.getTipoVehiculo());
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

	@Override
	public long calcularHorasTotales(Date d1, FechaModel salida) {
    	Date d2=salida.getTime();
    	long dif=(d2.getTime()-d1.getTime()) / (1000 * 60 * 60);
    	if((d2.getTime()-d1.getTime()) % (1000 * 60 * 60)!=0) dif++;
    	return dif;
    }

	@SuppressWarnings("static-access")
	@Override
	public long generarCobroCarros(Date entrada, FechaModel salida) {
		int horasTotales=(int)calcularHorasTotales(entrada, salida);
        int diasAPagar = horasTotales / 24;
        int horasAPagar=0;
        if((horasTotales % 24)>=9 && (horasTotales % 24)<=23) {
        	diasAPagar++;
        }else {
        	horasAPagar = horasTotales % 24;
        }        
        int totalAPagar=(diasAPagar*parqueaderoModel.DIACARRO)+(horasAPagar*parqueaderoModel.HORACARRO);
        return totalAPagar;
	}

	@Override
	public boolean validarPlacaExistente(String placa) {
		return vehiculoJpaRepository.exists(placa.toUpperCase());
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean validarEspacioCarros() {
		return vehiculoJpaRepository.countByCarros("Carro",true)<parqueaderoModel.LIMITECARROS;
	}

	@Override
	public boolean picoYPlaca(String placa, int diaSemana) {
		if(placa.startsWith("A")) {
    		if(diaSemana==1 || diaSemana==2){
    			return true;
    		}
    	}
    	return false;
	}
}