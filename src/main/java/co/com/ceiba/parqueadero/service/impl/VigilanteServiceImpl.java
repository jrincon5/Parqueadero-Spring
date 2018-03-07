package co.com.ceiba.parqueadero.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;
import co.com.ceiba.parqueadero.model.FechaCalendario;
import co.com.ceiba.parqueadero.model.Parqueadero;
import co.com.ceiba.parqueadero.model.Vehiculo;
import co.com.ceiba.parqueadero.model.ComprobantePago;
import co.com.ceiba.parqueadero.model.DatosEntrada;
import co.com.ceiba.parqueadero.repository.ComprobanteRepository;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;
import co.com.ceiba.parqueadero.repository.converter.ComprobanteCoverter;
import co.com.ceiba.parqueadero.repository.converter.VehiculoConverter;
import co.com.ceiba.parqueadero.service.VigilanteService;
import co.com.ceiba.parqueadero.validation.entervalidation.ValidacionIngresoVehiculo;
import co.com.ceiba.parqueadero.validation.exitvalidation.ValidacionSalidaVehiculo;

@Service("vigilanteServiceImpl")
public class VigilanteServiceImpl implements VigilanteService {

	private Parqueadero parqueaderoModel = new Parqueadero();

	private static final Log LOG = LogFactory.getLog(VigilanteServiceImpl.class);

	@Autowired
	@Qualifier("vehiculoRepository")
	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	@Qualifier("vehiculoConverter")
	private VehiculoConverter vehiculoConverter;
	
	@Autowired
	@Qualifier("comprobanteCoverter")
	private ComprobanteCoverter comprobanteConverter;

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
	public void ingresarVehiculo(Vehiculo vehiculo) {
		LOG.info("CALL: ingresarVehiculo()");
		validacionesIngreso.stream().forEach(validacion -> validacion.validar(vehiculo));
		vehiculoRepository.save(vehiculoConverter.establecerVehiculoAGuardar(vehiculo));
		agregarComprobantePago(vehiculo);
	}
	
	public void agregarComprobantePago(Vehiculo vehiculo) {
		LOG.info("CALL: agregarComprobantePago()");
		ComprobantePago comprobante = new ComprobantePago(vehiculo.getPlaca().toUpperCase(), 
				parqueaderoModel.getFechaActual().getTime(),null, 0, 0);
		comprobanteRepository.save(comprobanteConverter.comprobanteModel2Entity(comprobante,true));
	}

	@Override
	public List<DatosEntrada> consultarVehiculos() {
		LOG.info("CALL: consultarVehiculos()");
		List<DatosEntrada> datos = new ArrayList<>();
		List<VehiculoEntity> vehiculos = vehiculoRepository.findAll();
		return encontrarVehiculos(datos,vehiculos);
	}
	
	public List<DatosEntrada> encontrarVehiculos(List<DatosEntrada> datos,
			List<VehiculoEntity> vehiculos){
		LOG.info("CALL: encontrarVehiculos()");
		for (VehiculoEntity vehiculo : vehiculos) { // Cargar vehiculos activos
			if(comprobanteRepository.findByPlaca(vehiculo).isEstado()) {
				DatosEntrada dato = new DatosEntrada();
				dato.setPlaca(vehiculo.getPlaca());
				dato.setTipoVehiculo(vehiculo.getTipoVehiculo());
				dato.setFechaEntrada(comprobanteRepository.findByPlaca(vehiculo).getFechaEntrada());
				datos.add(dato);	
			}			
		}
		return datos;
	}

	@Override
	public ComprobantePago removerVehiculo(String placa) {
		LOG.info("CALL: removerVehiculo()");
		validacionesSalida.stream().forEach(validacion -> validacion.validar(placa.toUpperCase()));
		VehiculoEntity vehiculoEntity = vehiculoRepository.findOne(placa.toUpperCase());
		vehiculoEntity.setParqueado(false);
		return generarCobro(vehiculoRepository.save(vehiculoEntity));
	}

	public ComprobantePago generarCobro(VehiculoEntity vehiculo) {
		LOG.info("CALL: generarCobroCarro()");
		ComprobantePagoEntity comprobante = comprobanteRepository.findByPlaca(vehiculo);// Busca el comprobante en la base de datos
		FechaCalendario fechaSalida = parqueaderoModel.getFechaActual();
		comprobante.setFechaSalida(fechaSalida.getTime());
		long horasTotales = calcularHorasTotales(comprobante.getFechaEntrada(), fechaSalida);
		comprobante.setTotalHoras((int) horasTotales);
		int totalPagar = establecerVehiculoAPagar(comprobante, fechaSalida, vehiculo);
		comprobante.setTotalPagar(totalPagar);
		comprobante.setEstado(false);
		return comprobanteConverter.comprobanteEntity2Model(comprobanteRepository.save(comprobante));
	}

	public int establecerVehiculoAPagar(ComprobantePagoEntity comprobante, FechaCalendario fechaSalida,
			VehiculoEntity vehiculo) {
		long totalPagar = 0;
		if (vehiculo.getTipoVehiculo().equals("Carro")) {
			totalPagar = calcularTotalAPagar(comprobante.getFechaEntrada(), fechaSalida, Parqueadero.VALORDIACARRO,
					Parqueadero.VALORHORACARRO);
		}
		if (vehiculo.getTipoVehiculo().equals("Moto")) {
			totalPagar = calcularTotalAPagar(comprobante.getFechaEntrada(), fechaSalida, Parqueadero.VALORDIAMOTO,
					Parqueadero.VALORHORAMOTO);
			totalPagar += generarAumentoMotosAltoCilindraje(vehiculo.getCilindraje());
		}
		return (int) totalPagar;
	}

	public long calcularHorasTotales(Date fechaEntrada, FechaCalendario paramFechaSalida) {
		Date fechaSalida = paramFechaSalida.getTime();
		long diferenciaHoras = (fechaSalida.getTime() - fechaEntrada.getTime())
				/ (Parqueadero.MILISEGUNDOS * Parqueadero.SEGUNDOS * Parqueadero.MINUTOS);
		if ((fechaSalida.getTime() - fechaEntrada.getTime())
				% (Parqueadero.MILISEGUNDOS * Parqueadero.SEGUNDOS * Parqueadero.MINUTOS) != 0)
			diferenciaHoras++;
		return diferenciaHoras;
	}

	public long calcularTotalAPagar(Date fechaEntrada, FechaCalendario fechaSalida, int valorDia, int valorHora) {
		int horasTotales = (int) calcularHorasTotales(fechaEntrada, fechaSalida);
		int diasAPagar = horasTotales / Parqueadero.HORASMAXIMASDELDIA;
		int horasAPagar = 0;
		if ((horasTotales % Parqueadero.HORASMAXIMASDELDIA) >= Parqueadero.HORASMINIMASDELDIA
				&& (horasTotales % Parqueadero.HORASMAXIMASDELDIA) <= Parqueadero.HORASMAXIMASDELDIA - 1) {
			diasAPagar++; // Si hay una hora entre 9 y 23 horas, debe aumentar un dia
		} else {
			horasAPagar = horasTotales % Parqueadero.HORASMAXIMASDELDIA;
		}
		return (long) (diasAPagar * valorDia) + (horasAPagar * valorHora);
	}

	public long generarAumentoMotosAltoCilindraje(int cilindraje) {
		if (cilindraje > Parqueadero.CILINDRAJEREGLAMOTO)
			return Parqueadero.AUMENTOCILINDRAJE;
		return 0;
	}

}