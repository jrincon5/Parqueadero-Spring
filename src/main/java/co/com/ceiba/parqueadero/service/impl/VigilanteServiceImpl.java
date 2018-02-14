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
import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.MotoModel;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.ComprobantePagoModel;
import co.com.ceiba.parqueadero.repository.ComprobanteJpaRepository;
import co.com.ceiba.parqueadero.repository.VehiculoJpaRepository;
import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.service.VigilanteService;

@Service("vigilanteServiceImpl")
public class VigilanteServiceImpl implements VigilanteService{
	
	private ParqueaderoModel parqueaderoModel= new ParqueaderoModel();
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
			throw new ParqueaderoException("EL DIA DE HOY LE TOCA PICO Y PLACA, NO ES POSIBLE INGRESAR");
		}
		throw new ParqueaderoException("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS CARROS");
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
			throw new ParqueaderoException("EL DIA DE HOY LE TOCA PICO Y PLACA, NO ES POSIBLE INGRESAR");
		}
		throw new ParqueaderoException("NO HAY MAS CUPOS DISPONIBLES PARA INGRESAR MAS MOTOS");
	}
	
	@Override
	public List<ComprobantePagoModel> consultarVehiculos() {
		List<ComprobantePagoModel> comprobantes = new ArrayList<>();
		List<VehiculoEntity> vehiculos = vehiculoJpaRepository.findAll();
		for(VehiculoEntity vehiculo : vehiculos) {
			ComprobantePagoModel comprobante = new ComprobantePagoModel();
			comprobante.setPlaca(vehiculo.getPlaca());
			comprobante.setTipoVehiculo(vehiculo.getTipoVehiculo());
			comprobante.setFechaEntrada(comprobanteJpaRepository.findByPlaca(vehiculo).getFechaEntrada());
			comprobantes.add(comprobante);
		}
		return comprobantes;
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
		throw new ParqueaderoException("LA PLACA INGRESADA NO SE ENCUENTRA UBICADA EN EL PARQUEADERO");
	}
	
	@SuppressWarnings("static-access")
	@Override
	public ComprobantePagoEntity generarCobro(String placa) {
		LOG.info("CALL: generarCobroCarro()");
		LOG.info("CALL: comprobanteJpaRepository.findByPlaca(placa)");
		VehiculoEntity vehiculo = vehiculoJpaRepository.findOne(placa); // Encontrar vehiculo con la clave foranea
		ComprobantePagoEntity comprobanteEntity = comprobanteJpaRepository.findByPlaca(vehiculo);//Busca el comprobante en la base de datos
		FechaModel fechaSalida = parqueaderoModel.getFechaActual();
		comprobanteEntity.setFechaSalida(fechaSalida.getTime());
		comprobanteEntity.setEstado(false);
		long horasTotales=calcularHorasTotales(comprobanteEntity.getFechaEntrada(),fechaSalida);
		comprobanteEntity.setTotalHoras((int)horasTotales);
		int totalPagar=establecerVehiculoAPagar(comprobanteEntity, fechaSalida, vehiculo);
		comprobanteEntity.setTotalPagar(totalPagar);
		LOG.info("RETURNING: generarCobroCarro()");
		return comprobanteJpaRepository.save(comprobanteEntity);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public int establecerVehiculoAPagar(ComprobantePagoEntity comprobante, FechaModel fechaSalida,
			VehiculoEntity vehiculo) {
		long totalPagar=0;
		if(vehiculo.getTipoVehiculo().equals("Carro")){
			totalPagar=calcularTotalAPagar(comprobante.getFechaEntrada(), 
					fechaSalida,parqueaderoModel.VALORDIACARRO,parqueaderoModel.VALORHORACARRO);
		}			
		if(vehiculo.getTipoVehiculo().equals("Moto")) {
			totalPagar=calcularTotalAPagar(comprobante.getFechaEntrada(), 
					fechaSalida,parqueaderoModel.VALORDIAMOTO,parqueaderoModel.VALORHORAMOTO);
			totalPagar+=generarAumentoMotosAltoCilindraje(vehiculo.getCilindraje());
		}
		return (int)totalPagar;
	}

	@SuppressWarnings("static-access")
	@Override
	public long calcularHorasTotales(Date fechaEntrada, FechaModel paramFechaSalida) {
    	Date fechaSalida=paramFechaSalida.getTime();
		long diferenciaHoras=(fechaSalida.getTime()-fechaEntrada.getTime()) / 
    			(parqueaderoModel.MILISEGUNDOS * parqueaderoModel.SEGUNDOS * parqueaderoModel.MINUTOS);
    	if((fechaSalida.getTime()-fechaEntrada.getTime()) % 
    			(parqueaderoModel.MILISEGUNDOS * parqueaderoModel.SEGUNDOS * parqueaderoModel.MINUTOS)!=0)
    		diferenciaHoras++;
    	return diferenciaHoras;
    }
	
	@SuppressWarnings("static-access")
	@Override
	public long calcularTotalAPagar(Date fechaEntrada, FechaModel fechaSalida, int valorDia, int valorHora) {
		int horasTotales=(int)calcularHorasTotales(fechaEntrada, fechaSalida);
        int diasAPagar = horasTotales / parqueaderoModel.HORASMAXIMASDELDIA;
        int horasAPagar=0;
        if( (horasTotales % parqueaderoModel.HORASMAXIMASDELDIA)>=parqueaderoModel.HORASMINIMASDELDIA &&
        		(horasTotales % parqueaderoModel.HORASMAXIMASDELDIA)<=parqueaderoModel.HORASMAXIMASDELDIA-1 ) {
        	diasAPagar++; // Si hay una hora entre 9 y 23 horas, debe aumentar un dia
        }else {
        	horasAPagar = horasTotales % parqueaderoModel.HORASMAXIMASDELDIA;
        }
        return (long)(diasAPagar*valorDia)+(horasAPagar*valorHora);
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

	@SuppressWarnings("static-access")
	@Override
	public boolean picoYPlaca(String placa, int diaSemana) {
		return ((placa.startsWith(parqueaderoModel.validacionLetraA)) && (diaSemana==Calendar.SUNDAY || diaSemana==Calendar.MONDAY));
	}

	@SuppressWarnings("static-access")
	@Override
	public long generarAumentoMotosAltoCilindraje(int cilindraje) {
		if(cilindraje>parqueaderoModel.CILINDRAJEREGLAMOTO) return parqueaderoModel.AUMENTOCILINDRAJE;
		return 0;
	}
}