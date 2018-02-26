package co.com.ceiba.parqueadero.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.exception.ParqueaderoException;
import co.com.ceiba.parqueadero.model.FechaModel;
import co.com.ceiba.parqueadero.model.ParqueaderoModel;
import co.com.ceiba.parqueadero.model.VehiculoModel;
import co.com.ceiba.parqueadero.model.ComprobantePagoModel;
import co.com.ceiba.parqueadero.repository.ComprobanteRepository;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;
import co.com.ceiba.parqueadero.repository.converter.VehiculoConverter;
import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.service.VigilanteService;
import co.com.ceiba.parqueadero.validation.entervalidation.ValidacionIngresoVehiculo;
import co.com.ceiba.parqueadero.validation.exitvalidation.ValidacionSalidaVehiculo;

@Service("vigilanteServiceImpl")
public class VigilanteServiceImpl implements VigilanteService {

	private ParqueaderoModel parqueaderoModel = new ParqueaderoModel();

	private static final Log LOG = LogFactory.getLog(VigilanteServiceImpl.class);

	@Autowired
	@Qualifier("vehiculoRepository")
	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	@Qualifier("vehiculoConverter")
	private VehiculoConverter vehiculoConverter;

	@Autowired
	@Qualifier("comprobanteRepository")
	private ComprobanteRepository comprobanteRepository;

	List<ValidacionIngresoVehiculo> validacionesIngreso;
	List<ValidacionSalidaVehiculo> validacionesSalida;

	@Autowired
	public VigilanteServiceImpl(List<ValidacionIngresoVehiculo> validacionesIngreso, List<ValidacionSalidaVehiculo> validacionesSalida) {
		this.validacionesIngreso = validacionesIngreso;
		this.validacionesSalida = validacionesSalida;
	}

	@Override
	public void ingresarVehiculo(VehiculoModel vehiculoModel) {
		LOG.info("CALL: ingresarVehiculo()");
		validacionesIngreso.stream().forEach(validacion -> validacion.validar(vehiculoModel));
		agregarComprobantePago(vehiculoRepository.save(vehiculoConverter.
				establecerVehiculoAGuardar(vehiculoModel)));
		LOG.info("RETURNING: ingresarVehiculo()");
	}

	@Override
	public List<ComprobantePagoModel> consultarVehiculos() {
		LOG.info("CALL: consultarVehiculos()");
		List<ComprobantePagoModel> comprobantes = new ArrayList<>();
		List<VehiculoEntity> vehiculos = vehiculoRepository.findAll();
		LOG.info("RETURNING: consultarVehiculos()");
		return encontrarVehiculos(comprobantes,vehiculos);
	}
	
	public List<ComprobantePagoModel> encontrarVehiculos(List<ComprobantePagoModel> comprobantes,
			List<VehiculoEntity> vehiculos){
		for (VehiculoEntity vehiculo : vehiculos) {
			if(comprobanteRepository.findByPlaca(vehiculo).isEstado()) {
				ComprobantePagoModel comprobante = new ComprobantePagoModel();
				comprobante.setPlaca(vehiculo.getPlaca());
				comprobante.setTipoVehiculo(vehiculo.getTipoVehiculo());
				comprobante.setFechaEntrada(comprobanteRepository.findByPlaca(vehiculo).getFechaEntrada());
				comprobantes.add(comprobante);	
			}			
		}
		//if (comprobantes.isEmpty()) throw new ParqueaderoException("NO HAY VEHICULOS EN LA BASE DE DATOS"); 
		return comprobantes;
	}

	public ComprobantePagoEntity agregarComprobantePago(VehiculoEntity vehiculo) {
		LOG.info("CALL: agregarComprobantePago()");
		ComprobantePagoEntity factura = new ComprobantePagoEntity(parqueaderoModel.getFechaActual().getTime(), null, 0,
				0, true,vehiculo);
		LOG.info("RETURNING: agregarComprobantePago()");
		return comprobanteRepository.save(factura);
	}

