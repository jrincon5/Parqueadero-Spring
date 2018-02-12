package co.com.ceiba.parqueadero.service.impl;

import java.util.Calendar;
import java.util.Date;

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
import co.com.ceiba.parqueadero.model.VehiculoModel;
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
	
	@SuppressWarnings("static-access")
	@Override
	public VehiculoEntity agregarCarro(CarroModel carro) {
		LOG.info("CALL: agregarCarro()");
		if(validarEspacioCarros()) { // Validar espacio
			if(!picoYPlaca(carro.getPlaca(), Calendar.DAY_OF_WEEK)) { // Validar placa inicia con A
				LOG.info("RETURNING: agregarCarro()");
				return vehiculoJpaRepository.save(mapearModelAEntidad(carro));
			}
			LOG.info("EL DIA DE HOY LE TOCA PICO Y PLACA, NO ES POSIBLE INGRESAR");
			return null;
		}
		LOG.info("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS CARROS");
		return null;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public VehiculoEntity agregarMoto(MotoModel moto) {
		LOG.info("CALL: agregarMoto()");		
		if(validarEspacioMotos()) { // Validar espacio
			if(!picoYPlaca(moto.getPlaca(), Calendar.DAY_OF_WEEK)) { // Validar placa inicia con A
				LOG.info("RETURNING: agregarMoto()");
				return vehiculoJpaRepository.save(mapearModelAEntidad(moto));
			}
			LOG.info("EL DIA DE HOY LE TOCA PICO Y PLACA, NO ES POSIBLE INGRESAR");
			return null;
		}
		LOG.info("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS MOTOS");
		return null;
	}
	
	@Override
	public VehiculoEntity mapearModelAEntidad(VehiculoModel vehiculo) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		if(vehiculo instanceof MotoModel) {
			MotoModel moto = (MotoModel)vehiculo;
			vehiculoEntity = motoConverter.model2Entity(moto);
		}
		if(vehiculo instanceof CarroModel) {
			CarroModel carro = (CarroModel)vehiculo;
			vehiculoEntity = carroConverter.model2Entity(carro);
		}
		vehiculoEntity.setPlaca(vehiculoEntity.getPlaca().toUpperCase());
		vehiculoEntity.setParqueado(true);
		vehiculoEntity.setTipoVehiculo(vehiculo.getTipoVehiculo());
		this.vehiculoAux=vehiculoEntity;
		return vehiculoEntity;
	}
	
	@Override
	public ComprobantePagoEntity agregarComprobantePago() {
		LOG.info("CALL: agregarComprobantePago()");
		ComprobantePagoEntity factura = new ComprobantePagoEntity
				(parqueaderoModel.getFechaActual().getTime(),null,0,0,true,this.vehiculoAux);
		vehiculoAux=null;
		LOG.info("RETURNING: agregarComprobantePago()");
		return comprobanteJpaRepository.save(factura);
	}
	
	@Override
	public VehiculoEntity removerVehiculo(String placa) {
		LOG.info("CALL: removerVehiculo()");
		if(vehiculoJpaRepository.exists(placa.toUpperCase())) {
			VehiculoEntity vehiculoEntity = vehiculoJpaRepository.findOne(placa);
			vehiculoEntity.setParqueado(false);
			LOG.info("RETURNING: removerVehiculo()");
			return vehiculoJpaRepository.save(vehiculoEntity);
		}
		LOG.info("RETURNING NULL FROM: removeVehiculo() -- IT DIDN'T FIND THE VEHICLE");
		return null;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public ComprobantePagoEntity generarCobro(String placa) {
		LOG.info("CALL: generarCobroCarro()");
		if(vehiculoJpaRepository.exists(placa.toUpperCase())) {
			LOG.info("CALL: comprobanteJpaRepository.findByPlaca(placa)");
			VehiculoEntity veh = vehiculoJpaRepository.findOne(placa); // Encontrar vehiculo con la clave foranea
			ComprobantePagoEntity comprobanteEntity = comprobanteJpaRepository.findByPlaca(veh);//Busca el comprobante en la base de datos
			FechaModel fechaSalida = parqueaderoModel.getFechaActual();
			comprobanteEntity.setFechaSalida(fechaSalida.getTime());
			comprobanteEntity.setEstado(false);
			long horasTotales=calcularHorasTotales(comprobanteEntity.getFechaEntrada(),fechaSalida);
			comprobanteEntity.setTotalHoras((int)horasTotales);
			long totalPagar=0;
			if(veh.getTipoVehiculo().equals("Carro")){
				totalPagar=calcularTotalAPagar(comprobanteEntity.getFechaEntrada(), 
						fechaSalida,parqueaderoModel.DIACARRO,parqueaderoModel.HORACARRO);
			}			
			if(veh.getTipoVehiculo().equals("Moto")) {
				totalPagar=calcularTotalAPagar(comprobanteEntity.getFechaEntrada(), 
						fechaSalida,parqueaderoModel.DIAMOTO,parqueaderoModel.HORAMOTO);
				totalPagar+=generarAumentoMotosAltoCilindraje(veh.getCilindraje());
			}
			comprobanteEntity.setTotalPagar((int)totalPagar);
			LOG.info("RETURNING: generarCobroCarro()");
			return comprobanteJpaRepository.save(comprobanteEntity);
		}
		return null;
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
	public long calcularTotalAPagar(Date entrada, FechaModel salida, int valorDia, int valorHora) {
		int horasTotales=(int)calcularHorasTotales(entrada, salida);
        int diasAPagar = horasTotales / 24;
        int horasAPagar=0;
        if((horasTotales % 24)>=9 && (horasTotales % 24)<=23) {
        	diasAPagar++;
        }else {
        	horasAPagar = horasTotales % 24;
        }
        return (diasAPagar*valorDia)+(horasAPagar*valorHora);
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean validarEspacioCarros() {
		return vehiculoJpaRepository.countByCarrosOrMotos("Carro",true)<parqueaderoModel.LIMITECARROS;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean validarEspacioMotos() {
		return vehiculoJpaRepository.countByCarrosOrMotos("Moto",true)<parqueaderoModel.LIMITEMOTOS;
	}

	@Override
	public boolean picoYPlaca(String placa, int diaSemana) {
		return ((placa.startsWith("A")) && (diaSemana==1 || diaSemana==2));
	}

	@Override
	public long generarAumentoMotosAltoCilindraje(int cilindraje) {
		if(cilindraje>500) return 2000;
		return 0;
	}	
}