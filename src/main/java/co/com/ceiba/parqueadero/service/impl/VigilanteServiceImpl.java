package co.com.ceiba.parqueadero.service.impl;

import java.util.ArrayList;
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
import co.com.ceiba.parqueadero.model.validacionesingreso.ValidacionIngresoVehiculo;
import co.com.ceiba.parqueadero.model.CarroModel;
import co.com.ceiba.parqueadero.model.ComprobantePagoModel;
import co.com.ceiba.parqueadero.repository.ComprobanteRepository;
import co.com.ceiba.parqueadero.repository.VehiculoRepository;
import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.service.VigilanteService;

@Service("vigilanteServiceImpl")
public class VigilanteServiceImpl implements VigilanteService {

	private ParqueaderoModel parqueaderoModel = new ParqueaderoModel();

	private static final Log LOG = LogFactory.getLog(VigilanteServiceImpl.class);

	@Autowired
	@Qualifier("carroConverter")
	private CarroConverter carroConverter;

	@Autowired
	@Qualifier("motoConverter")
	private MotoConverter motoConverter;

	@Autowired
	@Qualifier("vehiculoJpaRepository")
	private VehiculoRepository vehiculoJpaRepository;

	@Autowired
	@Qualifier("comprobanteJpaRepository")
	private ComprobanteRepository comprobanteRepository;

	List<ValidacionIngresoVehiculo> validacionesIngreso;

	@Autowired
	public VigilanteServiceImpl(List<ValidacionIngresoVehiculo> validacionesIngreso) {
		this.validacionesIngreso = validacionesIngreso;
	}

	@Override
	public void ingresarVehiculo(VehiculoModel vehiculoModel) {
		validacionesIngreso.stream().forEach(validacion -> validacion.validar(vehiculoModel));
		agregarComprobantePago(vehiculoJpaRepository.save(mapearModelAEntidad(vehiculoModel)));
	}

	@Override
	public List<ComprobantePagoModel> consultarVehiculos() {
		List<ComprobantePagoModel> comprobantes = new ArrayList<>();
		List<VehiculoEntity> vehiculos = vehiculoJpaRepository.findAll();
		for (VehiculoEntity vehiculo : vehiculos) {
			ComprobantePagoModel comprobante = new ComprobantePagoModel();
			comprobante.setPlaca(vehiculo.getPlaca());
			comprobante.setTipoVehiculo(vehiculo.getTipoVehiculo());
			comprobante.setFechaEntrada(comprobanteRepository.findByPlaca(vehiculo).getFechaEntrada());
			comprobantes.add(comprobante);
		}
		return comprobantes;
	}

	public VehiculoEntity mapearModelAEntidad(VehiculoModel vehiculo) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		if (vehiculo instanceof MotoModel) {
			MotoModel moto = (MotoModel) vehiculo;
			vehiculoEntity = motoConverter.model2Entity(moto);
		}
		if (vehiculo instanceof CarroModel) {
			CarroModel carro = (CarroModel) vehiculo;
			vehiculoEntity = carroConverter.model2Entity(carro);
		}
		vehiculoEntity.setPlaca(vehiculoEntity.getPlaca().toUpperCase());
		vehiculoEntity.setParqueado(true);
		vehiculoEntity.setTipoVehiculo(vehiculo.getTipoVehiculo());
		return vehiculoEntity;
	}

	public ComprobantePagoEntity agregarComprobantePago(VehiculoEntity vehiculo) {
		LOG.info("CALL: agregarComprobantePago()");
		ComprobantePagoEntity factura = new ComprobantePagoEntity(parqueaderoModel.getFechaActual().getTime(), null, 0,
				0, true,vehiculo);
		LOG.info("RETURNING: agregarComprobantePago()");
		return comprobanteRepository.save(factura);
	}

	@Override
	public VehiculoEntity removerVehiculo(String placa) {
		LOG.info("CALL: removerVehiculo()");
		if (vehiculoJpaRepository.exists(placa.toUpperCase())) {
			VehiculoEntity vehiculoEntity = vehiculoJpaRepository.findOne(placa);
			vehiculoEntity.setParqueado(false);
			LOG.info("RETURNING: removerVehiculo()");
			return vehiculoJpaRepository.save(vehiculoEntity);
		}
		throw new ParqueaderoException("LA PLACA INGRESADA NO SE ENCUENTRA UBICADA EN EL PARQUEADERO");
	}

	@SuppressWarnings("static-access")
	public ComprobantePagoEntity generarCobro(String placa) {
		LOG.info("CALL: generarCobroCarro()");
		LOG.info("CALL: comprobanteJpaRepository.findByPlaca(placa)");
		VehiculoEntity vehiculo = vehiculoJpaRepository.findOne(placa); // Encontrar vehiculo con la clave foranea
		ComprobantePagoEntity comprobanteEntity = comprobanteRepository.findByPlaca(vehiculo);// Busca el comprobante
																									// en la base de
																									// datos
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