	@Override
	public void removerVehiculo(String placa) {
		LOG.info("CALL: removerVehiculo()");
		validacionesSalida.stream().forEach(validacion -> validacion.validar(placa));
		VehiculoEntity vehiculoEntity = vehiculoRepository.findOne(placa);
		vehiculoEntity.setParqueado(false);
		generarCobro(placa);
		vehiculoRepository.save(vehiculoEntity);
		LOG.info("RETURNING: removerVehiculo()");
	}

	public ComprobantePagoEntity generarCobro(String placa) {
		LOG.info("CALL: generarCobroCarro()");
		VehiculoEntity vehiculo = vehiculoRepository.findOne(placa); // Encontrar vehiculo con la clave foranea
		ComprobantePagoEntity comprobanteEntity = comprobanteRepository.findByPlaca(vehiculo);// Busca el comprobante en la base de datos
		FechaModel fechaSalida = parqueaderoModel.getFechaActual();
		comprobanteEntity.setFechaSalida(fechaSalida.getTime());
		comprobanteEntity.setEstado(false);
		long horasTotales = calcularHorasTotales(comprobanteEntity.getFechaEntrada(), fechaSalida);
		comprobanteEntity.setTotalHoras((int) horasTotales);
		int totalPagar = establecerVehiculoAPagar(comprobanteEntity, fechaSalida, vehiculo);
		comprobanteEntity.setTotalPagar(totalPagar);
		LOG.info("RETURNING: generarCobroCarro()");
		return comprobanteRepository.save(comprobanteEntity);
	}

	public int establecerVehiculoAPagar(ComprobantePagoEntity comprobante, FechaModel fechaSalida,
			VehiculoEntity vehiculo) {
		long totalPagar = 0;
		if (vehiculo.getTipoVehiculo().equals("Carro")) {
			totalPagar = calcularTotalAPagar(comprobante.getFechaEntrada(), fechaSalida, ParqueaderoModel.VALORDIACARRO,
					ParqueaderoModel.VALORHORACARRO);
		}
		if (vehiculo.getTipoVehiculo().equals("Moto")) {
			totalPagar = calcularTotalAPagar(comprobante.getFechaEntrada(), fechaSalida, ParqueaderoModel.VALORDIAMOTO,
					ParqueaderoModel.VALORHORAMOTO);
			totalPagar += generarAumentoMotosAltoCilindraje(vehiculo.getCilindraje());
		}
		return (int) totalPagar;
	}

	public long calcularHorasTotales(Date fechaEntrada, FechaModel paramFechaSalida) {
		Date fechaSalida = paramFechaSalida.getTime();
		long diferenciaHoras = (fechaSalida.getTime() - fechaEntrada.getTime())
				/ (ParqueaderoModel.MILISEGUNDOS * ParqueaderoModel.SEGUNDOS * ParqueaderoModel.MINUTOS);
		if ((fechaSalida.getTime() - fechaEntrada.getTime())
				% (ParqueaderoModel.MILISEGUNDOS * ParqueaderoModel.SEGUNDOS * ParqueaderoModel.MINUTOS) != 0)
			diferenciaHoras++;
		return diferenciaHoras;
	}

	public long calcularTotalAPagar(Date fechaEntrada, FechaModel fechaSalida, int valorDia, int valorHora) {
		int horasTotales = (int) calcularHorasTotales(fechaEntrada, fechaSalida);
		int diasAPagar = horasTotales / ParqueaderoModel.HORASMAXIMASDELDIA;
		int horasAPagar = 0;
		if ((horasTotales % ParqueaderoModel.HORASMAXIMASDELDIA) >= ParqueaderoModel.HORASMINIMASDELDIA
				&& (horasTotales % ParqueaderoModel.HORASMAXIMASDELDIA) <= ParqueaderoModel.HORASMAXIMASDELDIA - 1) {
			diasAPagar++; // Si hay una hora entre 9 y 23 horas, debe aumentar un dia
		} else {
			horasAPagar = horasTotales % ParqueaderoModel.HORASMAXIMASDELDIA;
		}
		return (long) (diasAPagar * valorDia) + (horasAPagar * valorHora);
	}

	public long generarAumentoMotosAltoCilindraje(int cilindraje) {
		if (cilindraje > ParqueaderoModel.CILINDRAJEREGLAMOTO)
			return ParqueaderoModel.AUMENTOCILINDRAJE;
		return 0;
	}

}